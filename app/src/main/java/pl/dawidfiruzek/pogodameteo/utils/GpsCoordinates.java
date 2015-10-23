package pl.dawidfiruzek.pogodameteo.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;

import pl.dawidfiruzek.pogodameteo.activities.MainActivity;
import pl.dawidfiruzek.pogodameteo.interfaces.AsyncGpsResponse;

/**
 * Created by fks on 2015-10-23.
 */
public class GpsCoordinates extends AsyncTask<Void, Void, Void> {
    public AsyncGpsResponse delegate = null;
    private Context context;
    SharedPreferences sharedPreferences;

    public GpsCoordinates(Context context){
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    @Override
    protected Void doInBackground(Void... params) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String bestProvider = locationManager.getBestProvider(criteria, false);
        Location location = locationManager.getLastKnownLocation(bestProvider);
        Double latitude, longitude;
        try {
            latitude = location.getLatitude();
            longitude = location.getLongitude();
            Log.e(MainActivity.TAG, "GPS coordinates lat: " + latitude + " lon: "+ longitude);

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("preferences_gps_latitude", latitude.toString());
            editor.putString("preferences_gps_longitude", longitude.toString());
            editor.commit();
        }
        catch (NullPointerException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        delegate.fetchWeatherWithCoordinates();
        super.onPostExecute(aVoid);
    }
}
