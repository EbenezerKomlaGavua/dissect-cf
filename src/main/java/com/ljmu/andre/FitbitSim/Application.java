package com.ljmu.andre.FitbitSim;

import hu.mta.sztaki.lpds.cloud.simulator.Timed;

/**
 * Created by Andre on 26/01/2017.
 */
//TODO Implement class correctly
public class Application extends Timed {
    private static Application appInstance;

    private Application(Object... params) {
    }

    @Override
    public void tick(long fires) {

    }

    public static Application getInstance(Object... params) {
        if (appInstance == null)
            appInstance = new Application(params);

        return appInstance;
    }
}
