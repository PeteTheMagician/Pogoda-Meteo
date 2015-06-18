package pl.dawidfiruzek.pogodameteo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

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

        Uri.Builder builder = new Uri.Builder();
        //TODO czytane z bazy danych
        builder.scheme("http")
                .authority("new.meteo.pl")
                .appendPath("um")
                .appendPath("metco")
                .appendPath("mgram_pict.php")
                .appendQueryParameter("ntype","0u")
                .appendQueryParameter("fdate","2015061812")
                .appendQueryParameter("row","466")
                .appendQueryParameter("col", "232")
                .appendQueryParameter("lang", "pl").build();

        try{
            URL meteoUrl = new URL(builder.toString());
            Log.v(MainActivity.TAG, meteoUrl.toString());

            URLConnection urlConnection = meteoUrl.openConnection();
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
