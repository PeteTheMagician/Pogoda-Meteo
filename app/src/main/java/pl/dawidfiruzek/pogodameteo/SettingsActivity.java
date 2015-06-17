package pl.dawidfiruzek.pogodameteo;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.MenuItem;


public class SettingsActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        MainActivity.SETTINGS_FRAGMENT extra = (MainActivity.SETTINGS_FRAGMENT)getIntent().getSerializableExtra(MainActivity.TYPE);

        if(savedInstanceState == null){
            android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
            switch(extra) {
                case SEARCH:
                    manager
                            .beginTransaction()
                            .add(R.id.settings_container, new SearchFragment())
                            .commit();
                    getSupportActionBar().setTitle("Search");
                    break;
                case COMMENT:
                    manager
                        .beginTransaction()
                        .add(R.id.settings_container, new SynopticsCommentFragment())
                        .commit();
                    getSupportActionBar().setTitle("Comment");
                    break;
                case FAVOURITES:
                    manager
                        .beginTransaction()
                        .add(R.id.settings_container, new CitiesFragment())
                            .commit();
                    getSupportActionBar().setTitle("Favourites");
                    break;
                case SETTINGS:
                    manager
                        .beginTransaction()
                        .add(R.id.settings_container, new SettingsFragment())
                            .commit();
                    getSupportActionBar().setTitle("Settings");
                    break;
                case INFO:
                    manager
                        .beginTransaction()
                        .add(R.id.settings_container, new AboutFragment())
                        .commit();
                    getSupportActionBar().setTitle("Info");
                    break;
                default:
                    Log.e(MainActivity.TAG, "Unexpected fragment id passed from main activity");
                    break;

            }
        }
        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.color.darkblue));
        // TODO make string of it
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_navigate_before_white_24dp);
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
}
