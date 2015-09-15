package pl.dawidfiruzek.pogodameteo;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class GridSelectorFragment extends Fragment implements View.OnClickListener {

    public GridSelectorFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_grid_selector, container, false);

        v.findViewById(R.id.button_grid_um).setOnClickListener(this);
        v.findViewById(R.id.button_grid_coamps).setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        switch (v.getId()){
            case R.id.button_grid_um:
                Toast.makeText(getActivity(), "UM grid selected", Toast.LENGTH_SHORT).show();
                editor.putString("grid_preference", "um");
                editor.commit();
                break;
            case R.id.button_grid_coamps:
                Toast.makeText(getActivity(), "COAMPS grid selected", Toast.LENGTH_SHORT).show();
                editor.putString("grid_preference", "coamps");
                editor.commit();
                break;
            default:
                Log.e(MainActivity.TAG, "Incorrect button id");
                break;
        }
        getActivity().getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.abc_fade_in, R.anim.abc_fade_out, R.anim.abc_fade_in, R.anim.abc_fade_out)
                .replace(R.id.first_start_container, new UpdateMethodSelectorFragment())
                .addToBackStack(null)
                .commit();
    }
}
