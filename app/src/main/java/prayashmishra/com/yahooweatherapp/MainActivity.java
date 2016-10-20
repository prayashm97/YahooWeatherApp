package prayashmishra.com.yahooweatherapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import prayashmishra.com.yahooweatherapp.data.Channel;
import prayashmishra.com.yahooweatherapp.data.Item;
import prayashmishra.com.yahooweatherapp.service.WeatherServiceCallback;
import prayashmishra.com.yahooweatherapp.service.YahooWeatherService;

public class MainActivity extends AppCompatActivity implements WeatherServiceCallback, AdapterView.OnItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void serviceSuccess(final Channel channel) {

    }

    public void serviceFailure(Exception exception) {
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }
}
