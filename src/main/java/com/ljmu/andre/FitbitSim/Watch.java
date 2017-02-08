package com.ljmu.andre.FitbitSim;

import com.ljmu.andre.FitbitSim.DataStores.WatchData;
import com.ljmu.andre.FitbitSim.Interfaces.ConnectionEvent;
import com.ljmu.andre.FitbitSim.Packets.ActivityPacket;
import com.ljmu.andre.FitbitSim.Packets.BasicPacket;
import com.ljmu.andre.FitbitSim.Packets.PacketHandler;
import com.ljmu.andre.FitbitSim.Packets.SubscriptionPacket;

import java.util.ArrayList;
import java.util.List;

import hu.mta.sztaki.lpds.cloud.simulator.Timed;
import hu.mta.sztaki.lpds.cloud.simulator.iaas.PhysicalMachine;
import hu.mta.sztaki.lpds.cloud.simulator.io.Repository;

/**
 * Created by Andre on 26/01/2017.
 */
public class Watch extends Timed implements ConnectionEvent {
    private final int connectionCap;
    private int connectionAttempts = 0;
    private boolean isOnCooldown = false;

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

        PacketHandler.sendPacket(this, smartphone, subPacket);
    }

    public String getId() {
        return getRepository().getName();
    }

    @Override
    public void tick(long fires) {
        if(failedPacketIds.size() >= connectionCap)
            stop();

        if (fires >= watchData.getStopTime()) {
            System.out.println("Unsubscribing");
            stop();

            BasicPacket unsubPacket = new SubscriptionPacket(false)
                    .setSenderId(this.getId());

            if (unsubPacket instanceof SubscriptionPacket)
                System.out.println("isUnsub");

            PacketHandler.sendPacket(this, smartphone, unsubPacket);

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

                ArrayList<BasicPacket> sendList = new ArrayList<BasicPacket>();

                for (String failedPacketId : failedPacketIds) {
                    System.out.println("FailedPacketId: " + failedPacketId);
                    BasicPacket failedObject = (BasicPacket) getRepository().lookup(failedPacketId);
                    System.out.println("FailedObject: " + failedObject);
                    sendList.add(failedObject);
                }

                failedPacketIds.clear();

                BasicPacket basicPacket
                        = new ActivityPacket("WatchData", dataCollection)
                        .setSenderId(this.getId());
                sendList.add(basicPacket);

                ArrayList<BasicPacket> failedPackets = PacketHandler.sendPackets(this, smartphone, sendList);

                dataCollection = 0;
                lastSentTime = fires;
            }
        }
    }

    public void stop() {
        unsubscribe();
    }

    @Override public void connectionStarted(ConnectionEvent source) {

    }

    @Override public void connectionFinished(ConnectionEvent source, State connectionState, BasicPacket packet) {
        if (connectionState == State.FAILED && source == this) {
            failedPacketIds.add(packet.id);
            System.out.println("Added failed packet");
        }
    }

    public Repository getRepository() {
        return watchMachine.localDisk;
    }

    public PhysicalMachine getPhysicalMachine() {
        return watchMachine;
    }
}
