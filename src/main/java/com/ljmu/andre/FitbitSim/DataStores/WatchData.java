package com.ljmu.andre.FitbitSim.DataStores;

import com.sun.istack.internal.NotNull;

import java.io.Serializable;

/**
 * Created by Andre on 26/01/2017.
 */
public class WatchData implements Serializable {
    private String id;
    private long simDuration;
    private long startTime;
    private long stopTime;
    private long frequency;
    private long actionDelay;
    private int fileSize;

    public WatchData() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getSimDuration() {
        return simDuration;
    }

    public void setSimDuration(long simDuration) {
        this.simDuration = simDuration;
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

    public long getFrequency() {
        return frequency;
    }

    public void setFrequency(long frequency) {
        this.frequency = frequency;
    }

    public int getFileSize() {
        return fileSize;
    }

    public void setFileSize(int fileSize) {
        this.fileSize = fileSize;
    }

    public long getActionDelay() {
        return actionDelay;
    }

    public void setActionDelay(long actionDelay) {
        this.actionDelay = actionDelay;
    }

    @Override public String toString() {
        return "WatchData{" +
                "id='" + id + '\'' +
                ", simDuration=" + simDuration +
                ", startTime=" + startTime +
                ", stopTime=" + stopTime +
                ", frequency=" + frequency +
                ", fileSize=" + fileSize +
                ", actionDelay=" + actionDelay +
                '}';
    }
}
