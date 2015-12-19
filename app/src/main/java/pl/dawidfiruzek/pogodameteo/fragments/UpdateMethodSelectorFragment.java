package pl.dawidfiruzek.pogodameteo.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import pl.dawidfiruzek.pogodameteo.R;
import pl.dawidfiruzek.pogodameteo.activities.MainActivity;

public class UpdateMethodSelectorFragment extends Fragment implements View.OnClickListener {
    public UpdateMethodSelectorFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_update_method_selector, container, false);

        v.findViewById(R.id.button_use_gps).setOnClickListener(this);
        v.findViewById(R.id.button_use_city).setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Intent intent = new Intent(getActivity(), MainActivity.class);
        switch(v.getId()){
            case R.id.button_use_gps:
                editor.putString("update_preference", "gps");
                startActivity(intent);
                getActivity().finish();
                editor.putBoolean("first_time_launch_preference", false);
                editor.commit();
                break;
            case R.id.button_use_city:
                editor.putString("update_preference", "city");
                getActivity().getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.abc_fade_in, R.anim.abc_fade_out, R.anim.abc_fade_in, R.anim.abc_fade_out)
                        .replace(R.id.default_container, new SearchFragment())
                        .addToBackStack(null)
                        .commit();
                break;
            default:
                Log.e(MainActivity.TAG, "Button doesn't exist with that id");
        }
    }
}
