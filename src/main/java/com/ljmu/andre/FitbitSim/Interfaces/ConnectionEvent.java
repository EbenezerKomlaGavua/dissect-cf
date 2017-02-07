package com.ljmu.andre.FitbitSim.Interfaces;

import com.ljmu.andre.FitbitSim.Packets.BasicPacket;

/**
 * Created by Andre on 02/02/2017.
 */
public interface ConnectionEvent {
    void connectionStarted();

    void connectionFinished(State connectionState, BasicPacket packet);

    enum State {
        SUCCESS, FAILED, IN_PROGRESS
    }
}
