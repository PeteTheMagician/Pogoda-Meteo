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
public class LanguageFragment extends Fragment implements View.OnClickListener {

    public LanguageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_language, container, false);

        v.findViewById(R.id.button_polish).setOnClickListener(this);
        v.findViewById(R.id.button_english).setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View view){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        switch (view.getId()){
            case R.id.button_polish:
                Toast.makeText(getActivity(), "buttonPolishClicked", Toast.LENGTH_SHORT).show();
                editor.putString("language_preference", "pl");
                editor.commit();
                break;
            case R.id.button_english:
                Toast.makeText(getActivity(), "buttonEnglishClicked", Toast.LENGTH_SHORT).show();
                editor.putString("language_preference", "en");
                editor.commit();
                break;
            default:
                Log.e(MainActivity.TAG, "Incorrect button id");
                break;
        }
        getActivity().getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.abc_fade_in, R.anim.abc_fade_out, R.anim.abc_fade_in, R.anim.abc_fade_out)
                .replace(R.id.firststartcontainer, new GridSelectorFragment())
                .addToBackStack(null)
                .commit();
    }

}
