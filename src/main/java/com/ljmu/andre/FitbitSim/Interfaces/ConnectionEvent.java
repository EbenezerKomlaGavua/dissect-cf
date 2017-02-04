package com.ljmu.andre.FitbitSim.Interfaces;

/**
 * Created by Andre on 02/02/2017.
 */
public interface ConnectionEvent {
    public void connectionStarted();
    public void connectionFinished(State connectionState);

    public enum State {
        SUCCESS, FAILED, IN_PROGRESS
    }
}
