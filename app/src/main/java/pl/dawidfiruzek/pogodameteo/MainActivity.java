package pl.dawidfiruzek.pogodameteo;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
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

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {

    public static final String TAG = "Pogoda Meteo";
    public static final String TYPE = "FRAGMENT_TYPE";
    public static boolean firstStart = true;
    public enum SETTINGS_FRAGMENT{
        SEARCH,
        COMMENT,
        FAVOURITES,
        SETTINGS,
        INFO
    };

    private ListView mDrawerList;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private ArrayList<NavigationListItem> mNavItems = new ArrayList<NavigationListItem>();

    private String[] mNavDrawerTitles;
    private String[] mNavDrawerSubtitles;
    private TypedArray mNavDrawerIcons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new WeatherFragment())
                    .commit();
        }

        //TODO read from preferences
        if(firstStart){
            Intent intent = new Intent(this, FirstStartActivity.class);
            startActivity(intent);
            finish();
            //TODO set firstStart to false after set all initial prefs
        }

        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.color.darkblue));

        mNavDrawerTitles = getResources().getStringArray(R.array.navigation_drawer_titles);
        mNavDrawerSubtitles = getResources().getStringArray(R.array.navigation_drawer_subtitles);
        mNavDrawerIcons = getResources().obtainTypedArray(R.array.navigation_drawer_icons);

        for(int i = 0; i < mNavDrawerTitles.length; ++i){
            mNavItems.add(new NavigationListItem(mNavDrawerTitles[i], mNavDrawerSubtitles[i], mNavDrawerIcons.getResourceId(i,-1)));
        }

        mNavDrawerIcons.recycle();

        mDrawerLayout = (DrawerLayout) findViewById(R.id.main_activity_drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.start_activity_left_drawer_list);
        DrawerListAdapter adapter = new DrawerListAdapter(this, mNavItems);
        mDrawerList.setAdapter(adapter);

        mDrawerToggle = new ActionBarDrawerToggle(
                this,
                mDrawerLayout,
                R.string.open_drawer,
                R.string.close_drawer){

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

        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onClickNavigationDrawerItem(position);
            }
        });
    }

    private void onClickNavigationDrawerItem(int position) {
        Intent intent = new Intent(this, SettingsActivity.class);
        switch(position){
            case 0: //GPS based weather
                mDrawerLayout.closeDrawers();
                break;
            case 1: //City based weather
//                intent.putExtra(TYPE, SETTINGS_FRAGMENT.CITY);
                mDrawerLayout.closeDrawers();
                break;
            case 2: //Comment
                intent.putExtra(TYPE, SETTINGS_FRAGMENT.COMMENT);
                startActivity(intent);
                break;
            case 3: //Favourite cities
                intent.putExtra(TYPE, SETTINGS_FRAGMENT.FAVOURITES);
                startActivity(intent);
                break;
            //Settings
            case 4:
                intent.putExtra(TYPE, SETTINGS_FRAGMENT.SETTINGS);
                startActivity(intent);
                break;
            //Info
            case 5:
                intent.putExtra(TYPE, SETTINGS_FRAGMENT.INFO);
                startActivity(intent);
                break;
            default:
                Log.e(TAG, "Unexpected navigation drawer item id");
                break;
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState){
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onBackPressed() {
        ImageView legend = (ImageView)findViewById(R.id.image_legend);
        if(mDrawerLayout.isDrawerOpen(mDrawerList)){
            mDrawerLayout.closeDrawers();
        }
        if(legend.getVisibility() == View.VISIBLE){
            legend.setVisibility(View.INVISIBLE);
        }
        else super.onBackPressed();
    }

    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        if(! mDrawerLayout.isDrawerOpen(mDrawerList)){
            mDrawerLayout.openDrawer(mDrawerList);
        }
        else mDrawerLayout.closeDrawers();
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
        if (id == R.id.action_search_city) {
            Intent intent = new Intent(this, SettingsActivity.class);
            intent.putExtra(TYPE, SETTINGS_FRAGMENT.SEARCH);
            startActivity(intent);
            mDrawerLayout.closeDrawers();
            return true;
        }

        if(id == R.id.action_show_legend){
            //TODO show legend
            mDrawerLayout.closeDrawers();
            return false;
        }

        // Activate navigation drawer toggle
        if(mDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
