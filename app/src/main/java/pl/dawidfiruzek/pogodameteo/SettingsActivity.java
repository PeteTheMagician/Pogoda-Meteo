package pl.dawidfiruzek.pogodameteo;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;


public class SettingsActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.settings_container, new SettingsFragment())
                    .commit();
        }
        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.color.darkblue));
        // TODO make string of it
        getSupportActionBar().setTitle("Settings");
    }
}
