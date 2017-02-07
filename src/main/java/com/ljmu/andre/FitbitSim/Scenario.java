package com.ljmu.andre.FitbitSim;


import com.ljmu.andre.FitbitSim.DataStores.SimulationData;

import java.io.IOException;
import java.util.List;

import hu.mta.sztaki.lpds.cloud.simulator.Timed;

/**
 * Created by Andre on 25/01/2017.
 */
public class Scenario extends Timed {
    private static final String USER_DIR = System.getProperty("user.dir");

    private static final String CLOUD_LOADER_XML = USER_DIR + "/Cloud_Loader.xml";
    private static final String SIMULATION_JSON_PATH = USER_DIR + "/Simulation_Data.json";
    private static final String WATCH_JSON_PATH = USER_DIR + "/Watch_Data.json";
    private static final String SMARTPHONE_JSON_PATH = USER_DIR + "/Smartphone_Data.json";

    private SimulationData simData;
    private List<Watch> watchList;
    private Smartphone smartphone;

    public Scenario() throws IOException {
        try {
            Cloud.init(CLOUD_LOADER_XML);
        } catch (Exception e) {
            e.printStackTrace();
        }

        simData = LoaderUtils.getSimDataFromJson(SIMULATION_JSON_PATH);
        smartphone = LoaderUtils.getPhoneFromJson(SMARTPHONE_JSON_PATH);
        watchList = LoaderUtils.getWatchListFromJson(WATCH_JSON_PATH);

        subscribe(simData.getFrequency());

        for (Watch watch : watchList) {
            watch.bindSmartphone(smartphone);
            watch.start();
        }

        if (simData.getStopTime() == -1)
            simulateUntilLastEvent();
        else
            simulateUntil(simData.getStopTime());
    }

    @Override public void tick(long fires) {
        if (fires < simData.getStartTime()) {
            jumpTime(simData.getStartTime() - fires);
            return;
        }

        boolean subscribers = false;

        if (smartphone.isSubscribed())
            subscribers = true;
        else {
            for (Watch watch : watchList) {
                if (watch.isSubscribed()) {
                    subscribers = true;
                    break;
                }
            }
        }

        if (!subscribers)
            unsubscribe();
    }
}
