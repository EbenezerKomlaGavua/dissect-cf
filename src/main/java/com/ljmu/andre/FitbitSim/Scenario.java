package com.ljmu.andre.FitbitSim;


import com.ljmu.andre.FitbitSim.DataStores.PhysicalMachineData;
import com.ljmu.andre.FitbitSim.DataStores.PowerStateTransitionData;
import com.ljmu.andre.FitbitSim.DataStores.PowerStateTransitionData.Power;
import com.ljmu.andre.FitbitSim.DataStores.PowerStateTransitionData.PowerKind;
import com.ljmu.andre.FitbitSim.DataStores.RepositoryData;
import com.ljmu.andre.FitbitSim.DataStores.SimulationData;
import com.ljmu.andre.FitbitSim.DataStores.WatchData;
import com.ljmu.andre.FitbitSim.Utils.XMLUtils;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import hu.mta.sztaki.lpds.cloud.simulator.Timed;
import hu.mta.sztaki.lpds.cloud.simulator.energy.powermodelling.ConstantConsumptionModel;
import hu.mta.sztaki.lpds.cloud.simulator.energy.powermodelling.LinearConsumptionModel;
import hu.mta.sztaki.lpds.cloud.simulator.iaas.IaaSService;
import hu.mta.sztaki.lpds.cloud.simulator.iaas.PhysicalMachine;
import hu.mta.sztaki.lpds.cloud.simulator.io.Repository;
import hu.mta.sztaki.lpds.cloud.simulator.util.CloudLoader;

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


        /*Power powerDefault = new Power(LinearConsumptionModel.class.getName(), 400, 600, "Default");
        Power powerOff = new Power(ConstantConsumptionModel.class.getName(), 20, 20, "Off");
        Power[] power = new Power[]{powerDefault, powerOff};
        PowerKind hostPowerKind = new PowerKind("host", power);

        Power powerDefault2 = new Power(LinearConsumptionModel.class.getName(), 400, 600, "Default");
        Power powerOff2 = new Power(ConstantConsumptionModel.class.getName(), 20, 20, "Off");
        Power[] power2 = new Power[]{powerDefault2, powerOff2};
        PowerKind networkPowerKind = new PowerKind("network", power2);

        Power powerDefault3 = new Power(LinearConsumptionModel.class.getName(), 400, 600, "Default");
        Power powerOff3 = new Power(ConstantConsumptionModel.class.getName(), 20, 20, "Off");
        Power[] power3 = new Power[]{powerDefault3, powerOff3};
        PowerKind storagePowerKind = new PowerKind("storage", power3);
        PowerStateTransitionData pstData = new PowerStateTransitionData(new PowerKind[] {hostPowerKind, networkPowerKind, storagePowerKind});

        PhysicalMachineData pmData = new PhysicalMachineData(4, 0.001, 1024, 5, 4, repoData, pstData);
        //XMLUtils.encode(pmData, WATCH_MACHINE_XML_PATH);*/
        /*try {
            IaaSService iaaSService = CloudLoader.loadNodes(SERVER_XML_PATH);
            for(PhysicalMachine machine : iaaSService.machines) {
                System.out.println("Machine: " + machine.toString());
            }
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }*/

        PhysicalMachineData watchMachineData = (PhysicalMachineData) XMLUtils.decode(WATCH_MACHINE_XML_PATH);
        WatchData watchData = (WatchData) XMLUtils.decode(WATCH_XML_PATH);
        try {
            watch = new Watch(watchMachineData, watchData, targetRepo);
            System.out.println(watch.toString());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        subscribe(simData.getFrequency());
        simulateUntil(simData.getStopTime());
    }

    @Override public void tick(long fires) {
        if(fires < simData.getStartTime()) {
            jumpTime(simData.getStartTime() - fires);
            return;
        }

        //System.out.println("SimTick:" + fires);

        if(!watch.isSubscribed())
            unsubscribe();
    }
}
