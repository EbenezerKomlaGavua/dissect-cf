package com.ljmu.andre.FitbitSim;


import com.ljmu.andre.FitbitSim.DataStores.SimulationData;

import java.util.ArrayList;
import java.util.List;

import hu.mta.sztaki.lpds.cloud.simulator.Timed;
import hu.mta.sztaki.lpds.cloud.simulator.iaas.PhysicalMachine;
import hu.mta.sztaki.lpds.cloud.simulator.iaas.VirtualMachine;

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

    Scenario() throws Exception {
        Cloud.init(CLOUD_LOADER_XML);
        simData = LoaderUtils.getSimDataFromJson(SIMULATION_JSON_PATH);
        smartphone = LoaderUtils.getPhoneFromJson(SMARTPHONE_JSON_PATH);
        watchList = LoaderUtils.getWatchListFromJson(WATCH_JSON_PATH);
        Cloud.clearNonCloudMachines(buildNonCloudMachineList());

        VirtualMachine vm = Cloud.getVM();

        if (vm == null)
            throw new NullPointerException("Null VM");

        System.out.println("VMState: " + vm.getState());

        subscribe(simData.getFrequency());

        for (Watch watch : watchList) {
            watch.bindSmartphone(smartphone);
            watch.start();
        }

        //if (simData.getStopTime() == -1)
        //simulateUntilLastEvent();
        //else
        //simulateUntil(simData.getStopTime());
    }

    public ArrayList<PhysicalMachine> buildNonCloudMachineList() {
        ArrayList<PhysicalMachine> nonCloudMachines = new ArrayList<PhysicalMachine>();
        nonCloudMachines.add(smartphone.getPhysicalMachine());
        for (Watch watch : watchList)
            nonCloudMachines.add(watch.getPhysicalMachine());

        return nonCloudMachines;
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
