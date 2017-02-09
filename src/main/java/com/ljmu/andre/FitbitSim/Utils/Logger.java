package com.ljmu.andre.FitbitSim.Utils;

/**
 * Created by Andre on 08/02/2017.
 */
public class Logger {
    private Class subscribedClass;

    public Logger(Class subscribedClass) {
        this.subscribedClass = subscribedClass;
    }

    public void log(String message) {
        message = String.format("[%s] %s", subscribedClass.getSimpleName(), message);
        System.out.println(message);
    }
}
