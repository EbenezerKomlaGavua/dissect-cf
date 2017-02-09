package com.ljmu.andre.FitbitSim;

import com.ljmu.andre.FitbitSim.DataStores.WatchData;
import com.ljmu.andre.FitbitSim.Interfaces.ConnectionEvent;
import com.ljmu.andre.FitbitSim.Packets.ActivityPacket;
import com.ljmu.andre.FitbitSim.Packets.BasePacket;
import com.ljmu.andre.FitbitSim.Packets.PacketHandler;
import com.ljmu.andre.FitbitSim.Packets.SubscriptionPacket;
import com.ljmu.andre.FitbitSim.Utils.Logger;

import java.util.ArrayList;
import java.util.List;

import hu.mta.sztaki.lpds.cloud.simulator.Timed;
import hu.mta.sztaki.lpds.cloud.simulator.iaas.PhysicalMachine;
import hu.mta.sztaki.lpds.cloud.simulator.io.Repository;

/**
 * Created by Andre on 26/01/2017.
 */
public class Watch extends Timed implements ConnectionEvent {
    private static final Logger logger = new Logger(Watch.class);

    private final int connectionCap;

    private Smartphone smartphone;
    private PhysicalMachine watchMachine;

    private WatchData watchData;
    private List<String> failedPacketIds = new ArrayList<String>();

    private double dataCollection = 0;
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

    void bindSmartphone(Smartphone smartphone) {
        this.smartphone = smartphone;
        PacketHandler.sendPacket(this, smartphone, new SubscriptionPacket(true));
    }

    public String getId() {
        return getRepository().getName();
    }

    @Override
    public void tick(long fires) {
        if (failedPacketIds.size() >= connectionCap)
            stop();

        if (fires >= watchData.getStopTime()) {
            logger.log("Unsubscribing");
            PacketHandler.sendPacket(this, smartphone, new SubscriptionPacket(false));
            stop();
            return;
        }

        if (fires >= watchData.getStartTime()) {
            logger.log("Tick: " + fires);
            double dataSize = watchData.getRandomDataPerTick();
            dataCollection += dataSize;
            //System.out.println("Value: " + (fires - lastSentTime));

            if (fires - lastSentTime >= watchData.getSendDelay()) {

                logger.log("SEND DATA");

                // If there have been previous failed send attempts;

                ArrayList<BasePacket> sendList = new ArrayList<BasePacket>();

                for (String failedPacketId : failedPacketIds) {
                    logger.log("FailedPacketId: " + failedPacketId);
                    BasePacket failedObject = (BasePacket) getRepository().lookup(failedPacketId);
                    logger.log("FailedObject: " + failedObject);
                    sendList.add(failedObject);
                }

                failedPacketIds.clear();

                BasePacket basePacket
                        = new ActivityPacket("WatchData", (int) dataCollection);

                sendList.add(basePacket);

                PacketHandler.sendPackets(this, smartphone, sendList);

                dataCollection = 0;
                lastSentTime = fires;
            }
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
