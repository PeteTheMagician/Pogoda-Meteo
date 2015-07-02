package pl.dawidfiruzek.pogodameteo;


import android.content.Intent;
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
                //TODO make it fucking work!
                final CharSequence test = "FirstStartActivity";
                Log.d(MainActivity.TAG, "\\" + getActivity().getTitle() + "\\ " + "FirstStartActivity");
                if(v.getId() == R.id.button) {
                    if (getActivity().getTitle() == test) {
                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        startActivity(intent);
                        getActivity().finish();
                    }
                    else{
                        Toast.makeText(getActivity(), "kopasdlkfjasfdlkj", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        return v;
    }


}
