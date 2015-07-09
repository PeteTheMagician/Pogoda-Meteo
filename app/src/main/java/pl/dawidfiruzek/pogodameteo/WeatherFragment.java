package pl.dawidfiruzek.pogodameteo;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


/**
 * Fragment containing Weather Image fetched from the web
 */
public class WeatherFragment extends Fragment {

    ImageView mWeatherView;
    ImageView mLegendView;

    public WeatherFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_weather, container, false);
        mWeatherView = (ImageView)view.findViewById(R.id.image_weather);
        mLegendView = (ImageView)view.findViewById(R.id.image_legend);

        //TODO is it necessary??
//        /**
//         * Setting Legend size
//         * */
//        WindowManager wm = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
//        Display display = wm.getDefaultDisplay();
//        int height = display.getHeight();
//
//        //around original proportion of the image h/w = 2.035714285714286
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
//            mLegendView.setMaxWidth(height / 2);
            mLegendView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    int action = event.getActionMasked();
                    //TODO make legend invisible on swipe
                    if(action == MotionEvent.ACTION_MOVE){
                        mLegendView.setVisibility(View.INVISIBLE);
                    }
                    return true;
                }
            });
        }
        onUpdateWeatherFromWeb();
        
        Log.d(MainActivity.TAG, "WeatherFragment Created");
        return view;
    }

    /**
     * Fetching weather from the Web*/
    public void onUpdateWeatherFromWeb(){
        FetchWeatherTask fetchWeatherTask = new FetchWeatherTask(getActivity(), mWeatherView);
        fetchWeatherTask.execute();
        setLegendImage();
        Log.d(MainActivity.TAG, "Started fetching weather from web");
    }
    /**
     * Setting Legend image corresponding to choosen Update Method and Language
     * */
    public void setLegendImage() {
        SharedPreferences preferenceManager = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String model = preferenceManager.getString("grid_preference", "um");
        String language = preferenceManager.getString("language_preference", "pl");
        if(language.equals("pl")){
            if(model.equals("um")){
                mLegendView.setImageResource(R.drawable.leg60_pl);
            }
            else if(model.equals("coamps")){
                mLegendView.setImageResource(R.drawable.leg84_pl);
            }
            else {
                Log.e(MainActivity.TAG, "Unexpectec model for Polish language");
            }
        }
        else if(language.equals("en")){
            if(model.equals("um")){
                mLegendView.setImageResource(R.drawable.leg60_en);
            }
            else if(model.equals("coamps")){
                mLegendView.setImageResource(R.drawable.leg84_en);
            }
            else {
                Log.e(MainActivity.TAG, "Unexpectec model for English language");
            }
        }
        else{
            Log.e(MainActivity.TAG, "Unexpectec model");
        }
    }
}
