package pl.dawidfiruzek.pogodameteo;

import android.graphics.Bitmap;

/**
 * Created by fks on 2015-09-09.
 */
public interface AsyncWeatherResponse {
    void setDownloadedWeatherImage(Bitmap output);
}
