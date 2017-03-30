package com.ljmu.andre.FitbitSim;


import com.ljmu.andre.FitbitSim.Utils.Logger;

import hu.mta.sztaki.lpds.cloud.simulator.Timed;

/**
 * Created by Andre on 25/01/2017.
 */
public class Scenario extends Timed {
    private static final Logger logger = new Logger(Scenario.class);

    private static final String USER_DIR = System.getProperty("user.dir");
    public static final String BLUETOOTH_IN_CSV = USER_DIR + "/bluetooth_in.csv";
    public static final String NETWORK_OUT_CSV = USER_DIR + "/network_out.csv";
    public static final String NETWORK_IN_CSV = USER_DIR + "/network_in.csv";
    public static final String MACHINE_XML_PATH = USER_DIR + "/Machine_Details.xml";

    private Watch watch;
    private Smartphone smartphone;
    private Cloud cloud;

    Scenario() throws Exception {
        logger.log("Starting Scenario");
        MachineHandler.init(MACHINE_XML_PATH);
        smartphone = LoaderUtils.getSmartphone();
        watch = LoaderUtils.getWatch();
        cloud = LoaderUtils.getCloud();
        //VirtualMachine vm = Cloud.getVM();


        watch.bindSmartphone(smartphone);
        smartphone.bindWatch(watch);
        cloud.bindSmartphone(smartphone);
        watch.start();
        smartphone.start();
        cloud.start();

        subscribe(1000);
        simulateUntilLastEvent();
    }

    @Override public void tick(long fires) {
        if (!smartphone.isSubscribed() && !watch.isSubscribed() && !cloud.isSubscribed()) {
            unsubscribe();
            logger.log("No more subscribers... ENDING");
        }

        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
