package pl.dawidfiruzek.pogodameteo.utils;

import android.graphics.drawable.Drawable;

/**
 * Created by fks on 2015-12-19.
 */
public class CityListEntryDataHandler {
    public int id;
    public int column;
    public int row;
    public String region;
    public String city;
    public String district;
    public int favourite;

    //test only
    public String cityName;
    public String cityRegion;
    public String cityDistrict;
    public Drawable cityFavourite;

    //test only
    public void setContent(String cityName, String cityRegion, String cityDistrict, Drawable cityFavourite) {
        this.cityName = cityName;
        this.cityRegion = cityRegion;
        this.cityDistrict = cityDistrict;
        this.cityFavourite = cityFavourite;
    }
}
