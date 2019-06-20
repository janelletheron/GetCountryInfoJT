package com.example.getcountryinfo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import com.microsoft.appcenter.AppCenter;
import com.microsoft.appcenter.analytics.Analytics;
import com.microsoft.appcenter.crashes.Crashes;

import java.lang.ref.WeakReference;

public class MainActivity extends AppCompatActivity {
    private EditText mCountryInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppCenter.start(getApplication(), "33da3d6d-a0b5-41e5-b19e-8cfc3b110571",
                Analytics.class, Crashes.class); //for app deployment
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true); //for svg image
        //initialise views
        mCountryInput = (EditText)findViewById(R.id.cName);
    }

    public void searchCountries(View view) {//on button click method
        //check connectivity
        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);

        if (inputManager != null ) {
            inputManager.hideSoftInputFromWindow(view.getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }

        //get the text from the EditText view. Convert the text to a String, and assign it to a variable.
        String queryString = mCountryInput.getText().toString();
        //launch in background
        Intent infoOutput = new Intent(getBaseContext(),CountryInfoOutput.class);
        infoOutput.putExtra("query",queryString);
        startActivity(infoOutput);
    }
}

//Resources: https://codelabs.developers.google.com/codelabs/android-training-asynctask-asynctaskloader/index.html?index=..%2F..%2Fandroid-training#0