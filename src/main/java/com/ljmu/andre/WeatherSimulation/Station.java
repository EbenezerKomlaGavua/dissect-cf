package com.ljmu.andre.WeatherSimulation;

import com.ljmu.andre.SimulationHelpers.ConnectionEvent;
import com.ljmu.andre.SimulationHelpers.Device;
import com.ljmu.andre.SimulationHelpers.Packets.BasePacket;
import com.ljmu.andre.SimulationHelpers.Packets.DataPacket;
import com.ljmu.andre.SimulationHelpers.Packets.PacketHandler;
import com.ljmu.andre.SimulationHelpers.Utils.Logger;

import hu.mta.sztaki.lpds.cloud.simulator.DeferredEvent;
import hu.mta.sztaki.lpds.cloud.simulator.helpers.trace.GenericTraceProducer;

/**
 * Created by Andre on 13/04/2017.
 */
public class Station extends Device {
    private static final Logger logger = new Logger(Station.class);
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
        if(++currentJob > getJobs().size()) {
            stop();
            return;
        }

        if(getRepository().getFreeStorageCapacity() < getRepository().getMaxStorageCapacity()) {
            //logger.log("Used space: " + (getRepository().getMaxStorageCapacity() - getRepository().getFreeStorageCapacity()));
            //getRepository().
        }

        new Metered(1);
        logger.log("Stats: [Current: %s] [Total: %s]", currentJob, getJobs().size());
    }

    @Override public void handleConnectionFinished(ConnectionEvent source, State connectionState, BasePacket packet) {
        logger.log("Doing shit");
        boolean state = getRepository().deregisterObject(packet);

        logger.log("State: %s, Free Space: %s", state, getRepository().getFreeStorageCapacity());
    }

    public class Metered extends DeferredEvent {

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
            PacketHandler.sendPacket(Station.this, "Server", new DataPacket("Data", 200, false));
        }
    }
}
