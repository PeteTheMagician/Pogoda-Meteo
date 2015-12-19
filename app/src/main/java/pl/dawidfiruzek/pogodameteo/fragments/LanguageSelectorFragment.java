package pl.dawidfiruzek.pogodameteo.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import pl.dawidfiruzek.pogodameteo.R;
import pl.dawidfiruzek.pogodameteo.activities.MainActivity;

public class LanguageSelectorFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "LanguageSelector";

    public LanguageSelectorFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_language_selector, container, false);

        v.findViewById(R.id.button_polish).setOnClickListener(this);
        v.findViewById(R.id.button_english).setOnClickListener(this);
        setEmptyActionBarTitle();

        return v;
    }

    private void setEmptyActionBarTitle(){
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("");
    }

    @Override
    public void onClick(View view){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        SharedPreferences.Editor editor = sharedPreferences.edit();

        switch (view.getId()){
            case R.id.button_polish:
                Log.d(TAG, "onClick: buttonPolish");
                editor.putString(getString(R.string.preference_category_language), getString(R.string.preference_language_polish));
                break;
            case R.id.button_english:
                Log.d(TAG, "onClick: buttonEnglish");
                editor.putString(getString(R.string.preference_category_language), getString(R.string.preference_language_english));
                break;
            default:
                Log.e(MainActivity.TAG, "Incorrect button id");
                return;
        }
        editor.commit();

        getActivity().getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.abc_fade_in, R.anim.abc_fade_out, R.anim.abc_fade_in, R.anim.abc_fade_out)
                .replace(R.id.first_start_container, new GridSelectorFragment())
                .addToBackStack(null)
                .commit();
    }
}
