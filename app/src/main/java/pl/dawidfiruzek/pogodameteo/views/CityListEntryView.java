package pl.dawidfiruzek.pogodameteo.views;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import pl.dawidfiruzek.pogodameteo.R;
import pl.dawidfiruzek.pogodameteo.utils.CityListEntryDataHandler;

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
        View.inflate(context, R.layout.view_city_list_entry, this);
        cityName = (TextView) findViewById(R.id.city_name);
        cityRegion = (TextView) findViewById(R.id.city_region);
        cityDistrict = (TextView) findViewById(R.id.city_district);
        cityFavourite = (ImageView) findViewById(R.id.city_favourite);
    }

    private void setContent(String cityName, String cityRegion, String cityDistrict, Drawable cityFavourite){
        this.cityName.setText(cityName);
        this.cityRegion.setText(cityRegion);
        this.cityDistrict.setText(cityDistrict);
        this.cityFavourite.setImageDrawable(cityFavourite);
    }

    public void setContent(CityListEntryDataHandler element){
        setContent(
                element.cityName,
                element.cityRegion,
                element.cityDistrict,
                element.cityFavourite
        );
    }

    public String getCity() {
        return cityName.getText().toString();
    }

    public String getRegion() {
        return cityRegion.getText().toString();
    }

    public String getDistrict() {
        return cityDistrict.getText().toString();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();

        if(action == MotionEvent.ACTION_DOWN) {
            setBackgroundColor(ContextCompat.getColor(getContext(), R.color.focused_backround));
        }
        else if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL) {
            setBackgroundColor(ContextCompat.getColor(getContext(), R.color.background));
        }

        return super.onTouchEvent(event);
    }
}
