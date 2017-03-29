package com.ljmu.andre.FitbitSim;

import com.ljmu.andre.FitbitSim.Utils.Logger;

import hu.mta.sztaki.lpds.cloud.simulator.iaas.PhysicalMachine;

import static com.ljmu.andre.FitbitSim.Scenario.BLUETOOTH_IN_CSV;
import static com.ljmu.andre.FitbitSim.Scenario.NETWORK_IN_CSV;
import static com.ljmu.andre.FitbitSim.Scenario.NETWORK_OUT_CSV;

/**
 * Created by Andre on 06/02/2017.
 */
class LoaderUtils {
    private static final Logger logger = new Logger(LoaderUtils.class);

    static Watch getWatch() throws NoSuchMethodException {
        PhysicalMachine watchMachine = MachineHandler.claimPM("Watch");

        if (watchMachine == null)
            throw new NullPointerException("WatchMachine not found");

        FitbitTraceFileReader traceFileReader = new FitbitTraceFileReader(
                "BluetoothIN",
                BLUETOOTH_IN_CSV,
                0, Integer.MAX_VALUE, true,
                NetworkJob.class);

        return new Watch(watchMachine, traceFileReader);
    }

    static Smartphone getSmartphone() throws NoSuchMethodException {
        PhysicalMachine phoneMachine = MachineHandler.claimPM("Smartphone");
        if (phoneMachine == null)
            throw new NullPointerException("SmartphoneMachine not found");

        FitbitTraceFileReader traceFileReader = new FitbitTraceFileReader(
                "NetworkOUT",
                NETWORK_OUT_CSV,
                0, Integer.MAX_VALUE, true,
                NetworkJob.class);

        return new Smartphone(phoneMachine, traceFileReader);
    }

    static Cloud getCloud() throws NoSuchMethodException {
        FitbitTraceFileReader traceFileReader = new FitbitTraceFileReader(
                "NetworkIN",
                NETWORK_IN_CSV,
                0, Integer.MAX_VALUE, true,
                NetworkJob.class);
        return new Cloud(MachineHandler.claimPM("Cloud"), traceFileReader);
    }
}
