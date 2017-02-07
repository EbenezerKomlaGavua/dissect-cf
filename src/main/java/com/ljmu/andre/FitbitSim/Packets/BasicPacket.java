package com.ljmu.andre.FitbitSim.Packets;

import hu.mta.sztaki.lpds.cloud.simulator.io.StorageObject;

/**
 * Created by Andre on 02/02/2017.
 */
public class BasicPacket extends StorageObject {
    private static long totalPackets = 0;
    private String senderId;
    private long currPacketNum = totalPackets;
    private boolean shouldDeregister = false;

    public BasicPacket(String myid) {
        super(myid + totalPackets);
        totalPackets++;
    }

    public BasicPacket(String myid, long mysize, boolean vary) {
        super(myid + totalPackets, mysize, vary);
        totalPackets++;
    }

    public boolean getShouldDeregister() {
        return shouldDeregister;
    }

    public BasicPacket setShouldDeregister(boolean shouldDeregister) {
        this.shouldDeregister = shouldDeregister;
        return this;
    }

    public long getCurrPacketNum() {
        return currPacketNum;
    }

    public String getSenderId() {
        return this.senderId;
    }

    public BasicPacket setSenderId(String senderid) {
        this.senderId = senderid;
        return this;
    }
}
