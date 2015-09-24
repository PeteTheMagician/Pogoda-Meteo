package pl.dawidfiruzek.pogodameteo;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

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

        setActionBarParams();
    }

    private void setActionBarParams() {
        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.color.blue));
        //TODO make a string value
        getSupportActionBar().setTitle("Initial settings");
    }
}
