package pl.dawidfiruzek.pogodameteo.networking;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import pl.dawidfiruzek.pogodameteo.activities.MainActivity;
import pl.dawidfiruzek.pogodameteo.interfaces.AsyncWeatherResponse;

public class FetchWeatherTask extends AsyncTask<Void, Void, Bitmap> {
    public AsyncWeatherResponse delegate = null;
    private Context content;
    private ProgressDialog progressCircle;
    private String gridModel;
    private String language;
    private String updateMethod;
    private String defaultCity;

    public FetchWeatherTask(Context context){
        this.content = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        getSharedPreferences();
        setAndShowProgressCircle();
    }

    @Override
    protected Bitmap doInBackground(Void... params) {
        Bitmap meteogramImage = null;
        Uri.Builder weatherUri = new Uri.Builder();
        buildWeatherUri(weatherUri);

        try{
            URL weatherUrl = new URL(weatherUri.toString());
            Log.d(MainActivity.TAG, weatherUrl.toString());
            URL meteogramImageUrl = getMeteogramImageUrl(weatherUrl);
            meteogramImage = downloadMeteogramImage(meteogramImageUrl);
        } catch (Exception e){
            Log.e(MainActivity.TAG, "Unsuccessful image downloading");
        }

        return meteogramImage;
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        this.progressCircle.dismiss();
        delegate.setDownloadedWeatherImage(result);
    }

    private void getSharedPreferences() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.content);
        this.gridModel = sharedPreferences.getString("grid_preference", "um");
        this.language = sharedPreferences.getString("language_preference", "pl");
        this.updateMethod = sharedPreferences.getString("update_preference", "GPS");
        this.defaultCity = sharedPreferences.getString("city_preference", "462");
    }

    private void setAndShowProgressCircle() {
        this.progressCircle = new ProgressDialog(this.content);
        //TODO to string
        this.progressCircle.setMessage("Downloading weather");
        this.progressCircle.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        this.progressCircle.show();
    }

    private void buildWeatherUri(Uri.Builder weatherUri) {
        weatherUri.scheme("http").authority("new.meteo.pl");
        if(this.gridModel.equals("um")){
            weatherUri.appendPath("um");
        }
        weatherUri.appendPath("php");

        if(this.updateMethod.equals("GPS")){
            Log.d(MainActivity.TAG, "GPS update detected");
            weatherUri
                    .appendPath("mgram_search.php")
                            //TODO enter actual values from GPS
                    .appendQueryParameter("NALL", "50")
                    .appendQueryParameter("EALL", "19").build();
        }
        else {
            Log.d(MainActivity.TAG, "CITY update detected");
            if (this.gridModel.equals("um")) {
                weatherUri
                        .appendPath("meteorogram_id_um.php")
                        .appendQueryParameter("ntype", "0u");
            } else if (this.gridModel.equals("coamps")) {
                weatherUri
                        .appendPath("meteorogram_id_coamps.php")
                        .appendQueryParameter("ntype", "2n");
            }
            weatherUri
                    .appendQueryParameter("id", this.defaultCity)
                    .build();
        }
    }

    @NonNull
    private URL getMeteogramImageUrl(URL meteoUrl) throws IOException {
        Element scriptWithVariables = parseScriptWithVariables(meteoUrl);
        Matcher parsedVariables = getVariablesFromScript(scriptWithVariables);

        String meteorogramParams = null;
        while(parsedVariables.find()) {
            meteorogramParams = parsedVariables.group();
            Log.d(MainActivity.TAG, meteorogramParams);
        }
        if(meteorogramParams != null){
            //example result: var fcstdate = "2015062612";var ntype ="0n";var lang ="pl";var id="462";var act_x = 232;var act_y = 466;
            meteorogramParams = formatMeteogramParams(meteorogramParams);
            if(isEnglishLanguage()){
                meteorogramParams = setEnglishLanguageInUrl(meteorogramParams);
            }

            Log.d(MainActivity.TAG, meteorogramParams);
        }
        else {
            Log.e(MainActivity.TAG, "Unsuccessful image address parsing");
        }
        String meteogramImgAddress = getMeteogramImgAddress(meteorogramParams);
        return new URL(meteogramImgAddress);
    }

    private Element parseScriptWithVariables(URL meteoUrl) throws IOException {
        org.jsoup.nodes.Document doc = Jsoup.connect(meteoUrl.toString()).timeout(10000).get();
        return doc.select("script").last();
    }

    @NonNull
    private Matcher getVariablesFromScript(Element scriptWithVariables) {
        //getting variables values that are needed to get meteorogram
        Pattern pattern = Pattern.compile("(?s)var\\s??(.+?);var\\s??(.+?)ntype(.+?);\\n");
        return pattern.matcher(scriptWithVariables.html());
    }

    private String formatMeteogramParams(String meteorogramParams) {
        meteorogramParams = meteorogramParams.replace("\"", "")
                .replace(";var ", "&")
                .replace("var ", "?")
                .replace("fcstdate", "fdate")
                .replace("act_x", "col")
                .replace("act_y", "row")
                .replace(" ", "")
                .replace(";", "");
        return meteorogramParams;
    }

    private boolean isEnglishLanguage() {
        return this.language.equals("en");
    }

    private String setEnglishLanguageInUrl(String meteorogramParams) {
        meteorogramParams = meteorogramParams.replace("lang=pl", "lang=en");
        return meteorogramParams;
    }

    @NonNull
    private String getMeteogramImgAddress(String meteorogramParams) {
        String meteorogramImg = "http://new.meteo.pl";
        if(this.gridModel.equals("um")) {
            meteorogramImg += "/um/metco/mgram_pict.php";
        }
        else{
            meteorogramImg += "/metco/mgram_pict.php";
        }
        meteorogramImg += meteorogramParams;
        return meteorogramImg;
    }

    private Bitmap downloadMeteogramImage(URL meteogramImageUrl) throws IOException {
        Bitmap weatherImage;
        URLConnection urlConnection = meteogramImageUrl.openConnection();
        urlConnection.connect();
        InputStream is = urlConnection.getInputStream();
        BufferedInputStream bis = new BufferedInputStream(is);
        weatherImage = BitmapFactory.decodeStream(bis);
        bis.close();
        is.close();
        return weatherImage;
    }
}
