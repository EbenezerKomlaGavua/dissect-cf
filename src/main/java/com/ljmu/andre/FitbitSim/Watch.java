package com.ljmu.andre.FitbitSim;

import com.ljmu.andre.FitbitSim.DataStores.WatchData;
import com.ljmu.andre.FitbitSim.Interfaces.ConnectionEvent;
import com.ljmu.andre.FitbitSim.Packets.ActivityPacket;
import com.ljmu.andre.FitbitSim.Packets.BasicPacket;
import com.ljmu.andre.FitbitSim.Packets.SubscriptionPacket;

import java.util.ArrayList;
import java.util.List;

import hu.mta.sztaki.lpds.cloud.simulator.Timed;
import hu.mta.sztaki.lpds.cloud.simulator.iaas.PhysicalMachine;
import hu.mta.sztaki.lpds.cloud.simulator.iaas.resourcemodel.ResourceConsumption;
import hu.mta.sztaki.lpds.cloud.simulator.iaas.resourcemodel.ResourceConsumption.ConsumptionEvent;
import hu.mta.sztaki.lpds.cloud.simulator.io.NetworkNode;
import hu.mta.sztaki.lpds.cloud.simulator.io.NetworkNode.NetworkException;
import hu.mta.sztaki.lpds.cloud.simulator.io.Repository;

/**
 * Created by Andre on 26/01/2017.
 */
public class Watch extends Timed implements ConnectionEvent {
    private final int connectionCap;
    private int connectionAttempts = 0;

    private Smartphone smartphone;
    private PhysicalMachine watchMachine;

    private WatchData watchData;
    private List<String> failedPacketIds = new ArrayList<String>();

    private int dataCollection = 0;
    private long lastSentTime = 0;

    public Watch(
            PhysicalMachine watchMachine,
            WatchData watchData) {
        this(watchMachine, null, watchData);
    }

    public Watch(
            PhysicalMachine watchMachine,
            Smartphone smartphone,
            WatchData watchData) {
        this.watchMachine = watchMachine;
        this.smartphone = smartphone;
        this.watchData = watchData;
        this.connectionCap = watchData.getConnectionCap();
    }

    public void start() {
        subscribe(watchData.getFrequency());
    }

    public void bindSmartphone(Smartphone smartphone) {
        this.smartphone = smartphone;
        BasicPacket subPacket = new SubscriptionPacket(true)
                .setSubscriber(this)
                .setSenderId(this.getId());

        sendPacket(subPacket);
    }

    public String getId() {
        return getRepository().getName();
    }

    public void sendPacket(final BasicPacket packet) {
        System.out.println("Stored packet? " + getRepository().registerObject(packet));

        try {
            if (NetworkNode.checkConnectivity(getRepository(), smartphone.getRepository()) <= 0)
                return;

            smartphone.connectionStarted();
            getRepository().requestContentDelivery(packet.id, smartphone.getRepository(), new ConsumptionEvent() {
                @Override public void conComplete() {
                    getRepository().deregisterObject(packet.id);
                    smartphone.connectionFinished(State.SUCCESS, packet);
                    System.out.println("Completed");
                }

                @Override public void conCancelled(ResourceConsumption problematic) {
                    failedPacketIds.add(packet.id);
                    smartphone.connectionFinished(State.FAILED, null);
                    System.out.println("Cancelled: " + problematic.toString());
                }
            });
        } catch (NetworkException e) {
            ++connectionAttempts;

            System.out.println("Connection Attempts: " + connectionAttempts + "/" + connectionCap);
            if (connectionAttempts > connectionCap)
                stop();

            System.out.println(e.getMessage());
            failedPacketIds.add(packet.id);
        }
    }

    public Repository getRepository() {
        return watchMachine.localDisk;
    }

    public void stop() {
        unsubscribe();
    }

    @Override
    public void tick(long fires) {
        if (fires >= watchData.getStopTime()) {
            System.out.println("Unsubscribing");
            stop();

            BasicPacket unsubPacket = new SubscriptionPacket(false)
                    .setSenderId(this.getId());

            if (unsubPacket instanceof SubscriptionPacket)
                System.out.println("isUnsub");

            sendPacket(unsubPacket);
            return;
        }

        if (fires >= watchData.getStartTime()) {
            System.out.println("Fires: " + fires);
            long dataSize = watchData.getRandomDataPerTick();
            dataCollection += dataSize;
            //System.out.println("Value: " + (fires - lastSentTime));

            if (fires - lastSentTime >= watchData.getSendDelay()) {
                System.out.println("SEND DATA");

                // If there have been previous failed send attempts;
                if (failedPacketIds.size() > 0) {
                    ArrayList<String> mutablePacketList = new ArrayList<String>(failedPacketIds);
                    System.out.println("MutableSize: " + mutablePacketList.size());

                    for (String failedPacketId : mutablePacketList) {
                        if (!isSubscribed())
                            return;

                        System.out.println("Failed: " + failedPacketId);
                        BasicPacket failedObject = (BasicPacket) getRepository().lookup(failedPacketId);
                        System.out.println("Failed: " + failedObject);
                        failedPacketIds.remove(failedPacketId);

                        sendPacket(failedObject);
                    }
                    System.out.println("Prev Failed");
                }

                BasicPacket basicPacket
                        = new ActivityPacket("WatchData", dataCollection)
                        .setSenderId(this.getId());

                sendPacket(basicPacket);

                dataCollection = 0;
                lastSentTime = fires;
            }
        }
    }

    @Override public void connectionStarted() {

    }

    @Override public void connectionFinished(State connectionState, BasicPacket packet) {

    }
}
