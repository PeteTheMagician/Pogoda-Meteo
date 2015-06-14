package pl.dawidfiruzek.pogodameteo;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;


/**
 * A placeholder fragment containing a simple view.
 */
public class WeatherFragment extends Fragment {

    View v;
    public WeatherFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        v = inflater.inflate(R.layout.fragment_weather, container, false);

        WindowManager wm = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        int width = display.getWidth();
        ImageView iv = (ImageView)v.findViewById(R.id.image_legend);
        iv.setMaxWidth(width/2);

        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.action_show_legend){
            ImageView image = (ImageView) v.findViewById(R.id.image_legend);
            if(image.getVisibility() == View.INVISIBLE) {
                image.setVisibility(View.VISIBLE);
            }
            else image.setVisibility(View.INVISIBLE);
            return true;
        }


        return super.onOptionsItemSelected(item);
    }
}
