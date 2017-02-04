package com.ljmu.andre.FitbitSim;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ljmu.andre.FitbitSim.DataStores.SimulationData;
import com.ljmu.andre.FitbitSim.DataStores.WatchData;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import hu.mta.sztaki.lpds.cloud.simulator.Timed;
import hu.mta.sztaki.lpds.cloud.simulator.iaas.PhysicalMachine;

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

    private static final String SMARTPHONE_XML_PATH = USER_DIR + "/Smartphone_Data.xml";
    private static final String SERVER_XML_PATH = USER_DIR + "/Server_Data.xml";
    private Watch watch;
    private SimulationData simData;

    public Scenario() throws IOException {
        try {
            Cloud.init(CLOUD_LOADER_XML);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();

        File simFile = new File(SIMULATION_JSON_PATH);
        if(!simFile.exists() && !simFile.createNewFile()) {
            System.out.println("Couldn't create simulation file");
            return;
        }

        FileReader simReader = new FileReader(SIMULATION_JSON_PATH);
        simData = gson.fromJson(simReader, SimulationData.class);
        simReader.close();

        PhysicalMachine watchPM = Cloud.findPM("watch");
        PhysicalMachine smartphonePM = Cloud.findPM("smartphone");
        Smartphone smartphone = new Smartphone(smartphonePM);

        File watchFile = new File(WATCH_JSON_PATH);
        if(!watchFile.exists() && !watchFile.createNewFile()) {
            System.out.println("Couldn't create new file");
            return;
        }

        FileReader fileReader = new FileReader(watchFile);
        WatchData watchData = gson.fromJson(fileReader, WatchData.class);
        fileReader.close();

        watch = new Watch(watchPM, smartphone, watchData);

        subscribe(simData.getFrequency());
        simulateUntil(simData.getStopTime());
    }

    @Override public void tick(long fires) {
        if(fires < simData.getStartTime()) {
            jumpTime(simData.getStartTime() - fires);
            return;
        }

        if(!watch.isSubscribed())
            unsubscribe();
    }
}
