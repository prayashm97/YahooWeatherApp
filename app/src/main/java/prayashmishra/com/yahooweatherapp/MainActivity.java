package prayashmishra.com.yahooweatherapp;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import prayashmishra.com.yahooweatherapp.data.Channel;
import prayashmishra.com.yahooweatherapp.data.Item;
import prayashmishra.com.yahooweatherapp.service.WeatherServiceCallback;
import prayashmishra.com.yahooweatherapp.service.YahooWeatherService;

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
            //Toast.makeText(getApplicationContext(), "Internet: yes", Toast.LENGTH_LONG).show();
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
        if (internetDialog()) {
            //if there is internet, does not exit the app
            //starts the app

            weatherIconImageView = (ImageView) findViewById(R.id.weatherIconImageView);
            temperatureTextView = (TextView) findViewById(R.id.temperatureTextView);
            conditionTextView = (TextView) findViewById(R.id.conditionTextView);
            locationTextView = (TextView) findViewById(R.id.locationTextView);
            SearchAutoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.searchWeather);
            Button searchButton = (Button) findViewById(R.id.WeatherButton);


            SearchAutoCompleteTextView.setAdapter(new GooglePlacesAutocompleteAdapter(getApplicationContext(), R.layout.list_item));
            SearchAutoCompleteTextView.setOnItemClickListener(this);
            final String[] locationSearch = new String[1];

            service = new YahooWeatherService(this);
            service.setLocation("Toronto");

            searchButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (internetDialog()) {
                        if (SearchAutoCompleteTextView.getText().toString().isEmpty()) {
                            Toast.makeText(getApplicationContext(), "Enter a location", Toast.LENGTH_LONG).show();
                        } else {
                            locationSearch[0] = SearchAutoCompleteTextView.getText().toString();
                            service.setLocation(locationSearch[0]);
//                            Toast.makeText(getApplicationContext(), service.getLocation(), Toast.LENGTH_LONG).show();

                            //Toast.makeText(getApplicationContext(),locationSearch[0],Toast.LENGTH_LONG).show();

                        }
                    }

                }
            });

        }
    }


    @Override
    public void serviceSuccess(final Channel channel) {
        Item item = channel.getItem();
        int resourceId = getResources().getIdentifier("drawable/icon_" + item.getCondition().getCode(), null, getPackageName());

        @SuppressWarnings("deprecation") Drawable weatherIconDrawable = getResources().getDrawable(resourceId);

        weatherIconImageView.setImageDrawable(weatherIconDrawable);
        temperatureTextView.setText(item.getCondition().getTemperature() + "\u00B0" + channel.getUnits().getTemperature());
        conditionTextView.setText(item.getCondition().getDescription());
        locationTextView.setText(service.getLocation());


        Toast.makeText(getApplicationContext(), service.getLocation(), Toast.LENGTH_SHORT).show();
        final int tempChange = channel.getItem().getCondition().getTemperature();
        int fah = (int) 1.8;
        final int fahTemp = tempChange * fah + 32;
        final String fahrenheitSign = getString(R.string.fahrenheit);
        final String fahFull = fahTemp + fahrenheitSign;
        temperatureTextView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View arg0, MotionEvent arg1) {

                if (temperatureTextView.getText().equals(fahFull)) {
                    temperatureTextView.setText(tempChange + "\u00B0" + channel.getUnits().getTemperature());
                } else if (temperatureTextView.getText().equals(tempChange + "\u00B0" + channel.getUnits().getTemperature())) {
                    temperatureTextView.setText(fahFull);
                }

                return false;
            }
        });

    }

    @Override
    public void serviceFailure(Exception exception) {
        Toast.makeText(this, exception.getMessage(), Toast.LENGTH_LONG).show();
        new AlertDialog.Builder(this)
                .setIcon(R.mipmap.ic_launcher)
                .setTitle("Failed")
                .setMessage(exception.getMessage())
                .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }

                })
                .show();

    }

    public void appendLog(String text) {
//        File logFile = new File("sdcard/logFile.txt");
//        if (!logFile.exists()) {
//            try {
//                logFile.createNewFile();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        try {
//            //BufferedWriter for performance, true to set append to file flag
//            BufferedWriter buf = new BufferedWriter(new FileWriter(logFile, true));
//            buf.append(text);
//            buf.newLine();
//            buf.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        String str = (String) adapterView.getItemAtPosition(position);

    }


    class GooglePlacesAutocompleteAdapter extends ArrayAdapter implements Filterable {
        private ArrayList resultList;

        public GooglePlacesAutocompleteAdapter(Context context, int textViewResourceId) {
            super(context, textViewResourceId);
        }

        @Override
        public int getCount() {
            return resultList.size();
        }

        @Override
        public String getItem(int index) {
            return (String) resultList.get(index);
        }

        @Override
        public Filter getFilter() {
            Filter filter = new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                    FilterResults filterResults = new FilterResults();
                    if (constraint != null) {
                        // Retrieve the autocomplete results.
                        resultList = autocomplete(constraint.toString());
                        //appendLog("Result from google: " + resultList.toString());
                        notifyDataSetChanged();
                        // Assign the data to the FilterResults
                        filterResults.values = resultList;
                        filterResults.count = resultList.size();
                    }
                    return filterResults;
                }

                @Override
                protected void publishResults(CharSequence constraint, FilterResults results) {
                    if (results != null && results.count > 0) {
                        notifyDataSetChanged();
                    } else {
                        notifyDataSetInvalidated();
                    }
                }
            };
            return filter;
        }

    }


    public static ArrayList autocomplete(String input) {
        ArrayList resultList = null;

        HttpURLConnection conn = null;
        StringBuilder jsonResults = new StringBuilder();
        try {
            StringBuilder sb = new StringBuilder(PLACES_API_BASE + TYPE_AUTOCOMPLETE + OUT_JSON);
            sb.append("?key=" + API_KEY);
            sb.append("&types=(cities)");
            sb.append("&input=").append(URLEncoder.encode(input, "utf8"));

            URL url = new URL(sb.toString());

            conn = (HttpURLConnection) url.openConnection();
            InputStreamReader in = new InputStreamReader(conn.getInputStream());


            // Load the results into a StringBuilder
            int read;
            char[] buff = new char[1024];
            while ((read = in.read(buff)) != -1) {
                jsonResults.append(buff, 0, read);
            }
        } catch (MalformedURLException e) {
            Log.d(LOG_TAG, "Error connecting to Places API", e);
            return resultList;
        } catch (IOException e) {
            Log.i(LOG_TAG, "Error connecting to Places API", e);
            return resultList;
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }

        try {
            // Create a JSON object hierarchy from the results
            JSONObject jsonObj = new JSONObject(jsonResults.toString());
            JSONArray predsJsonArray = jsonObj.getJSONArray("predictions");

            // Extract the Place descriptions from the results
            resultList = new ArrayList(predsJsonArray.length());
            for (int i = 0; i < predsJsonArray.length(); i++) {
                resultList.add(predsJsonArray.getJSONObject(i).getString("description"));
            }
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Cannot process JSON results", e);
        }

        return resultList;
    }


}
