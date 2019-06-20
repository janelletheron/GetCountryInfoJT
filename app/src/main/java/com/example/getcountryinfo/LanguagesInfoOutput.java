package com.example.getcountryinfo;

import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.TextView;

public class LanguagesInfoOutput extends AppCompatActivity {
    private TextView mLanguage;
    private TextView mLOutput;
    //private TextView extrasearch;
    //private TextView mIntermediate;
    //private static final String REST_BASE_URL = "https://restcountries.eu/rest/v2/name/";
    public static final String REST_LAN_URL = "https://restcountries.eu/rest/v2/lang/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_languages_info_output);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mLanguage = (TextView) findViewById(R.id.headingLanguages);
        mLOutput = (TextView) findViewById(R.id.languagesOutput);
        String queryString = getIntent().getStringExtra("query"); //this will be CountryName
        String isosearch = getIntent().getStringExtra("isoname");
        Uri builtURI = Uri.parse(REST_LAN_URL).buildUpon()
                .appendEncodedPath(isosearch)
                .build();

        //mLanguage.setText(queryString);
        FetchJSONString asyncTask = (FetchJSONString) new FetchJSONString(new FetchJSONString.AsyncResponse() {
            @Override
            public void processFinish(String output) {
                mLOutput.setText(FetchJSONString.JSONSearch(output,"name"));
            }
        }).execute(builtURI.toString()); //search country name for languages iso name
        //String newSearch = extrasearch.getText().toString();
    }
}




