package pl.dawidfiruzek.pogodameteo.networking;

import android.os.AsyncTask;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.net.URL;

import pl.dawidfiruzek.pogodameteo.activities.MainActivity;
import pl.dawidfiruzek.pogodameteo.interfaces.AsyncCommentResponse;

public class FetchCommentTask extends AsyncTask <Void, Void, String> {
    public AsyncCommentResponse delegate = null;

    @Override
    protected String doInBackground(Void... params) {
        String downloadedText = null;

        try {
            URL url = new URL("http://www.meteo.pl/komentarze/index1.php");

            Document document = Jsoup.connect(url.toString()).timeout(10000).get();
            Element comment = document.select("div").get(3);
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
    protected void onPostExecute(String result) {
        delegate.setDownloadedComment(result);
    }
}
