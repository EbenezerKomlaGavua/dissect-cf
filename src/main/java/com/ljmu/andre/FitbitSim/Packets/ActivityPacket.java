package com.ljmu.andre.FitbitSim.Packets;

/**
 * Created by Andre on 07/02/2017.
 */
public class ActivityPacket extends BasicPacket {
    public ActivityPacket(String myid, long mysize) {
        super(myid, mysize, false);
    }
}
