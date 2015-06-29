package pl.dawidfiruzek.pogodameteo;

import android.os.AsyncTask;
import android.text.Html;
import android.util.Log;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.net.URL;

/**
 * Created by fks on 2015-06-28.
 */
public class FetchCommentTask extends AsyncTask <Void, Void, String> {

    private TextView mCommentText;
    FetchCommentTask(TextView textView){
        mCommentText = textView;
    }
    @Override
    protected String doInBackground(Void... params) {
        String downloadedText = null;

        try {
            URL url = new URL("http://www.meteo.pl/komentarze/index1.php");

            Document document = Jsoup.connect(url.toString()).timeout(10000).get();
            Element comment = document.select("div").last();
            Log.d(MainActivity.TAG, comment.toString());

            downloadedText = comment.toString();
            if(downloadedText != null){
                downloadedText = downloadedText
                        .replaceAll("</*div.*>", "");
            }
            else {
                Log.e(MainActivity.TAG, "Unsuccessfull comment fetching");
            }

            Log.d(MainActivity.TAG, downloadedText);
        } catch (Exception e) {
            Log.e(MainActivity.TAG, "Unsuccessful comment downloading");
        }
        return downloadedText;
    }

    @Override
    protected void onPostExecute(String s) {
        mCommentText.setText(Html.fromHtml(s));
    }
}
