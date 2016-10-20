package prayashmishra.com.yahooweatherapp.service;

import prayashmishra.com.yahooweatherapp.data.Channel;

/**
 * Created by Pray on 2016-10-20.
 */

public interface WeatherServiceCallback {

    void serviceSuccess(Channel channel);

    void serviceFailure(Exception exception);
}
