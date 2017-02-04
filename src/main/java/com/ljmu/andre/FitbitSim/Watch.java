package com.ljmu.andre.FitbitSim;

import com.ljmu.andre.FitbitSim.DataStores.WatchData;
import com.ljmu.andre.FitbitSim.Interfaces.ConnectionEvent;
import com.ljmu.andre.FitbitSim.Packets.BasicPacket;

import hu.mta.sztaki.lpds.cloud.simulator.Timed;
import hu.mta.sztaki.lpds.cloud.simulator.iaas.PhysicalMachine;
import hu.mta.sztaki.lpds.cloud.simulator.iaas.resourcemodel.ResourceConsumption;
import hu.mta.sztaki.lpds.cloud.simulator.iaas.resourcemodel.ResourceConsumption.ConsumptionEvent;
import hu.mta.sztaki.lpds.cloud.simulator.io.NetworkNode.NetworkException;

/**
 * Created by Andre on 26/01/2017.
 */
public class Watch extends Timed implements ConnectionEvent {
    private Smartphone smartphone;
    private PhysicalMachine watchMachine;

    private WatchData watchData;
    private BasicPacket failedPacket;

    private int totalDataCollection = 0;
    private int dataCollection = 0;
    private long lastSentTime = 0;
    private int packetId = 0;

    public Watch(
            PhysicalMachine watchMachine,
            Smartphone smartphone,
            WatchData watchData) {
        this.watchMachine = watchMachine;
        this.smartphone = smartphone;
        this.watchData = watchData;

        this.subscribe(watchData.getFrequency());
    }

    @Override
    public void tick(long fires) {
        if (fires >= watchData.getStopTime()) {
            System.out.println("Unsubscribing");
            unsubscribe();
            return;
        }

        if (fires >= watchData.getStartTime()) {
            System.out.println("Fires: " + fires);
            long dataSize = watchData.getRandomDataPerTick();
            dataCollection += dataSize;
            totalDataCollection += dataSize;
            //System.out.println("Value: " + (fires - lastSentTime));

            if (fires - lastSentTime >= watchData.getSendDelay()) {
                System.out.println("SEND DATA");

                // If there have been previous failed send attempts;
                if (failedPacket != null) {
                    // Add the previous data to the current data
                    dataCollection += failedPacket.size;
                    watchMachine.localDisk.deregisterObject("FailedObject");
                    failedPacket = null;
                    System.out.println("Prev Failed");
                }

                BasicPacket basicPacket
                        = new BasicPacket("WatchData" + packetId, dataCollection, false)
                        .setPacketNum(packetId);

                watchMachine.localDisk.registerObject(basicPacket);
                System.out.println("Registered Object: " + basicPacket.toString());

                sendStorageObject(basicPacket);

                dataCollection = 0;
                lastSentTime = fires;
            }
        }
    }

    public void sendStorageObject(final BasicPacket basicPacket) {
        smartphone.connectionStarted();
        try {
            watchMachine.localDisk.requestContentDelivery(basicPacket.getId(), smartphone.getRepository(), new ConsumptionEvent() {
                @Override public void conComplete() {
                    watchMachine.localDisk.deregisterObject(basicPacket.id);
                    smartphone.connectionFinished(State.SUCCESS);
                    System.out.println("Completed");
                    packetId++;
                }

                @Override public void conCancelled(ResourceConsumption problematic) {
                    storeFailedObject(basicPacket);
                    smartphone.connectionFinished(State.FAILED);
                    System.out.println("Cancelled: " + problematic.toString());
                }
            });
        } catch (NetworkException e) {
            e.printStackTrace();
            storeFailedObject(basicPacket);
            smartphone.connectionFinished(State.FAILED);
        }
    }

    public void storeFailedObject(final BasicPacket basicPacket) {
        System.out.println("Storing failed obj");

        failedPacket = basicPacket.buildFailedCopy();

        try {
            watchMachine.localDisk.storeInMemoryObject(failedPacket, new ConsumptionEvent() {
                @Override public void conComplete() {
                    watchMachine.localDisk.deregisterObject(failedPacket);
                    System.out.println("Correctly stored failed packet");
                }

                @Override public void conCancelled(ResourceConsumption problematic) {
                    System.out.println("Cancelled storing of failed packet");
                }
            });
        } catch (NetworkException e1) {
            e1.printStackTrace();
        }
    }


    @Override public void connectionStarted() {

    }

    @Override public void connectionFinished(State connectionState) {

    }
}
