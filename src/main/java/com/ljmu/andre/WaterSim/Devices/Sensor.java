package com.ljmu.andre.WaterSim.Devices;

import com.ljmu.andre.WaterSim.Application;
import com.ljmu.andre.WaterSim.Interfaces.ConnectionEvent;
import com.ljmu.andre.WaterSim.NetworkJob;
import com.ljmu.andre.WaterSim.Packets.BasePacket;
import com.ljmu.andre.WaterSim.Packets.DataPacket;
import com.ljmu.andre.WaterSim.Packets.PacketHandler;
import com.ljmu.andre.WaterSim.SimulationFileReader;
import com.ljmu.andre.WaterSim.Utils.Logger;

import hu.mta.sztaki.lpds.cloud.simulator.helpers.job.Job;

/**
 * Created by Andre on 30/03/2017.
 */
public class Sensor extends Device {
    private static final Logger logger = new Logger(Sensor.class);

    public Sensor(String id, String machineID, SimulationFileReader simulationFileReader) {
        super(id, machineID, simulationFileReader);
    }

    public Sensor(String id, String machineID) {
        super(id, machineID);
    }

    @Override public void tick(long fires) {
        if (networkJobs == null) {
            stop();
            return;
        }

        NetworkJob currentJob = (NetworkJob) networkJobs.get(currentJobNum);
        sendPacket(currentJob.getTarget(), new DataPacket("SensorData", currentJob.getPacketSize(), false));

        logger.log("Job: " + currentJobNum + "/" + networkJobs.size());

        if (++currentJobNum < networkJobs.size()) {
            Job nextJob = networkJobs.get(currentJobNum);
            long timeDiff = nextJob.getSubmittimeSecs() - currentJob.getSubmittimeSecs();
            logger.log("TimeDiff: " + timeDiff);
            this.updateFrequency(timeDiff);
        } else
            stop();
    }

    @Override void handleConnectionStarted(ConnectionEvent source) {

    }

    @Override void handleConnectionFinished(ConnectionEvent source, State connectionState, BasePacket packet) {
        logger.log("Received Packet [From: %s][State: %s]", source.getId(), connectionState);
        Application.totalPackets++;
    }
}
