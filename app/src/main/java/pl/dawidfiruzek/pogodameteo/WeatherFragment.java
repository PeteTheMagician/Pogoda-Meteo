package pl.dawidfiruzek.pogodameteo;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;


/**
 * A placeholder fragment containing a simple view.
 */
public class WeatherFragment extends Fragment {

    View v;
    ImageView legendView;
    ImageView weatherView;
    int width;
    int height;

    public WeatherFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        v = inflater.inflate(R.layout.fragment_weather, container, false);
        Toast.makeText(getActivity(), "testdupatest", Toast.LENGTH_SHORT).show();

        WindowManager wm = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        height = display.getHeight();
        weatherView = (ImageView)v.findViewById(R.id.image_weather);
        legendView = (ImageView)v.findViewById(R.id.image_legend);

        setLegendImage();

        //around original proportion of the image h/w = 2.035714285714286
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            legendView.setMaxWidth(height / 2);
            legendView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    int action = event.getActionMasked();
                    //TODO make legend invisible on swipe
                    if(action == MotionEvent.ACTION_MOVE){
                        legendView.setVisibility(View.INVISIBLE);
                    }
                    return true;
                }
            });
        }
        onFetchWeather();

        return v;
    }

    public void setLegendImage() {
        //setting legend for corresponding language
        SharedPreferences preferenceManager = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String model = preferenceManager.getString("grid_preference", "um");
        String language = preferenceManager.getString("language_preference", "pl");
        if(language.equals("pl")){
            if(model.equals("um")){
                legendView.setImageResource(R.drawable.leg60_pl);
            }
            else if(model.equals("coamps")){
                legendView.setImageResource(R.drawable.leg84_pl);
            }
            else {
                Log.e(MainActivity.TAG, "Unexpectec model for Polish language");
            }
        }
        else if(language.equals("en")){
            if(model.equals("um")){
                legendView.setImageResource(R.drawable.leg60_en);
            }
            else if(model.equals("coamps")){
                legendView.setImageResource(R.drawable.leg84_en);
            }
            else {
                Log.e(MainActivity.TAG, "Unexpectec model for English language");
            }
        }
        else{
            Log.e(MainActivity.TAG, "Unexpectec model");
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.action_show_legend){
            ImageView image = (ImageView) v.findViewById(R.id.image_legend);
            if(image.getVisibility() == View.INVISIBLE) {
                image.setVisibility(View.VISIBLE);
            }
            else image.setVisibility(View.INVISIBLE);
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    public void onUpdateWeatherFromWeb(MainActivity.ICON_CLICKED icon){
        switch (icon){
            case GPS:
                Toast.makeText(getActivity(), "GPS", Toast.LENGTH_SHORT).show();
                break;
            case REFRESH:
                //TODO handle it in proper way
            case CITY:
                onFetchWeather();
                break;
            default:
                Log.e(MainActivity.TAG, "Unexpected button passed to update weather");
        }
    }

    private void onFetchWeather() {
        FetchWeatherTask fetchWeatherTask = new FetchWeatherTask(getActivity(), weatherView);
        fetchWeatherTask.execute();
        Toast.makeText(getActivity(), "CITY", Toast.LENGTH_SHORT).show();
    }
}
