package com.ljmu.andre.FitbitSim.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import hu.mta.sztaki.lpds.cloud.simulator.Timed;

/**
 * Created by Andre on 08/02/2017.
 */
public class Logger {
    private static final SimpleDateFormat sdf = new SimpleDateFormat("EEE HH:mm:ss:SSS");
    private Class subscribedClass;

    public Logger(Class subscribedClass) {
        this.subscribedClass = subscribedClass;
    }

    public void log(String message) {
        String time = sdf.format(new Date(Timed.getFireCount() - TimeUnit.HOURS.toMillis(1)));

        message = String.format("[%s][%s] %s",
                time,
                subscribedClass.getSimpleName(),
                message);

        System.out.println(message);
    }

    public void log(long timestamp, String message) {
        String time = sdf.format(new Date(timestamp - TimeUnit.HOURS.toMillis(1)));

        message = String.format("[%s][%s] %s",
                time,
                subscribedClass.getSimpleName(),
                message);

        System.out.println(message);
    }

    public void log(String message, Object... params) {
        message = String.format(message, params);

        String time = sdf.format(new Date(Timed.getFireCount() - TimeUnit.HOURS.toMillis(1)));

        message = String.format("[%s][%s] %s",
                time,
                subscribedClass.getSimpleName(),
                message);

        System.out.println(message);
    }
}
