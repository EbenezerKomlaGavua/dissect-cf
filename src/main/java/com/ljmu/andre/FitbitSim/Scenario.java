package com.ljmu.andre.FitbitSim;


import com.ljmu.andre.FitbitSim.DataStores.PhysicalMachineData;
import com.ljmu.andre.FitbitSim.DataStores.PowerStateTransitionData;
import com.ljmu.andre.FitbitSim.DataStores.RepositoryData;
import com.ljmu.andre.FitbitSim.DataStores.SimulationData;
import com.ljmu.andre.FitbitSim.DataStores.WatchData;
import com.ljmu.andre.FitbitSim.Utils.XMLUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import hu.mta.sztaki.lpds.cloud.simulator.Timed;
import hu.mta.sztaki.lpds.cloud.simulator.io.Repository;

/**
 * Created by Andre on 25/01/2017.
 */
public class Scenario extends Timed {
    private static final String USER_DIR = System.getProperty("user.dir");

    private static final String SIMULATION_XML_PATH = USER_DIR + "/Simulation_Data.xml";

    private static final String WATCH_XML_PATH = USER_DIR + "/Watch_Data.xml";
    private static final String WATCH_MACHINE_XML_PATH = USER_DIR + "/Watch_Machine_Data.xml";

    private static final String SMARTPHONE_XML_PATH = USER_DIR + "/Smartphone_Data.xml";
    private static final String SERVER_XML_PATH = USER_DIR + "/Server_Data.xml";
    private Watch watch;
    private SimulationData simData;

    public Scenario() throws IOException {
        /*try {
            writePMachineData();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        simData = (SimulationData) XMLUtils.decode(SIMULATION_XML_PATH);

        Map<String, Integer> latMap = new HashMap<String, Integer>();
        latMap.put("WatchRepo", 100);

        Repository targetRepo =
                new Repository(2048, "TargetRepo", 100, 100, 50, latMap);


        PhysicalMachineData watchMachineData = (PhysicalMachineData) XMLUtils.decode(WATCH_MACHINE_XML_PATH);
        WatchData watchData = (WatchData) XMLUtils.decode(WATCH_XML_PATH);
        watch = new Watch(watchMachineData, watchData, targetRepo);

        subscribe(simData.getFrequency());
        simulateUntil(simData.getStopTime());
    }

    @Override public void tick(long fires) {
        if(fires < simData.getStartTime()) {
            jumpTime(simData.getStartTime() - fires);
            return;
        }

        System.out.println("SimTick:" + fires);
    }
}
