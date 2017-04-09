package com.ljmu.andre.WaterSimulation;

import com.ljmu.andre.SimulationHelpers.Device;

import hu.mta.sztaki.lpds.cloud.simulator.helpers.trace.GenericTraceProducer;

/**
 * Created by Andre on 09/04/2017.
 */
public class Gateway extends Device {
    /**
     * Initiate a new Device and claim a PhysicalMachine for it
     *
     * @param id            - The ID of the Device
     * @param traceProducer - An OPTIONAL Trace File Reader which will load a list of jobs to run through
     */
    public Gateway(String id, GenericTraceProducer traceProducer) {
        super(id, traceProducer);
    }

    @Override public void tick(long fires) {
        super.tick(fires);
    }
}
