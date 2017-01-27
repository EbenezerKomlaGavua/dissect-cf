package com.ljmu.andre.FitbitSim;

import hu.mta.sztaki.lpds.cloud.simulator.Timed;
import hu.mta.sztaki.lpds.cloud.simulator.io.Repository;

/**
 * Created by Andre on 26/01/2017.
 */
//TODO Implement class correctly
public class Application extends Timed {
    private static Application appInstance;

    public static Application getInstance(Object... params) {
        if(appInstance == null)
             appInstance = new Application(params);

        return appInstance;
    }

    private Application(Object... params) {
    }

    @Override
    public void tick(long fires) {

    }
}
