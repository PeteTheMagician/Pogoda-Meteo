package pl.dawidfiruzek.pogodameteo;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.MenuItem;


public class DetailedActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);

        MainActivity.FRAGMENT_TYPE fragmentType = (MainActivity.FRAGMENT_TYPE)getIntent().getSerializableExtra(MainActivity.TYPE);
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

    private void startDetailedFragment(MainActivity.FRAGMENT_TYPE fragmentType) {
        android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
        switch(fragmentType) {
            case SEARCH:
                manager
                        .beginTransaction()
                        .add(R.id.detailed_container, new SearchFragment())
                        .commit();
                break;
            case COMMENT:
                manager
                        .beginTransaction()
                        .add(R.id.detailed_container, new SynopticsCommentFragment())
                        .commit();
                break;
            case FAVOURITES:
                manager
                        .beginTransaction()
                        .add(R.id.detailed_container, new CitiesFragment())
                        .commit();
                break;
            case SETTINGS:
                manager
                        .beginTransaction()
                        .add(R.id.detailed_container, new SettingsFragment())
                        .commit();
                break;
            case INFO:
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

    private void setActionBarTitle(MainActivity.FRAGMENT_TYPE fragmentType) {
        switch(fragmentType) {
            case SEARCH:
                getSupportActionBar().setTitle("Search");
                break;
            case COMMENT:
                getSupportActionBar().setTitle("Comment");
                break;
            case FAVOURITES:
                getSupportActionBar().setTitle("Favourites");
                break;
            case SETTINGS:
                getSupportActionBar().setTitle("Settings");
                break;
            case INFO:
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
