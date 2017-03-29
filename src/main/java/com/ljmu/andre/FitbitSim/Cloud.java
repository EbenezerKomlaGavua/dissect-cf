package com.ljmu.andre.FitbitSim;

import com.ljmu.andre.FitbitSim.Interfaces.ConnectionEvent;
import com.ljmu.andre.FitbitSim.Packets.BasePacket;
import com.ljmu.andre.FitbitSim.Packets.DataPacket;
import com.ljmu.andre.FitbitSim.Packets.PacketHandler;
import com.ljmu.andre.FitbitSim.Packets.SubscriptionPacket;
import com.ljmu.andre.FitbitSim.Utils.Logger;

import java.util.List;

import hu.mta.sztaki.lpds.cloud.simulator.Timed;
import hu.mta.sztaki.lpds.cloud.simulator.helpers.job.Job;
import hu.mta.sztaki.lpds.cloud.simulator.iaas.PhysicalMachine;
import hu.mta.sztaki.lpds.cloud.simulator.io.Repository;

/**
 * Created by Andre on 02/02/2017.
 */
class Cloud extends Timed implements ConnectionEvent {
    private static final Logger logger = new Logger(Cloud.class);

    private Smartphone smartphone;
    private PhysicalMachine cloudMachine;

    private FitbitTraceFileReader traceFileReader;
    private List<Job> jobList;
    private int jobNumber = 0;

    public Cloud(PhysicalMachine cloudMachine, FitbitTraceFileReader traceFileReader) {
        this.cloudMachine = cloudMachine;
        this.traceFileReader = traceFileReader;
        jobList = traceFileReader.getAllJobs();
    }

    void bindSmartphone(Smartphone smartphone) {
        this.smartphone = smartphone;
    }

    @Override public void tick(long fires) {
        NetworkJob currentJob = (NetworkJob) jobList.get(jobNumber);
        PacketHandler.sendPacket(this, smartphone, new DataPacket("Data", currentJob.getPacketSize(), false));

        logger.log("Job: " + jobNumber + "/" + jobList.size());
        if (++jobNumber < jobList.size()) {
            Job nextJob = jobList.get(jobNumber);
            long timeDiff = nextJob.getSubmittimeSecs() - currentJob.getSubmittimeSecs();
            logger.log("TimeDiff: " + timeDiff);
            this.updateFrequency(timeDiff);
        } else {
            PacketHandler.sendPacket(this, smartphone, new SubscriptionPacket(false));
            stop();
        }
    }

    private void stop() {
        logger.log("Stopped: " + unsubscribe());
    }

    public void start() {
        logger.log("Started [Frequency: %s]", subscribe(0));
    }

    @Override public void connectionStarted(ConnectionEvent source) {

    }

    @Override public void connectionFinished(ConnectionEvent source, State connectionState, BasePacket packet) {

    }

    @Override public Repository getRepository() {
        return cloudMachine.localDisk;
    }
}
