package com.ljmu.andre.FitbitSim;

import com.ljmu.andre.FitbitSim.DataStores.SmartphoneData;
import com.ljmu.andre.FitbitSim.Interfaces.ConnectionEvent;
import com.ljmu.andre.FitbitSim.Packets.ActivityPacket;
import com.ljmu.andre.FitbitSim.Packets.BasePacket;
import com.ljmu.andre.FitbitSim.Packets.SubscriptionPacket;
import com.ljmu.andre.FitbitSim.Utils.Logger;
import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;

import java.util.ArrayList;
import java.util.List;

import hu.mta.sztaki.lpds.cloud.simulator.Timed;
import hu.mta.sztaki.lpds.cloud.simulator.iaas.PhysicalMachine;
import hu.mta.sztaki.lpds.cloud.simulator.io.Repository;

/**
 * Created by Andre on 31/01/2017.
 */
public class Smartphone extends Timed implements ConnectionEvent {
    private static final Logger logger = new Logger(Smartphone.class);

    private PhysicalMachine physicalMachine;
    private SmartphoneData phoneData;
    private List<Watch> watchList = new ArrayList<Watch>();

    public Smartphone(PhysicalMachine physicalMachine, SmartphoneData phoneData) {
        this.physicalMachine = physicalMachine;
        this.phoneData = phoneData;
    }

    public PhysicalMachine getPhysicalMachine() {
        return physicalMachine;
    }

    @Override public void tick(long fires) {
        logger.log("Tick: " + fires);
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

        logger.log("Disk: " + freeCap + "/" + maxCap);
    }

    private void handleSuccess(ConnectionEvent source, BasePacket packet) {
        logger.log("Successfully received packet: " + packet.getClass().getSimpleName());
        if (packet instanceof SubscriptionPacket) {
            SubscriptionPacket subPacket = (SubscriptionPacket) packet;

            if (subPacket.getSubState()) {
                if (source instanceof Watch)
                    addWatch((Watch) source);

            } else {
                if (source instanceof Watch)
                    removeWatch((Watch) source);
            }
        } else if (packet instanceof ActivityPacket) {
        }
    }

    private void addWatch(Watch watch) {
        boolean result = watchList.add(watch);

        logger.log("Added watch: " + result);

        if (result && !isSubscribed())
            start();
    }

    private void removeWatch(Watch watch) {
        watchList.remove(watch);

        if (watchList.size() <= 0 && isSubscribed())
            stop();
    }

    public void start() {
        logger.log("Started [Frequency: " + subscribe(phoneData.getFrequency()) + "]");
    }

    private void stop() {
        logger.log("Stopped: " + unsubscribe());
    }

    @Nullable private Watch getWatch(@NotNull String id) {
        for (Watch watch : watchList) {
            if (watch.getId().equals(id))
                return watch;
        }

        return null;
    }
}
