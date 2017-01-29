package com.ljmu.andre.FitbitSim.DataStores;

/**
 * Created by Andre on 29/01/2017.
 */
public class SimulationData {
    private int frequency;
    private long startTime;
    private long stopTime;

    public SimulationData() {}

    public SimulationData(int frequency, long startTime, long stopTime) {
        this.frequency = frequency;
        this.startTime = startTime;
        this.stopTime = stopTime;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getStopTime() {
        return stopTime;
    }

    public void setStopTime(long stopTime) {
        this.stopTime = stopTime;
    }
}
