package com.ljmu.andre.FitbitSim.Packets;

import java.util.ArrayList;

import hu.mta.sztaki.lpds.cloud.simulator.io.StorageObject;

/**
 * Created by Andre on 02/02/2017.
 */
public class BasePacket extends StorageObject {
    private static long totalPackets = 0;
    private ArrayList<Object> deregistryList = new ArrayList<Object>();
    private boolean shouldStore = true;

    BasePacket(String myid) {
        super(myid + totalPackets++);
    }

    public BasePacket(String myid, long mysize, boolean vary) {
        super(myid + totalPackets++, mysize, vary);
    }

    boolean shouldDeregister(Object toDeregister) {
        return deregistryList.contains(toDeregister);
    }

    public BasePacket setDeregistryList(ArrayList<Object> deregisterFrom) {
        this.deregistryList = deregisterFrom;
        return this;
    }

    BasePacket addDeregisterObject(Object toDeregister) {
        if (!deregistryList.contains(toDeregister))
            deregistryList.add(toDeregister);

        return this;
    }

    public BasePacket removeDeregisterObject(Object toDeregister) {
        deregistryList.remove(toDeregister);
        return this;
    }

    boolean getShouldStore() {
        return shouldStore;
    }

    BasePacket setShouldStore(boolean shouldStore) {
        this.shouldStore = shouldStore;
        return this;
    }
}
