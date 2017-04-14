package com.ljmu.andre.WeatherSimulation;

import com.ljmu.andre.SimulationHelpers.Device;

import hu.mta.sztaki.lpds.cloud.simulator.DeferredEvent;
import hu.mta.sztaki.lpds.cloud.simulator.helpers.trace.GenericTraceProducer;

/**
 * Created by Andre on 13/04/2017.
 */
public class Station extends Device {
    int currentJob = 0;
    int sensors;

    /**
     * Initiate a new Device and claim a PhysicalMachine for it
     *
     * @param id            - The ID of the Device
     * @param traceProducer - An OPTIONAL Trace File Reader which will load a list of jobs to run through
     */
    public Station(String id, GenericTraceProducer traceProducer, String customAttributes) {
        super(id, traceProducer, customAttributes);

        sensors = Integer.parseInt(getAttribute("sensors"));
    }

    @Override public void tick(long fires) {
        if(currentJob++ > getJobs().size()) {
            stop();
            return;
        }

        for(int i = 0; i < sensors; i++) {
            new Metered(1000);
        }
    }

    public static class Metered extends DeferredEvent {

        /**
         * Allows constructing objects that will receive an eventAction() call from
         * Timed after delay ticks.
         *
         * @param delay the number of ticks that should pass before this deferred
         *              event object's eventAction() will be called.
         */
        public Metered(long delay) {
            super(delay);
        }

        @Override protected void eventAction() {
            Station.this.getRepository().
        }
    }
}
