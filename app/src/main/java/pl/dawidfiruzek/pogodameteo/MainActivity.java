package pl.dawidfiruzek.pogodameteo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {

    public static final String TAG = "Pogoda Meteo";
    public static final String TYPE = "FRAGMENT_TYPE";
    public static final String FRAGMENT_TAG = "WEATHER_FRAGMENT_TAG";
    public enum FRAGMENT_TYPE {
        COMMENT,
        FAVOURITES,
        SETTINGS,
        INFO,
        SEARCH
    }
    private SharedPreferences preferenceManager;
    private ListView drawerList;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private ArrayList<NavigationListItem> navigationDrawerItems = new ArrayList<NavigationListItem>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /**
         * On the first start of the app new activity to set default values is run.
         * We set there default language, default grid, default update method and
         * if update by city is selected user choose that city.
         * We can change those settings later in the Settings.
         * In case of any further runs, MainActivity will be run and WeatherFragment
         * will be loaded.
         */
        super.onCreate(savedInstanceState);
        this.preferenceManager = PreferenceManager.getDefaultSharedPreferences(this);
        setActionBarTitle();
        setActionBarBlueColor();

        if(isFirstLaunch()){
            startFirstLaunchActivity();
        }
        else {
            Log.d(TAG, "Loading WeatherFragment");
            setContentView(R.layout.activity_main);
            if (savedInstanceState == null) {
                startWeatherFragment();
            }
            setNavigationDrawer();
        }
    }

    /**
     * Overriding methods to make NavigationDrawer works as supposed:
     * Opening when onMenu is clicked and closing when onpened and onMenu
     * or onBack is clicked
     * If Legend is opened - onBack closing it
     * */
    @Override
    protected void onPostCreate(Bundle savedInstanceState){
        super.onPostCreate(savedInstanceState);
        this.drawerToggle.syncState();
    }

    @Override
    public void onBackPressed() {
        ImageView legendView = (ImageView) findViewById(R.id.image_legend);
        if(this.drawerLayout.isDrawerOpen(this.drawerList)){
            this.drawerLayout.closeDrawers();
        }
        else if(isLegendToHide(legendView)){
            legendView.setVisibility(View.INVISIBLE);
        }
        else super.onBackPressed();
    }

    private boolean isLegendToHide(ImageView legendView) {
        return getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT && legendView.getVisibility() == View.VISIBLE;
    }

    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        if(! this.drawerLayout.isDrawerOpen(this.drawerList)){
            this.drawerLayout.openDrawer(this.drawerList);
        }
        else this.drawerLayout.closeDrawers();
        return super.onMenuOpened(featureId, menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_refresh){
            updateWeather();
            return true;
        }
        if (id == R.id.action_search_city) {
            startDetailedActivityWithSearchFragment();
            this.drawerLayout.closeDrawers();
            return true;
        }
        if(id == R.id.action_show_legend){
            handleLegendVisibility();
            this.drawerLayout.closeDrawers();
            return true;
        }
        // Activate navigation drawer toggle
        if(this.drawerToggle.onOptionsItemSelected(item)){
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void handleLegendVisibility() {
        ImageView legendView = (ImageView) findViewById(R.id.image_legend);
        if(legendView.getVisibility() == View.INVISIBLE) {
            legendView.setVisibility(View.VISIBLE);
        }
        else legendView.setVisibility(View.INVISIBLE);
    }

    private void setActionBarTitle() {
        String updateMethod = this.preferenceManager.getString("update_preference", "gps");
        Log.d(TAG, updateMethod);
        if(updateMethod.equals("gps")){
            getSupportActionBar().setTitle("GPS");
        }
        else {
            //TODO get city name from prefs
            getSupportActionBar().setTitle("City");
        }
    }

    private void setActionBarBlueColor() {
        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.color.blue));
    }

    private Boolean isFirstLaunch(){
        return this.preferenceManager.getBoolean("first_time_launch_preference", true);
    }

    private void startFirstLaunchActivity() {
        Intent intent = new Intent(this, FirstLaunchActivity.class);
        startActivity(intent);
        finish();
    }

    private void startWeatherFragment() {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, new WeatherFragment(), FRAGMENT_TAG)
                .commit();
    }

    private void setNavigationDrawer() {
        /**
         * Loading Titles, Subtitles and Icons to populate and set NavigationDrawer as enabled
         */
        this.drawerLayout = (DrawerLayout) findViewById(R.id.main_activity_drawer_layout);
        this.drawerList = (ListView) findViewById(R.id.start_activity_left_drawer_list);

        populateNavigationDrawerItems();
        setDrawerListAdapter();
        this.drawerToggle = getActionBarDrawerToggle();
        this.drawerToggle.setDrawerIndicatorEnabled(true);
        this.drawerLayout.setDrawerListener(this.drawerToggle);

        setHomeButtonForNavigationDrawer();

        this.drawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onClickNavigationDrawerItem(position);
            }
        });

        this.drawerList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                onLongClickNavigationDrawerItem(position);
                return true;
            }
        });
    }

    private void populateNavigationDrawerItems() {
        String[] navigationDrawerTitles = getResources().getStringArray(R.array.navigation_drawer_titles);
        String[] navigationDrawerSubtitles = getResources().getStringArray(R.array.navigation_drawer_subtitles);
        TypedArray navigationDrawerIcons = getResources().obtainTypedArray(R.array.navigation_drawer_icons);

        for (int i = 0; i < navigationDrawerTitles.length; ++i) {
            this.navigationDrawerItems.add(new NavigationListItem(navigationDrawerTitles[i], navigationDrawerSubtitles[i], navigationDrawerIcons.getResourceId(i, -1)));
        }

        navigationDrawerIcons.recycle();
    }

    private void setDrawerListAdapter() {
        DrawerListAdapter adapter = new DrawerListAdapter(this, this.navigationDrawerItems);
        this.drawerList.setAdapter(adapter);
    }

    private void setHomeButtonForNavigationDrawer() {
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @NonNull
    private ActionBarDrawerToggle getActionBarDrawerToggle() {
        return new ActionBarDrawerToggle(
                this,
                this.drawerLayout,
                R.string.open_drawer,
                R.string.close_drawer) {

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                supportInvalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                supportInvalidateOptionsMenu();
            }
        };
    }

    private void onClickNavigationDrawerItem(int position) {
        /**
         * Handling onClickEvents for NavigationDrawer Items
         * After clicking update method preferences are updated and the last
         * choosen update option is default one.
         * Other options - Comment ... Info - open another activity.
         * */
        switch(position){
            case 0: //GPS based weather
                //TODO string + coords?
                setUpdateMethod("gps");
                setActionBarTitle();
                updateWeather();
                break;
            case 1: //City based weather
                //TODO get city name from prefs
                setUpdateMethod("city");
                setActionBarTitle();
                updateWeather();
                break;
            case 2: //Comment
                startDetailedActivityWithCustomFragment(FRAGMENT_TYPE.COMMENT);
                break;
            case 3: //Favourite cities
                startDetailedActivityWithCustomFragment(FRAGMENT_TYPE.FAVOURITES);
                break;
            case 4: //Settings
                startDetailedActivityWithCustomFragment(FRAGMENT_TYPE.SETTINGS);
                break;
            case 5: //Info
                startDetailedActivityWithCustomFragment(FRAGMENT_TYPE.INFO);
                break;
            default:
                Log.e(TAG, "Unexpected navigation drawer item id");
                break;
        }
        this.drawerLayout.closeDrawers();
    }

    private void setUpdateMethod(String field) {
        this.preferenceManager.edit().putString("update_preference", field).apply();
    }

    private void startDetailedActivityWithCustomFragment(FRAGMENT_TYPE fragmentType){
        Intent intent = new Intent(this, DetailedActivity.class);
        intent.putExtra(TYPE, fragmentType);
        startActivity(intent);
    }

    private void onLongClickNavigationDrawerItem(int position) {
        /**
         * Setting onLongClick listener only to updating by city
         * We do it to open searchFragment to find and set default city for updates
         * */
        switch (position){
            case 1://City based weather
                Toast.makeText(this, "dupadupa", Toast.LENGTH_SHORT).show();
                startDetailedActivityWithSearchFragment();
                break;
            default:
                break;
        }
    }

    private void startDetailedActivityWithSearchFragment() {
        Intent intent = new Intent(this, DetailedActivity.class);
        intent.putExtra(TYPE, FRAGMENT_TYPE.SEARCH);
        startActivity(intent);
    }

    private void updateWeather() {
        WeatherFragment weatherFragment = (WeatherFragment) getSupportFragmentManager()
                .findFragmentByTag(FRAGMENT_TAG);

        if(weatherFragment != null){
            weatherFragment.onUpdateWeatherFromWeb();
        }
        else {
            Log.d(TAG, "Cannot find fragment by tag - starting new one");
            startWeatherFragment();
        }
    }
}
