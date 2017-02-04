package com.ljmu.andre.FitbitSim.Packets;

import hu.mta.sztaki.lpds.cloud.simulator.io.StorageObject;

/**
 * Created by Andre on 04/02/2017.
 */
public class MessagePacket extends StorageObject {
    private String message;

    public MessagePacket( String myid, String message, boolean vary) {
        super(myid, message.getBytes().length, vary);
        this.message = message;

    }

    public String getMessage() {
        return message;
    }
}
