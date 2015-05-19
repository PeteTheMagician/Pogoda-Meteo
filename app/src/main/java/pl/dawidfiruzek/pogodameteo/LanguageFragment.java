package pl.dawidfiruzek.pogodameteo;


import android.os.Bundle;
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

    public static String LANGUAGE_TAG = "LanguageFragment";

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
        switch (view.getId()){
            case R.id.button_polish:
                Toast.makeText(getActivity(), "buttonPolishClicked", Toast.LENGTH_SHORT).show();
                //set language in settings to polish
                break;
            case R.id.button_english:
                Toast.makeText(getActivity(), "buttonEnglishClicked", Toast.LENGTH_SHORT).show();
                //set language in settings to english
                break;
            default:
                Log.e(LANGUAGE_TAG, "Incorrect button id");
                break;
        }
        getActivity().getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.abc_fade_in, R.anim.abc_fade_out, R.anim.abc_fade_in, R.anim.abc_fade_out)
                .replace(R.id.container, new GridSelectorFragment())
                .addToBackStack(null)
                .commit();
    }

}
