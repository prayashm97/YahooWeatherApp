package prayashmishra.com.yahooweatherapp;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import prayashmishra.com.yahooweatherapp.data.*;
import prayashmishra.com.yahooweatherapp.service.*;

public class MainActivity extends AppCompatActivity implements WeatherServiceCallback, AdapterView.OnItemClickListener {

    private YahooWeatherService service;

    private static final String LOG_TAG = "Autocomplete";
    private static final String PLACES_API_BASE = "https://maps.googleapis.com/maps/api/place";
    private static final String TYPE_AUTOCOMPLETE = "/autocomplete";
    private static final String OUT_JSON = "/json";
    private static final String API_KEY = "AIzaSyCEwp4TvdqeCVAKbnYm1qYq0XQhdcF1yEU";

    private ImageView weatherIconImageView;
    private TextView temperatureTextView;
    private TextView conditionTextView;
    private TextView locationTextView;
    private AutoCompleteTextView SearchAutoCompleteTextView;
    private Button searchButton;


    //If user presses the back button, prompt with a dialog
    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(R.mipmap.ic_launcher)
                .setTitle("Closing Activity")
                .setMessage("Are you sure you want to exit?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }

                })
                .setNegativeButton("No", null)
                .show();
    }


    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public boolean internetDialog() {
        if (isNetworkAvailable()) {
            Toast.makeText(getApplicationContext(), "Internet: yes", Toast.LENGTH_LONG).show();
            return true;
        } else {
            new AlertDialog.Builder(this)
                    .setTitle("No internet connection")
                    .setMessage("Check your internet and try again")
                    .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }

                    })
                    .show();
        }
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //checks the internet connection
        if (internetDialog())
        {
            //if there is internet, does not exit the app
            //starts the app

            /*weatherIconImageView = (ImageView) findViewById(R.id.weatherIconImageView);
            temperatureTextView = (TextView) findViewById(R.id.temperatureTextView);
            conditionTextView = (TextView) findViewById(R.id.conditionTextView);
            locationTextView = (TextView) findViewById(R.id.locationTextView);
            SearchAutoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.searchWeather);
            searchButton = (Button) findViewById(R.id.WeatherButton);


            SearchAutoCompleteTextView.setAdapter(new GooglePlacesAutocompleteAdapter(getApplicationContext(), R.layout.list_item));
            SearchAutoCompleteTextView.setOnItemClickListener(this);
            final String[] locationSearch = new String[1];*/

            service = new YahooWeatherService(this);
            service.setLocation("Toronto");

//            appendLog(getCurrentTimeStamp());

           /* searchButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v)
                {
                    if(SearchAutoCompleteTextView.getText().toString().isEmpty()){
                        Toast.makeText(getApplicationContext(),"Enter a location",Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        locationSearch[0] = SearchAutoCompleteTextView.getText().toString();
                        service.refreshWeather(locationSearch[0]);
                        Toast.makeText(getApplicationContext(),service.getLocation(),Toast.LENGTH_LONG).show();

                        //Toast.makeText(getApplicationContext(),locationSearch[0],Toast.LENGTH_LONG).show();

                    }
                }
            });*/

        }
    }


    private static String getCurrentTimeStamp(){
        try {

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String currentDateTime = dateFormat.format(new Date()); // Find todays date

            return currentDateTime;
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }

    @Override
    public void serviceSuccess(final Channel channel) {
//        appendLog("success");
//        Item item = channel.getItem();
//        int resourceId = getResources().getIdentifier("drawable/icon_" + item.getCondition().getCode(),null, getPackageName());
//
//        @SuppressWarnings("deprecation") Drawable weatherIconDrawable = getResources().getDrawable(resourceId);
//
//        weatherIconImageView.setImageDrawable(weatherIconDrawable);
//        temperatureTextView.setText(item.getCondition().getTemperature() + "\u00B0" + channel.getUnits().getTemperature());
//        conditionTextView.setText(item.getCondition().getDescription());
//        locationTextView.setText(service.getLocation());
//
//        DownloadTask task = new DownloadTask();
//        task.execute(service.getLocation());
//        Toast.makeText(getApplicationContext(), service.getLocation(), Toast.LENGTH_SHORT).show();
//        appendLog("weather for: " + service.getLocation() + " : " + channel.getItem().getCondition().getTemperature());
//        final int tempChange = channel.getItem().getCondition().getTemperature();
//        int fah = (int) 1.8;
//        final int fahTemp = tempChange * fah + 32;
//        temperatureTextView.setOnTouchListener(new View.OnTouchListener() {
//
//            @Override
//            public boolean onTouch(View arg0, MotionEvent arg1) {
//
//                if (temperatureTextView.getText().equals(fahTemp+ "\u00B0F")) {
//                    temperatureTextView.setText(tempChange + "\u00B0" + channel.getUnits().getTemperature());
//                } else if(temperatureTextView.getText().equals(tempChange+ "\u00B0" + channel.getUnits().getTemperature())) {
//                    temperatureTextView.setText(fahTemp+ "\u00B0F");
//                }
//                //Toast.makeText(getApplicationContext(),tempChange,Toast.LENGTH_LONG).show();
//
//                return false;
//            }
//        });

    }

    @Override
    public void serviceFailure(Exception exception) {
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
    }


}
