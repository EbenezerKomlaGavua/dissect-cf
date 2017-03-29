package com.ljmu.andre.FitbitSim;

import com.ljmu.andre.FitbitSim.Interfaces.ConnectionEvent;
import com.ljmu.andre.FitbitSim.Packets.BasePacket;
import com.ljmu.andre.FitbitSim.Packets.DataPacket;
import com.ljmu.andre.FitbitSim.Packets.PacketHandler;
import com.ljmu.andre.FitbitSim.Packets.SubscriptionPacket;
import com.ljmu.andre.FitbitSim.Utils.Logger;

import java.util.ArrayList;
import java.util.List;

import hu.mta.sztaki.lpds.cloud.simulator.Timed;
import hu.mta.sztaki.lpds.cloud.simulator.helpers.job.Job;
import hu.mta.sztaki.lpds.cloud.simulator.iaas.PhysicalMachine;
import hu.mta.sztaki.lpds.cloud.simulator.io.Repository;

/**
 * Created by Andre on 26/01/2017.
 */
public class Watch extends Timed implements ConnectionEvent {
    private static final Logger logger = new Logger(Watch.class);

    private Smartphone smartphone;
    private PhysicalMachine watchMachine;

    private FitbitTraceFileReader traceFileReader;
    private List<Job> jobList;

    private List<String> failedPacketIds = new ArrayList<String>();

    private int jobNumber = 0;

    public Watch(
            PhysicalMachine watchMachine,
            FitbitTraceFileReader traceFileReader) {
        this.watchMachine = watchMachine;
        this.traceFileReader = traceFileReader;
        jobList = traceFileReader.getAllJobs();
    }

    public void start() {
        logger.log("Started [Frequency: %s]", subscribe(0));
    }

    void bindSmartphone(Smartphone smartphone) {
        this.smartphone = smartphone;
        //PacketHandler.sendPacket(this, smartphone, new SubscriptionPacket(true));
    }

    public String getId() {
        return getRepository().getName();
    }

    @Override
    public void tick(long fires) {
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
        unsubscribe();
    }

    @Override public void connectionStarted(ConnectionEvent source) {

    }

    @Override public void connectionFinished(ConnectionEvent source, State connectionState, BasePacket packet) {
        if (connectionState == State.FAILED && source == this) {
            failedPacketIds.add(packet.id);
            logger.log("Added failed packet");
        }
    }

    public Repository getRepository() {
        return watchMachine.localDisk;
    }

    PhysicalMachine getPhysicalMachine() {
        return watchMachine;
    }
}
