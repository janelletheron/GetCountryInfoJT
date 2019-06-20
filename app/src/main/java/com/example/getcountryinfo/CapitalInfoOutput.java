package com.example.getcountryinfo;

import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.TextView;

public class CapitalInfoOutput extends AppCompatActivity {
    private static final String REST_BASE_URL = "https://geocode.xyz";
    private static final String REST_OUTPUTFORMAT = "?json=1";
    //private static final String AUTH = "&autho=14330011750324917781x2562";
    private TextView mHeadingCapital;
    private TextView mOutput;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capital_info_output);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mHeadingCapital = (TextView) findViewById(R.id.headingCapital);
        mOutput = (TextView) findViewById(R.id.locationOutput);
        String queryString = getIntent().getStringExtra("query"); //this will be location
        mHeadingCapital.setText(queryString);
        //doing string build here to allow fetchInfo to have functionality for Capital output as well
        //Uri builtURI = Uri.parse(REST_BASE_URL).buildUpon()
        //        .appendEncodedPath(queryString)
        //        .appendEncodedPath(REST_OUTPUTFORMAT)
        //        .build();


        FetchJSONString asyncTask = (FetchJSONString) new FetchJSONString(new FetchJSONString.AsyncResponse() {
            @Override
            public void processFinish(String output) {
                if ( output != "empty" )
                mOutput.setText(FetchJSONString.JSONSearch(output, "latt"));
                else{
                    mOutput.setText("Empty");
                }
            }
        }).execute("https://geocode.xyz/Ankara/?json=1/"); //hard coded for testing, cannot connect
            //}).execute(builtURI.toString()); //search country name for languages iso name
    }
}
