package pl.dawidfiruzek.pogodameteo;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class SynopticsCommentFragment extends Fragment implements AsyncCommentResponse{

    private FetchCommentTask fetchCommentTask;
    private TextView textComment;

    public SynopticsCommentFragment() {
        // Required empty public constructor
    }


    @Override
    public void onPause() {
        this.fetchCommentTask.cancel(true);
        super.onPause();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_synoptics_comment, container, false);
        this.textComment = (TextView) v.findViewById(R.id.text_comment);

        this.fetchCommentTask = new FetchCommentTask();
        this.fetchCommentTask.delegate = this;
        this.fetchCommentTask.execute();
        Toast.makeText(getActivity(), "Comment", Toast.LENGTH_LONG).show();

        return v;
    }

    @Override
    public void processFinish(String output) {
        this.textComment.setMovementMethod(LinkMovementMethod.getInstance());
        this.textComment.setText(Html.fromHtml(output));
    }
}
