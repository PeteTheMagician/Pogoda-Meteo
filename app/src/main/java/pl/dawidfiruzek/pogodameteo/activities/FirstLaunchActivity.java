package pl.dawidfiruzek.pogodameteo.activities;

import android.os.Bundle;

import pl.dawidfiruzek.pogodameteo.R;
import pl.dawidfiruzek.pogodameteo.fragments.LanguageSelectorFragment;

public class FirstLaunchActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_start);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.first_start_container, new LanguageSelectorFragment())
                    .commit();
        }
    }
}
