package pl.dawidfiruzek.pogodameteo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class SearchFragment extends Fragment {
    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_search, container, false);
        v.findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String referenceActivity = FirstLaunchActivity.class.getSimpleName();
                String currentActivity = getActivity().getTitle().toString();
                // TODO delete button and replace it by choosing city - mechanism stays the same
                if(v.getId() == R.id.button) {
                    if (currentActivity.equals(referenceActivity)) {
                        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putBoolean("first_time_launch_preference", false);
                        editor.commit();

                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        startActivity(intent);
                        getActivity().finish();
                    }
                    else{
                        //TODO handle it in normal way
                        Toast.makeText(getActivity(), "kopasdlkfjasfdlkj", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        return v;
    }
}
