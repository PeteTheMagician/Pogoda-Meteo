package pl.dawidfiruzek.pogodameteo.activities;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import pl.dawidfiruzek.pogodameteo.R;

/**
 * Created by fks on 2015-11-26.
 */
public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewId());
        ButterKnife.bind(this);

        setScreenOrientation();
    }

    public int getContentViewId() {
        return R.layout.activity_container;
    }

    private void setScreenOrientation() {
        boolean portraitModeOnly = getResources().getBoolean(R.bool.portrait_mode_only);

        if(portraitModeOnly) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }
}
