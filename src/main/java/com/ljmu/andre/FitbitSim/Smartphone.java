package com.ljmu.andre.FitbitSim;

import com.ljmu.andre.FitbitSim.DataStores.SmartphoneData;
import com.ljmu.andre.FitbitSim.Interfaces.ConnectionEvent;
import com.ljmu.andre.FitbitSim.Packets.BasicPacket;
import com.ljmu.andre.FitbitSim.Packets.MessagePacket;
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
    private PhysicalMachine physicalMachine;
    private SmartphoneData phoneData;
    private List<Watch> watchList = new ArrayList<Watch>();

    public Smartphone(PhysicalMachine physicalMachine, SmartphoneData phoneData) {
        this.physicalMachine = physicalMachine;
        this.phoneData = phoneData;
    }

    public void start() {
        subscribe(phoneData.getFrequency());
    }

    public void stop() {
        unsubscribe();
    }

    public void addWatch(Watch watch) {
        if(!watchList.contains(watch))
            watchList.add(watch);
    }

    public boolean removeWatch(Watch watch) {
        return watchList.remove(watch);
    }

    @Nullable public Watch getWatch(@NotNull String id) {
        for(Watch watch : watchList) {
            if(watch.getId().equals(id))
                return watch;
        }

        return null;
    }

    public PhysicalMachine getPhysicalMachine() {
        return physicalMachine;
    }

    public Repository getRepository() {
        return physicalMachine.localDisk;
    }

    @Override public void tick(long fires) {
        System.out.println("PhoneTick: " + fires);
    }

    @Override public void connectionStarted() {
        System.out.println("Smartphone received connection init");
    }

    @Override public void connectionFinished(State connectionState, BasicPacket packet) {
        System.out.println("Smartphone connection finished: " + connectionState);
        long freeCap = getRepository().getFreeStorageCapacity();
        long maxCap = getRepository().getMaxStorageCapacity();

        System.out.println("Smartphone disk: " + freeCap + "/" + maxCap);

        if(connectionState == State.SUCCESS) {
            String senderId = packet.getSenderId();

            System.out.println("Sender: " + senderId);

            if(packet instanceof MessagePacket) {
                System.out.println("Message: " + ((MessagePacket) packet).getMessage());
                removeWatch(getWatch(senderId));

                if(watchList.size() <= 0) {
                    unsubscribe();
                    System.out.println("Unsubbed");
                }
            }
        }


    }
}
