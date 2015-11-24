package pl.dawidfiruzek.pogodameteo.views;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import pl.dawidfiruzek.pogodameteo.R;

/**
 * Created by fks on 2015-11-25.
 */
public class CityListEntryView extends RelativeLayout {

    TextView cityName;
    TextView cityRegion;
    TextView cityDistrict;
    ImageView cityFavourite;


    public CityListEntryView(Context context){
        super(context);
        init(context);
    }

    public CityListEntryView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CityListEntryView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        cityName = (TextView) findViewById(R.id.city_name);
        cityRegion = (TextView) findViewById(R.id.city_region);
        cityDistrict = (TextView) findViewById(R.id.city_region);
        cityFavourite = (ImageView) findViewById(R.id.city_favourite);
    }

    //TODO set actual content
    public void setContent(CityListEntryView element){
        cityName = element.cityName;
        cityRegion = element.cityRegion;
        cityDistrict = element.cityDistrict;
        cityFavourite = element.cityFavourite;
    }

    public void setContent(String cityName, String cityRegion, String cityDistrict, Drawable cityFavourite){
        this.cityName.setText(cityName);
        this.cityRegion.setText(cityRegion);
        this.cityDistrict.setText(cityDistrict);
        this.cityFavourite.setImageDrawable(cityFavourite);
    }
}
