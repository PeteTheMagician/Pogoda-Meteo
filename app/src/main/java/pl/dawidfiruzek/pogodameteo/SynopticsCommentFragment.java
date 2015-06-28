package pl.dawidfiruzek.pogodameteo;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class SynopticsCommentFragment extends Fragment {


    public SynopticsCommentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_synoptics_comment, container, false);

        FetchComment fetchComment = new FetchComment((TextView) v.findViewById(R.id.text_comment));
        fetchComment.execute();
        Toast.makeText(getActivity(), "Comment", Toast.LENGTH_LONG).show();
        return v;
    }


}
