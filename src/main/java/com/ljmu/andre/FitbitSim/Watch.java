package com.ljmu.andre.FitbitSim;

import com.ljmu.andre.FitbitSim.DataStores.PhysicalMachineData;
import com.ljmu.andre.FitbitSim.DataStores.WatchData;

import java.io.Serializable;

import hu.mta.sztaki.lpds.cloud.simulator.Timed;
import hu.mta.sztaki.lpds.cloud.simulator.iaas.PhysicalMachine;
import hu.mta.sztaki.lpds.cloud.simulator.iaas.resourcemodel.ResourceConsumption;
import hu.mta.sztaki.lpds.cloud.simulator.iaas.resourcemodel.ResourceConsumption.ConsumptionEvent;
import hu.mta.sztaki.lpds.cloud.simulator.io.NetworkNode.NetworkException;
import hu.mta.sztaki.lpds.cloud.simulator.io.Repository;
import hu.mta.sztaki.lpds.cloud.simulator.io.StorageObject;

/**
 * Created by Andre on 26/01/2017.
 */
public class Watch extends Timed implements ConsumptionEvent {
    private Repository targetRepo;
    private PhysicalMachine watchMachine;

    private WatchData watchData;

    private int totalDataCollection = 0;
    private int dataCollection = 0;
    private long lastSentTime = 0;


    public Watch(PhysicalMachineData watchMachineData, WatchData watchData, Repository targetRepo) throws IllegalAccessException, NoSuchFieldException, InstantiationException, ClassNotFoundException {
        this.watchMachine = watchMachineData.getPhysicalMachineFromData();
        this.watchData = watchData;
        this.targetRepo = targetRepo;

        watchMachine.turnon();
        this.subscribe(watchData.getFrequency());
    }

    @Override
    public void tick(long fires) {
        if(fires >= watchData.getStopTime()) {
            System.out.println("Unsubscribing");
            unsubscribe();
            return;
        }

        if(fires >= watchData.getStartTime()) {
            System.out.println("Fires: " + fires);
            int dataSize = watchData.getRandomDataPerTick();
            dataCollection += dataSize;
            totalDataCollection += dataSize;
            System.out.println("Value: " + (fires - lastSentTime));

            if (fires - lastSentTime >= watchData.getSendDelay()) {
                System.out.println("SEND DATA");

                // If there have been previous failed send attempts;
                StorageObject prevFailedObject = watchMachine.localDisk.lookup("FailedObject");
                if(prevFailedObject != null) {
                    // Add the previous data to the current data
                    dataCollection += prevFailedObject.size;
                    watchMachine.localDisk.deregisterObject("FailedObject");
                    System.out.println("Prev Failed");
                }

                final StorageObject storageObject = new StorageObject("WatchData", dataCollection, false);

                watchMachine.localDisk.registerObject(storageObject);
                System.out.println("Registered Object: " + storageObject.toString());
                try {
                    watchMachine.localDisk.requestContentDelivery("WatchData", targetRepo, new ConsumptionEvent() {
                        @Override public void conComplete() {
                            watchMachine.localDisk.deregisterObject("WatchData");
                            System.out.println("Completed");
                        }

                        @Override public void conCancelled(ResourceConsumption problematic) {
                            storeFailedObject(storageObject);
                            System.out.println("Cancelled: " + problematic.toString());
                        }
                    });
                } catch (NetworkException e) {
                    e.printStackTrace();
                    storeFailedObject(storageObject);
                }

                dataCollection = 0;
                lastSentTime = fires;
            }
        }
    }

    public void storeFailedObject(final StorageObject failedObject) {
        System.out.println("Storing failed obj");
        try {
            watchMachine.localDisk.storeInMemoryObject(failedObject.newCopy("FailedObject"), new ConsumptionEvent() {
                @Override public void conComplete() {
                    watchMachine.localDisk.deregisterObject(failedObject);
                }

                @Override public void conCancelled(ResourceConsumption problematic) {}
            });
        } catch (NetworkException e1) {
            e1.printStackTrace();
        }
    }
    public Repository getTargetRepo() {
        return targetRepo;
    }

    public void setTargetRepo(Repository targetRepo) {
        this.targetRepo = targetRepo;
    }

    public PhysicalMachine getWatchMachine() {
        return watchMachine;
    }

    public void setWatchMachine(PhysicalMachine watchMachine) {
        this.watchMachine = watchMachine;
    }

    @Override public void conComplete() {

    }

    @Override public void conCancelled(ResourceConsumption problematic) {

    }

    @Override public String toString() {
        return "Watch{" +
                "targetRepo=" + targetRepo +
                ", watchMachine=" + watchMachine +
                ", watchData=" + watchData +
                ", totalDataCollection=" + totalDataCollection +
                ", dataCollection=" + dataCollection +
                ", lastSentTime=" + lastSentTime +
                '}';
    }

    private static class FailedStorageObject {
        private String id;
        private int size;
    }
}
