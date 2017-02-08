package com.ljmu.andre.FitbitSim;

import com.ljmu.andre.FitbitSim.DataStores.SmartphoneData;
import com.ljmu.andre.FitbitSim.Interfaces.ConnectionEvent;
import com.ljmu.andre.FitbitSim.Packets.ActivityPacket;
import com.ljmu.andre.FitbitSim.Packets.BasicPacket;
import com.ljmu.andre.FitbitSim.Packets.SubscriptionPacket;
import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;

import java.util.ArrayList;
import java.util.List;

import hu.mta.sztaki.lpds.cloud.simulator.Timed;
import hu.mta.sztaki.lpds.cloud.simulator.iaas.PhysicalMachine;
import hu.mta.sztaki.lpds.cloud.simulator.io.Repository;
import hu.mta.sztaki.lpds.cloud.simulator.io.StorageObject;

/**
 * Created by Andre on 31/01/2017.
 */
public class Smartphone extends Timed implements ConnectionEvent {
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
        System.out.println("PhoneTick: " + fires);
    }

    @Override public void connectionStarted(ConnectionEvent source) {
        System.out.println("Smartphone received connection init: " + source.getRepository().getName());
    }

    @Override public void connectionFinished(ConnectionEvent source, State connectionState, BasicPacket packet) {
        if (connectionState == State.FAILED)
            getRepository().deregisterObject(packet.id);

        System.out.println("Smartphone connection finished: " + connectionState);
        printStorageMetrics();

        if (connectionState == State.SUCCESS)
            handleSuccess(packet);
    }

    public Repository getRepository() {
        return physicalMachine.localDisk;
    }

    private void printStorageMetrics() {
        long freeCap = getRepository().getFreeStorageCapacity();
        long maxCap = getRepository().getMaxStorageCapacity();

        System.out.println("Smartphone disk: " + freeCap + "/" + maxCap);
    }

    private void handleSuccess(BasicPacket packet) {
        if (packet.getShouldDeregister()) {
            System.out.println("Deregisterring");
            getRepository().deregisterObject(packet.id);
        }

        if (packet instanceof SubscriptionPacket) {
            SubscriptionPacket subPacket = (SubscriptionPacket) packet;

            if (subPacket.getSubState()) {
                Object subscriber = subPacket.getSubscriber();
                System.out.println("Subscription? " + subscriber);
                if (subscriber instanceof Watch) {
                    addWatch((Watch) subscriber);
                }
            } else {
                Object subscriber = subPacket.getSubscriber();
                if (subscriber instanceof Watch) {
                    removeWatch((Watch) subscriber);
                }
            }

            System.out.println("Received unsub from " + packet.getSenderId());

            removeWatch(getWatch(packet.getSenderId()));

        } else if (packet instanceof ActivityPacket) {
            StorageObject so = getRepository().lookup(packet.id);
            System.out.println("Storage: " + so);
        }
    }

    void addWatch(Watch watch) {
        boolean result = watchList.add(watch);

        System.out.println("Added watch: " + result);

        if (result && !isSubscribed())
            start();
    }

    private void removeWatch(Watch watch) {
        watchList.remove(watch);

        if (watchList.size() <= 0 && isSubscribed())
            stop();
    }

    @Nullable private Watch getWatch(@NotNull String id) {
        for (Watch watch : watchList) {
            if (watch.getId().equals(id))
                return watch;
        }

        return null;
    }

    public void start() {
        System.out.println("Started Smartphone: " + subscribe(phoneData.getFrequency()));
    }

    private void stop() {
        System.out.println("Stopped Smartphone: " + unsubscribe());
    }
}
