package com.example.getcountryinfo;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatDelegate;

import com.larvalabs.svgandroid.SVG;
import com.larvalabs.svgandroid.SVGParser;

import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;

public class FetchSVGInfo extends AsyncTask<Void, Void, Drawable> {
    private static final String LOG_TAG2 = FetchSVGInfo.class.getSimpleName();
    public interface AsyncResponse{
        void processFinishSVG(Drawable outptDrawable);
    }
    public AsyncResponse delegate = null;
    public FetchSVGInfo(AsyncResponse delegate) {
        this.delegate = delegate;
    }
    @Override
    protected Drawable doInBackground(Void... params) {
        try {
            final URL url = new URL("https://restcountries.eu/data/tur.svg");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = urlConnection.getInputStream();
            SVG svg = SVGParser. getSVGFromInputStream(inputStream);
            Drawable drawable = svg.createPictureDrawable();
            return drawable;
        } catch (Exception e) {
            //Log.e("MainActivity", e.getMessage(), e);
            e.printStackTrace();
        }
        Log.d(LOG_TAG2, "done");
        return null;
    }

    @Override
    protected void onPostExecute(Drawable drawable) {
        // Update the view
        delegate.processFinishSVG(drawable);
    }

}
//https://stackoverflow.com/questions/28215625/androidload-svg-file-from-web-and-show-it-on-image-view
//instructions add svgandroid https://github.com/pents90/svg-android/tree/master/svgandroid
//instructions to add to library: https://stackoverflow.com/questions/25610727/adding-external-library-in-android-studio