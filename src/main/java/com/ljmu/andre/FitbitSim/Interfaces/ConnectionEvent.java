package com.ljmu.andre.FitbitSim.Interfaces;

import com.ljmu.andre.FitbitSim.Packets.BasicPacket;

/**
 * Created by Andre on 02/02/2017.
 */
public interface ConnectionEvent {
    public void connectionStarted();
    public void connectionFinished(State connectionState, BasicPacket packet);

    public enum State {
        SUCCESS, FAILED, IN_PROGRESS
    }
}
