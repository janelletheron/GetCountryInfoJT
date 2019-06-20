package com.example.getcountryinfo;

import android.net.Uri;
import android.util.JsonReader;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class NetworkUtils { //outputs JSON

    private static final String LOG_TAG = NetworkUtils.class.getSimpleName();

    // Base URL for Books API.

    static String getCountryInfo(String queryString){ //takes search term
    //static String getCountryInfo(URL queryString){
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String countryJSONString = null;

        try {
            URL requestURL = new URL(queryString);
            urlConnection = (HttpURLConnection) requestURL.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setConnectTimeout(15*1000);
            urlConnection.connect();
            if (urlConnection.getResponseCode() == 200) {
                // Get the InputStream.
                InputStream inputStream = urlConnection.getInputStream();

// Create a buffered reader from that input stream.
                reader = new BufferedReader(new InputStreamReader(inputStream));

// Use a StringBuilder to hold the incoming response.
                StringBuilder builder = new StringBuilder();

                String line;
                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                    // Since it's JSON, adding a newline isn't necessary (it won't
                    // affect parsing) but it does make debugging a *lot* easier
                    // if you print out the completed buffer for debugging.
                    builder.append("\n");
                }
                if (builder.length() == 0) {
                    // Stream was empty. No point in parsing.
                    return null;
                }
                countryJSONString = builder.toString();
            }
            else{
                Log.d(LOG_TAG, Integer.toString(urlConnection.getResponseCode()));
                countryJSONString = "empty";
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        Log.d(LOG_TAG, countryJSONString);
        return countryJSONString;
    }
}
