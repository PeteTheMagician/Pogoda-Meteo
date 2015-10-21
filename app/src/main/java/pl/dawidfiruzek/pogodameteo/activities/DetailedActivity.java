package pl.dawidfiruzek.pogodameteo.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.MenuItem;

import pl.dawidfiruzek.pogodameteo.R;
import pl.dawidfiruzek.pogodameteo.fragments.SynopticsCommentFragment;
import pl.dawidfiruzek.pogodameteo.fragments.AboutFragment;
import pl.dawidfiruzek.pogodameteo.fragments.CitiesFragment;
import pl.dawidfiruzek.pogodameteo.fragments.SearchFragment;
import pl.dawidfiruzek.pogodameteo.fragments.SettingsFragment;


public class DetailedActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);

        int fragmentType = getIntent().getIntExtra(MainActivity.TYPE, -1);
        if(savedInstanceState == null){
            startDetailedFragment(fragmentType);
        }
        setActionBarTitle(fragmentType);
        setActionBarBlueColor();
        setHomeButtonWithBeforeIcon();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void startDetailedFragment(int fragmentType) {
        android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
        switch(fragmentType) {
            case MainActivity.FRAGMENT_SEARCH:
                manager
                        .beginTransaction()
                        .add(R.id.detailed_container, new SearchFragment())
                        .commit();
                break;
            case MainActivity.FRAGMENT_COMMENT:
                manager
                        .beginTransaction()
                        .add(R.id.detailed_container, new SynopticsCommentFragment())
                        .commit();
                break;
            case MainActivity.FRAGMENT_FAVOURITES:
                manager
                        .beginTransaction()
                        .add(R.id.detailed_container, new CitiesFragment())
                        .commit();
                break;
            case MainActivity.FRAGMENT_SETTINGS:
                manager
                        .beginTransaction()
                        .add(R.id.detailed_container, new SettingsFragment())
                        .commit();
                break;
            case MainActivity.FRAGMENT_INFO:
                manager
                        .beginTransaction()
                        .add(R.id.detailed_container, new AboutFragment())
                        .commit();
                break;
            default:
                Log.e(MainActivity.TAG, "Unexpected fragment id passed from main activity");
                break;
        }
    }

    private void setActionBarTitle(int fragmentType) {
        switch(fragmentType) {
            case MainActivity.FRAGMENT_SEARCH:
                getSupportActionBar().setTitle("Search");
                break;
            case MainActivity.FRAGMENT_COMMENT:
                getSupportActionBar().setTitle("Comment");
                break;
            case MainActivity.FRAGMENT_FAVOURITES:
                getSupportActionBar().setTitle("Favourites");
                break;
            case MainActivity.FRAGMENT_SETTINGS:
                getSupportActionBar().setTitle("Settings");
                break;
            case MainActivity.FRAGMENT_INFO:
                getSupportActionBar().setTitle("Info");
                break;
            default:
                Log.e(MainActivity.TAG, "Unexpected fragment id passed from main activity");
                break;
        }
    }

    private void setActionBarBlueColor() {
        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.color.blue));
    }

    private void setHomeButtonWithBeforeIcon() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_navigate_before_white_24dp);
    }
}
