package com.ljmu.andre.FitbitSim.Interfaces;

import com.ljmu.andre.FitbitSim.Packets.BasicPacket;

import hu.mta.sztaki.lpds.cloud.simulator.io.Repository;

/**
 * Created by Andre on 02/02/2017.
 */
public interface ConnectionEvent {
    void connectionStarted(ConnectionEvent source);

    void connectionFinished(ConnectionEvent source, State connectionState, BasicPacket packet);

    Repository getRepository();

    enum State {
        SUCCESS, FAILED, IN_PROGRESS
    }
}
