package com.ljmu.andre.WaterSim.Devices;

import com.ljmu.andre.WaterSim.Application;
import com.ljmu.andre.WaterSim.Interfaces.ConnectionEvent;
import com.ljmu.andre.WaterSim.NetworkJob;
import com.ljmu.andre.WaterSim.Packets.BasePacket;
import com.ljmu.andre.WaterSim.Packets.DataPacket;
import com.ljmu.andre.WaterSim.SimulationFileReader;
import com.ljmu.andre.WaterSim.Utils.Logger;

import hu.mta.sztaki.lpds.cloud.simulator.helpers.job.Job;

/**
 * Created by Andre on 30/03/2017.
 */
public class Server extends Device {
    private static final Logger logger = new Logger(Server.class);

    public Server(String id, String machineID, SimulationFileReader simulationFileReader) {
        super(id, machineID, simulationFileReader);
    }

    public Server(String id, String machineID) {
        super(id, machineID);
    }

    @Override public void tick(long fires) {
        // If there are no jobs for this device, stop further execution
        if (networkJobs == null || networkJobs.isEmpty()) {
            stop();
            return;
        }

        // Get the Job that should be processed \\
        NetworkJob currentJob = (NetworkJob) networkJobs.get(currentJobNum);

        // Send the Job to the intended Target \\
        sendPacket(currentJob.getTarget(), new DataPacket("ServerData", currentJob.getPacketSize(), false));

        logger.log("Job: " + currentJobNum + "/" + networkJobs.size());

        // Increment the Current Job Number and check if there are more jobs to be processed \\
        if (++currentJobNum < networkJobs.size()) {
            // Get the next job, calculate the time difference, and update the frequency to wait until it's ready \\
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
