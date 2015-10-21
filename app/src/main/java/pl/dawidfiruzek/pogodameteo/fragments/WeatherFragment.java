package pl.dawidfiruzek.pogodameteo.fragments;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import pl.dawidfiruzek.pogodameteo.interfaces.AsyncWeatherResponse;
import pl.dawidfiruzek.pogodameteo.networking.FetchWeatherTask;
import pl.dawidfiruzek.pogodameteo.activities.MainActivity;
import pl.dawidfiruzek.pogodameteo.R;

public class WeatherFragment extends Fragment implements AsyncWeatherResponse {
    FetchWeatherTask fetchWeatherTask;
    ImageView weatherView;
    ImageView legendView;

    public WeatherFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_weather, container, false);
        this.weatherView = (ImageView)view.findViewById(R.id.image_weather);
        this.legendView = (ImageView)view.findViewById(R.id.image_legend);
        setLegendListenerOnPortraitOrientation();
        downloadAndSetWeather();
        setLegendImage();

        Log.d(MainActivity.TAG, "WeatherFragment Created");
        return view;
    }

    @Override
    public void onPause() {
        this.fetchWeatherTask.cancel(true);
        super.onPause();
    }

    @Override
    public void setDownloadedWeatherImage(Bitmap output) {
        this.weatherView.setImageBitmap(output);
    }

    public void downloadAndSetWeather(){
        Log.d(MainActivity.TAG, "Started fetching weather from web");
        this.fetchWeatherTask = new FetchWeatherTask(getActivity());
        this.fetchWeatherTask.delegate = this;
        this.fetchWeatherTask.execute();
    }

    public void setLegendImage() {
        SharedPreferences preferenceManager = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String model = preferenceManager.getString("grid_preference", "um");
        String language = preferenceManager.getString("language_preference", "pl");
        if(language.equals("pl")){
            if(model.equals("um")){
                this.legendView.setImageResource(R.drawable.leg60_pl);
            }
            else if(model.equals("coamps")){
                this.legendView.setImageResource(R.drawable.leg84_pl);
            }
            else {
                Log.e(MainActivity.TAG, "Unexpectec model for Polish language");
            }
        }
        else if(language.equals("en")){
            if(model.equals("um")){
                this.legendView.setImageResource(R.drawable.leg60_en);
            }
            else if(model.equals("coamps")){
                this.legendView.setImageResource(R.drawable.leg84_en);
            }
            else {
                Log.e(MainActivity.TAG, "Unexpectec model for English language");
            }
        }
        else{
            Log.e(MainActivity.TAG, "Unexpectec model");
        }
    }

    private void setLegendListenerOnPortraitOrientation() {
        if(isOrientationPortrait()) {
            this.legendView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    int action = event.getActionMasked();
                    //TODO make legend invisible on swipe
                    if(action == MotionEvent.ACTION_MOVE){
                        //TODO refractor code to call legend by this.legendView
                        legendView.setVisibility(View.INVISIBLE);
                    }
                    return true;
                }
            });
        }
    }

    private boolean isOrientationPortrait() {
        return getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
    }
}
