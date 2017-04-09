package com.ljmu.andre.WaterSimulation;

import com.ljmu.andre.SimulationHelpers.ConnectionEvent;
import com.ljmu.andre.SimulationHelpers.Device;
import com.ljmu.andre.SimulationHelpers.NetworkJob;
import com.ljmu.andre.SimulationHelpers.Packets.BasePacket;

import hu.mta.sztaki.lpds.cloud.simulator.helpers.trace.GenericTraceProducer;

/**
 * Created by Andre on 09/04/2017.
 */
public class WaterSensor extends Device {
    /**
     * Initiate a new Device and claim a PhysicalMachine for it
     *
     * @param id            - The ID of the Device
     * @param traceProducer - An OPTIONAL Trace File Reader which will load a list of jobs to run through
     */
    public WaterSensor(String id, GenericTraceProducer traceProducer) {
        super(id, traceProducer);
    }

    @Override public void handleConnectionFinished(ConnectionEvent source, State connectionState, BasePacket packet) {
        if(connectionState == State.SUCCESS) {
            beginSend();
        }
    }

    public void beginSend() {
        int delay = 0;
        for(int i = 0; i < 12; i++) {
            delay += 1000;
            NetworkJob job = new NetworkJob(null, getId(), "Gateway", getFireCount() + delay, 5000, null, null, null);
        }

        start();
    }
}
