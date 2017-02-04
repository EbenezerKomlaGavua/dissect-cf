package com.ljmu.andre.FitbitSim.Packets;

import com.sun.istack.internal.Nullable;

import hu.mta.sztaki.lpds.cloud.simulator.io.StorageObject;

/**
 * Created by Andre on 02/02/2017.
 */
public class BasicPacket extends StorageObject {
    private String name;
    private int packetNumber;

    public BasicPacket(String myid) {
        super(myid);
    }

    public BasicPacket(String myid, long mysize, boolean vary, @Nullable String name, int packetNumber) {
        super(myid, mysize, vary);
        this.name = name;
        this.packetNumber = packetNumber;
    }
    public BasicPacket(String myid, long mysize, boolean vary, @Nullable String name) {
        this(myid, mysize, vary, name, 0);
    }

    public BasicPacket(String myid, long mysize, boolean vary, int packetNumber) {
        this(myid, mysize, vary, null, packetNumber);
    }

    public BasicPacket(String myid, long mysize, boolean vary) {
        super(myid, mysize, vary);
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
        return new BasicPacket(buildFailedName(), this.size, false, name, packetNumber);
    }

    public String getId() {
        return this.id;
    }
}
