package com.example.getcountryinfo;

import android.os.AsyncTask;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;

public class FetchJSONString extends AsyncTask<String, Void, String> { //queryCountry string, void=no progress indicator, string is JSON output

    public interface AsyncResponse{
        void processFinish(String output);
    }
    public AsyncResponse delegate = null;
    public FetchJSONString(AsyncResponse delegate){
        this.delegate = delegate;
    }

    @Override
    protected String doInBackground(String... strings) {
        return NetworkUtils.getCountryInfo(strings[0]);
    }

    @Override
    protected void onPostExecute(String s) {
           delegate.processFinish(s);
    }

    public static String JSONSearchNested(String s, String searchWord, String SecondWord){
        String output = JSONSearch(s, searchWord);
        if (output.startsWith("[")){ //if nested
            output = JSONSearch(output, SecondWord);
        }
        return output;
    }

    static String JSONSearch(String s, String searchWord) {
        String outputJSON = null;
        //StringBuilder builder = new StringBuilder(); this works but creates string that cannot then be clicked on, preferable to show ability to next page view
        try {
            //to obtain Json from results string
            JSONArray itemsArray = new JSONArray(s);
            int i = 0;
            while (i < itemsArray.length()) {// && outputJSON == null) {
                // Get the current item information
                JSONObject countryInfoJ = itemsArray.getJSONObject(i); //find queryWord in JSON object
                try {
                    //builder.append(countryInfoJ.getString(searchWord));
                    //builder.append("\n");
                    outputJSON = countryInfoJ.getString(searchWord);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                // Move to the next item.
                i++;
            }
            //If a matching response is found, update the UI with that response. Because the references to the TextView objects are WeakReference objects, you have to dereference them using the get() method.
            if (outputJSON != null) {
            //if(builder != null){
                //outputJSON = builder.toString();

                //mCountry.get().setText(outputJSON);
            } else {
                outputJSON = "No Results";
                //mCountry.get().setText(R.string.no_results);
            }
        } catch (JSONException e) {
            outputJSON = "No Results";
            //mCountry.get().setText(R.string.no_results);
            e.printStackTrace();
        }
        return outputJSON;
    }

}
