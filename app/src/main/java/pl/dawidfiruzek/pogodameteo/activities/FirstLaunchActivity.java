package pl.dawidfiruzek.pogodameteo.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import pl.dawidfiruzek.pogodameteo.fragments.LanguageSelectorFragment;
import pl.dawidfiruzek.pogodameteo.R;

public class FirstLaunchActivity extends ActionBarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_start);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.first_start_container, new LanguageSelectorFragment())
                    .commit();
        }

        setActionBarTitle();
    }

    private void setActionBarTitle() {
        //TODO make a string value
        getSupportActionBar().setTitle("Initial settings");
    }
}