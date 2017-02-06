package com.ljmu.andre.FitbitSim;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ljmu.andre.FitbitSim.DataStores.SimulationData;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import hu.mta.sztaki.lpds.cloud.simulator.Timed;

/**
 * Created by Andre on 25/01/2017.
 */
public class Scenario extends Timed {
    private static final String USER_DIR = System.getProperty("user.dir");

    private static final String SIMULATION_XML_PATH = USER_DIR + "/Simulation_Data.xml";
    private static final String SIMULATION_JSON_PATH = USER_DIR + "/Simulation_Data.json";

    private static final String CLOUD_LOADER_XML = USER_DIR + "/Cloud_Loader.xml";

    private static final String WATCH_JSON_PATH = USER_DIR + "/Watch_Data.json";
    private static final String WATCH_MACHINE_XML_PATH = USER_DIR + "/Watch_Machine_Data.xml";

    private static final String SMARTPHONE_JSON_PATH = USER_DIR + "/Smartphone_Data.json";
    private static final String SERVER_XML_PATH = USER_DIR + "/Server_Data.xml";

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
        watchList = LoaderUtils.getWatchListFromJson(WATCH_JSON_PATH, smartphone);

        subscribe(simData.getFrequency());
        smartphone.start();
        for(Watch watch : watchList)
            watch.startWatch();

        if(simData.getStopTime() == -1)
            simulateUntilLastEvent();
        else
            simulateUntil(simData.getStopTime());
    }

    @Override public void tick(long fires) {
        if(fires < simData.getStartTime()) {
            jumpTime(simData.getStartTime() - fires);
            return;
        }

        boolean subscribers = false;

        if(smartphone.isSubscribed())
            subscribers = true;
        else {
            for (Watch watch : watchList) {
                if (watch.isSubscribed()) {
                    subscribers = true;
                    break;
                }
            }
        }

        if(!subscribers)
            unsubscribe();
    }
}
