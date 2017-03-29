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
 * Created by Andre on 31/01/2017.
 */
public class Smartphone extends Timed implements ConnectionEvent {
    private static final Logger logger = new Logger(Smartphone.class);

    private PhysicalMachine physicalMachine;
    private FitbitTraceFileReader traceFileReader;

    private List<Job> jobList;
    private Watch watch;

    private int jobNumber = 0;

    public Smartphone(PhysicalMachine physicalMachine, FitbitTraceFileReader traceFileReader) {
        this.physicalMachine = physicalMachine;
        this.traceFileReader = traceFileReader;
        jobList = traceFileReader.getAllJobs();
    }

    void bindWatch(Watch watch) {
        this.watch = watch;
        //PacketHandler.sendPacket(this, watch, new SubscriptionPacket(true));
    }

    PhysicalMachine getPhysicalMachine() {
        return physicalMachine;
    }

    @Override public void tick(long fires) {
        NetworkJob currentJob = (NetworkJob) jobList.get(jobNumber);

        PacketHandler.sendPacket(this, watch, new DataPacket("Data", currentJob.getPacketSize(), false));

        logger.log("Job: " + jobNumber + "/" + jobList.size());
        if (++jobNumber < jobList.size()) {
            Job nextJob = jobList.get(jobNumber);
            long timeDiff = nextJob.getSubmittimeSecs() - currentJob.getSubmittimeSecs();
            logger.log("TimeDiff: " + timeDiff);
            this.updateFrequency(timeDiff);
        } else {
            stop();
        }
    }

    private void stop() {
        logger.log("Stopped: " + unsubscribe());
    }

    @Override public void connectionStarted(ConnectionEvent source) {
        logger.log("Received connection init: " + source.getRepository().getName());
    }

    @Override public void connectionFinished(ConnectionEvent source, State connectionState, BasePacket packet) {
        logger.log("Connection finished: " + connectionState);
        printStorageMetrics();

        if (connectionState == State.SUCCESS)
            handleSuccess(source, packet);
    }

    public Repository getRepository() {
        return physicalMachine.localDisk;
    }

    private void printStorageMetrics() {
        long freeCap = getRepository().getFreeStorageCapacity();
        long maxCap = getRepository().getMaxStorageCapacity();

        logger.log("Disk: [%s/%s]", freeCap, maxCap);
    }

    private void handleSuccess(ConnectionEvent source, BasePacket packet) {
        if (packet instanceof SubscriptionPacket) {
            SubscriptionPacket subPacket = (SubscriptionPacket) packet;
            logger.log("Subscription: " + subPacket.getSubState());
        } else if (packet instanceof DataPacket) {
            logger.log("packet: " + packet);
        } else {
            logger.log("Unknown packet type: " + packet.getClass().getName());
        }
    }

    public void start() {
        logger.log("Started [Frequency: %s]", subscribe(0));
    }

    private Watch getWatch() {
        return watch;
    }
}
