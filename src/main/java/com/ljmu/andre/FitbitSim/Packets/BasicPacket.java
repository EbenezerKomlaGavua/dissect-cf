package com.ljmu.andre.FitbitSim.Packets;

import com.sun.istack.internal.Nullable;

import hu.mta.sztaki.lpds.cloud.simulator.io.StorageObject;

/**
 * Created by Andre on 02/02/2017.
 */
public class BasicPacket extends StorageObject {
    private String senderId;
    private int packetNumber;

    public BasicPacket(String myid) {
        super(myid);
    }

    public BasicPacket(String myid, String senderId, long mysize, boolean vary, int packetNumber) {
        super(myid + packetNumber, mysize, vary);
        this.senderId = senderId;
        this.packetNumber = packetNumber;
    }

    public BasicPacket(String myid, String senderId, long mysize, boolean vary) {
        super(myid, mysize, vary);
        this.senderId = senderId;
    }

    public BasicPacket setPacketNum(int packetNum) {
        this.packetNumber = packetNum;
        return this;
    }

    public int getPacketNum() {
        return packetNumber;
    }

    public String buildFailedName() {
        return getId() + "-FAIL";
    }

    public BasicPacket buildFailedCopy() {
        return new BasicPacket(buildFailedName(), senderId, size, false, packetNumber);
    }

    public String getId() {
        return this.id;
    }

    public String getSenderId() {
        return this.senderId;
    }
}
