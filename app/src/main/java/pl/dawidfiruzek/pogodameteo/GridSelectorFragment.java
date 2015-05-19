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
public class GridSelectorFragment extends Fragment implements View.OnClickListener {

    public static String GRID_SELECT_TAG = "GridSelectorFragment";

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
        switch (v.getId()){
            case R.id.button_grid_um:
                Toast.makeText(getActivity(), "UM grid selected", Toast.LENGTH_SHORT).show();
                //TODO set UM grid to settings
                break;
            case R.id.button_grid_coamps:
                Toast.makeText(getActivity(), "COAMPS grid selected", Toast.LENGTH_SHORT).show();
                //TODO set COAMPS grid to settings
                break;
            default:
                Log.e(GRID_SELECT_TAG, "Incorrect button id");
                break;
        }
        getActivity().getSupportFragmentManager().popBackStack(null, getActivity().getSupportFragmentManager().POP_BACK_STACK_INCLUSIVE);
        getActivity().finish();
    }
}
