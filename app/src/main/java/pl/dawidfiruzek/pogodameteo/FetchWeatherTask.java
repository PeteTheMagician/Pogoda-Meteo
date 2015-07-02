package pl.dawidfiruzek.pogodameteo;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.ImageView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by fks on 2015-06-18.
 */
public class FetchWeatherTask extends AsyncTask<Void, Void, Bitmap> {

    private ImageView mImage;
    private Context mContext;

    public FetchWeatherTask(Context context, ImageView imageView){
        mImage = imageView;
        mContext = context;
    }

    @Override
    protected Bitmap doInBackground(Void... params) {
        Bitmap weatherImage = null;

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        String gridModel = sharedPreferences.getString("grid_preference", "um");
        String language = sharedPreferences.getString("language_preference", "pl");
        String updateMethod = sharedPreferences.getString("update_preference", "GPS");
        String defaultCity = sharedPreferences.getString("city_preference", "462");
//    http://new.meteo.pl/um/php/mgram_search.php?NALL=50.25&EALL=19&lang
//    http://new.meteo.pl/um/php/meteorogram_id_um.php?ntype=0n&id=462
//    http://new.meteo.pl/um/metco/mgram_pict.php
//
//    http://new.meteo.pl/php/mgram_search.php?NALL=50.25&EALL=19&lang=pl
//    http://new.meteo.pl/php/meteorogram_id_coamps.php?ntype=2n&id=462
//    http://new.meteo.pl/metco/mgram_pict.php

        Uri.Builder builder = new Uri.Builder();
        builder.scheme("http").authority("new.meteo.pl");
        if(gridModel.equals("um")){
            builder.appendPath("um");
        }
        builder.appendPath("php");

        if(updateMethod.equals("GPS")){
            builder
                    .appendPath("mgram_search.php")
                    //TODO enter actual values from GPS
                    .appendQueryParameter("NALL", "50")
                    .appendQueryParameter("EALL", "19").build();
        }
        else {
            if (gridModel.equals("um")) {
                builder
                        .appendPath("meteorogram_id_um.php")
                        .appendQueryParameter("ntype", "0u");
            } else if (gridModel.equals("coamps")) {
                builder
                        .appendPath("meteorogram_id_coamps.php")
                        .appendQueryParameter("ntype", "2n");
            }
            builder
                    .appendQueryParameter("id", defaultCity)
                    .build();
        }

        try{
            URL meteoUrl = new URL(builder.toString());
            Log.d(MainActivity.TAG, meteoUrl.toString());

//            String testUrl = "http://new.meteo.pl/um/php/meteorogram_id_um.php?ntype=0n&id=462";

            //TODO URL build on id of the city or GPS position
            org.jsoup.nodes.Document doc = Jsoup.connect(meteoUrl.toString()).timeout(10000).get();
            Element script = doc.select("script").last();
            //getting variables values that are needed to get meteorogram
            Pattern pattern = Pattern.compile("(?s)var\\s??(.+?);var\\s??(.+?)ntype(.+?);\\n");
            Matcher m = pattern.matcher(script.html());

            String meteorogramParams = null;
//            Log.d(MainActivity.TAG, script.toString());
            while(m.find()) {
                meteorogramParams = m.group();
                Log.d(MainActivity.TAG, meteorogramParams);
            }

            if(meteorogramParams != null){
                //example result: var fcstdate = "2015062612";var ntype ="0n";var lang ="pl";var id="462";var act_x = 232;var act_y = 466;
                meteorogramParams = meteorogramParams.replace("\"", "")
                        .replace(";var ", "&")
                        .replace("var ", "?")
                        .replace("fcstdate", "fdate")
                        .replace("act_x", "col")
                        .replace("act_y", "row")
                        .replace(" ", "")
                        .replace(";", "");

                if(language.equals("en")){
                    meteorogramParams = meteorogramParams.replace("lang=pl", "lang=en");
                }

                Log.d(MainActivity.TAG, meteorogramParams);
            }
            else {
                Log.e(MainActivity.TAG, "Unsuccessful image address parsing");
            }

            String meteorogramImg = "http://new.meteo.pl";
            if(gridModel.equals("um")) {
                meteorogramImg += "/um/metco/mgram_pict.php";
            }
            else{
                meteorogramImg += "/metco/mgram_pict.php";
            }
            meteorogramImg += meteorogramParams;

            URL meteoImg = new URL(meteorogramImg);
            URLConnection urlConnection = meteoImg.openConnection();
            urlConnection.connect();
            InputStream is = urlConnection.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is);
            weatherImage = BitmapFactory.decodeStream(bis);
            bis.close();
            is.close();

        } catch (Exception e){
            Log.e(MainActivity.TAG, "Unsuccessful image downloading");
        }

        return weatherImage;
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        mImage.setImageBitmap(result);
    }
}
