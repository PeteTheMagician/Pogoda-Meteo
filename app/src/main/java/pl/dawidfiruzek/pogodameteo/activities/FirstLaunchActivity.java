package pl.dawidfiruzek.pogodameteo.activities;

import android.os.Bundle;

import pl.dawidfiruzek.pogodameteo.R;
import pl.dawidfiruzek.pogodameteo.fragments.LanguageSelectorFragment;

public class FirstLaunchActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.default_container, new LanguageSelectorFragment())
                    .commit();
        }
    }
}
