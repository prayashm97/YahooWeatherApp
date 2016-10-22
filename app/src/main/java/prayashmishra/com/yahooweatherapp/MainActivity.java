package prayashmishra.com.yahooweatherapp;

import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import prayashmishra.com.yahooweatherapp.data.Channel;
import prayashmishra.com.yahooweatherapp.service.WeatherServiceCallback;

public class MainActivity extends AppCompatActivity implements WeatherServiceCallback, AdapterView.OnItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //checks the internet connection
        if (internetDialog()) {
            //if there is internet, does not exit the app
            //starts the app
        }

    }


    @Override
    public void serviceSuccess(final Channel channel) {
    }

    @Override
    public void serviceFailure(Exception exception) {
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
    }

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

}
