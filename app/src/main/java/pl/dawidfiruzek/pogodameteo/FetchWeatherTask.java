package pl.dawidfiruzek.pogodameteo;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by fks on 2015-06-18.
 */
public class FetchWeatherTask extends AsyncTask<Void, Void, Bitmap> {

    private ImageView mImage;

    public FetchWeatherTask(ImageView imageView){
        mImage = imageView;
    }
//    http://new.meteo.pl/um/php/meteorogram_id_um.php?ntype=0n&id=462
//    http://new.meteo.pl/um/metco/mgram_pict.php?ntype=0u&fdate=2015061812&row=466&col=232&lang=pl

//    http://new.meteo.pl/php/meteorogram_id_coamps.php?ntype=2n&id=462
//    http://new.meteo.pl/metco/mgram_pict.php?ntype=2n&fdate=2015061812&row=151&col=90&lang=pl
    @Override
    protected Bitmap doInBackground(Void... params) {
        Bitmap weatherImage = null;
        String ntype = null;
        String fdate = null;
        String row = null;
        String col = null;
        String lang = null;

        Uri.Builder builder = new Uri.Builder();
        //TODO czytane z bazy danych dla typu pogody UM/COAMPS
        builder.scheme("http")
                .authority("new.meteo.pl")
                .appendPath("um")
                .appendPath("php")
                .appendPath("meteorogram_id_um.php")
                .appendQueryParameter("ntype","0n")
                .appendQueryParameter("id","462").build();

        try{
            URL meteoUrl = new URL(builder.toString());
            Log.v(MainActivity.TAG, meteoUrl.toString());
//
//            URLConnection urlConnection = meteoUrl.openConnection();
//            urlConnection.connect();
//            InputStream is = urlConnection.getInputStream();
//            BufferedInputStream bis = new BufferedInputStream(is);
//            weatherImage = BitmapFactory.decodeStream(bis);
//            bis.close();
//            is.close();
            //TODO URL build on id of the city or GPS position
            org.jsoup.nodes.Document doc = Jsoup.connect(meteoUrl.toString()).timeout(10000).get();
            Element script = doc.select("script").first();
            //getting variables values that are needed to get meteorogram
            Pattern pattern = Pattern.compile("(?is)var\\s??fcstdate\\s??=\\s??\"(.+?)\\n");
            Matcher m = pattern.matcher(script.html());

            String meteorogramParams = null;
//            Log.v(MainActivity.TAG, script.toString());
            while(m.find()) {
                meteorogramParams = m.group();
                Log.v(MainActivity.TAG, meteorogramParams);
            }

            if(meteorogramParams != null){
                //example result: var fcstdate = "2015062612";var ntype ="0n";var lang ="pl";var id="462";var act_x = 232;var act_y = 466;
                //TODO split parameters to save it.
            }

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
