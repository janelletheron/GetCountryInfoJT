package com.example.getcountryinfo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class CountryInfoOutput extends AppCompatActivity {
    private TextView mHeadingNew;
    private TextView mCountryCode;
    private TextView mCountryCapital;
    private TextView mCountryCurrencies;
    private TextView mCountryLanguages;
    private TextView mInstructions;
    private ImageView mCountryImage;
    private TextView mInfo1;
    private TextView mInfo2;
    private TextView mInfo3;
    private TextView mInfo4;
    private TextView mInfo5;
    private TextView mExtra;
    private TextView mInfo6;

    private static final String REST_BASE_URL = "https://restcountries.eu/rest/v2/name/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country_info_output);

        //--------------display info
        mCountryCode = (TextView) findViewById(R.id.countryCode);
        mCountryCapital = (TextView) findViewById(R.id.countryCapital);
        mHeadingNew = (TextView) findViewById(R.id.headingNew);
        mCountryCurrencies = (TextView) findViewById(R.id.countryCurrencies);
        mCountryLanguages = (TextView) findViewById(R.id.countryLanguages);
        mCountryImage = (ImageView) findViewById(R.id.countryImage);
        mInstructions = (TextView) findViewById(R.id.instructions2);
        mInfo1 = (TextView) findViewById(R.id.info1);
        mInfo2 = (TextView) findViewById(R.id.info2);
        mInfo3 = (TextView) findViewById(R.id.info3);
        mInfo4 = (TextView) findViewById(R.id.info4);
        mInfo5 = (TextView) findViewById(R.id.info5);
        mExtra = (TextView) findViewById(R.id.intermediate);
        mInfo6 = (TextView) findViewById(R.id.info6);
        //intent info from previous activity
        String queryString = getIntent().getStringExtra("query");
        //and display as heading
        mHeadingNew.setText(queryString);
        mInstructions.setText("Click on Capital or Language to find out more");
        mInfo2.setText("Code:");
        mInfo3.setText("Capital:");
        mInfo4.setText("Currency:");
        mInfo5.setText("Language:");
        //--------dispay info done

        //--------get REST
        //doing string build here to allow fetchInfo to have functionality for Capital output as well
        Uri builtURI = Uri.parse(REST_BASE_URL).buildUpon()
                .appendEncodedPath(queryString)
                .build();

        FetchJSONString asyncTask = (FetchJSONString) new FetchJSONString(new FetchJSONString.AsyncResponse() {
            @Override
            public void processFinish(String output) {
                mCountryCode.setText(FetchJSONString.JSONSearch(output, "alpha3Code"));
                mCountryCapital.setText(FetchJSONString.JSONSearch(output, "capital"));
                mCountryCurrencies.setText(FetchJSONString.JSONSearchNested(output, "currencies", "name"));
                mCountryLanguages.setText(FetchJSONString.JSONSearchNested(output, "languages", "name"));//this outputs all in one, unsure for more than one country
                mExtra.setText(FetchJSONString.JSONSearchNested(output, "languages", "iso639_1")); //have to do it via textview unfortunately can't get string to work because it is async
                //mInfo6.setText(FetchJSONString.JSONSearch(output, "flag"));
            }
        }).execute(builtURI.toString());
        //--------get REST done

        //--------get Image svg
        //this does not seem to run - future recive address from mInfo6 -> Async to receive this as http address
        FetchSVGInfo asyncTask2 = new FetchSVGInfo(new FetchSVGInfo.AsyncResponse() {
            @Override
            public void processFinishSVG(Drawable outputDrawable) {
                if (outputDrawable != null) {
                    mCountryImage.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
                    mCountryImage.setImageDrawable(outputDrawable);
                    mInfo6.setText("done");
                }
            }
        });

    }

    public void searchCapital(View view) { //on click of capital
        String queryString = mCountryCapital.getText().toString();
        Intent latitude = new Intent(getBaseContext(),CapitalInfoOutput.class);
        latitude.putExtra("query",queryString);
        startActivity(latitude);
    }

    public void searchLanguages(View view){ //on click of languages
        String queryString = mHeadingNew.getText().toString();
        String iso = mExtra.getText().toString();
        Intent languages = new Intent(getBaseContext(),LanguagesInfoOutput.class);
        languages.putExtra("query",queryString);
        languages.putExtra("isoname", iso);
        startActivity(languages);
    }
}
