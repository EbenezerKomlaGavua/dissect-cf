package com.ljmu.andre.FitbitSim.DataStores;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Andre on 26/01/2017.
 */

public class WatchData implements Serializable {
    @SerializedName("Id")
    private String id;

    @SerializedName("Simulation Duration")
    private long simDuration;

    @SerializedName("Start Time")
    private long startTime;

    @SerializedName("Frequency")
    private long frequency;

    @SerializedName("Min Data/Tick")
    private int minDataPerTick;

    @SerializedName("Max Data/Tick")
    private int maxDataPerTick;

    @SerializedName("Send Delay")
    private long sendDelay;

    @SerializedName("Max Connection Attempts")
    private int connectionCap;

    public WatchData(
            String id,
            long simDuration,
            long startTime,
            long frequency,
            int minDataPerTick,
            int maxDataPerTick,
            long sendDelay,
            int connectionCap) {
        this.id = id;
        this.simDuration = simDuration;
        this.startTime = startTime;
        this.frequency = frequency;
        this.minDataPerTick = minDataPerTick;
        this.maxDataPerTick = maxDataPerTick;
        this.sendDelay = sendDelay;
        this.connectionCap = connectionCap;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getStopTime() {
        return getStartTime() + getSimDuration();
    }

    public long getStartTime() {
        return startTime;
    }

    public long getSimDuration() {
        return simDuration;
    }

    public void setSimDuration(long simDuration) {
        this.simDuration = simDuration;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public void setStopTime(long stopTime) {
    }

    public long getFrequency() {
        return frequency;
    }

    public void setFrequency(long frequency) {
        this.frequency = frequency;
    }

    public int getMinDataPerTick() {
        return minDataPerTick;
    }

    public void setMinDataPerTick(int minDataPerTick) {
        this.minDataPerTick = minDataPerTick;
    }

    public int getMaxDataPerTick() {
        return maxDataPerTick;
    }

    public void setMaxDataPerTick(int maxDataPerTick) {
        this.maxDataPerTick = maxDataPerTick;
    }

    public int getRandomDataPerTick() {
        if (minDataPerTick == maxDataPerTick)
            return minDataPerTick;
        else if (minDataPerTick == -1 || maxDataPerTick == -1)
            return Math.max(minDataPerTick, maxDataPerTick);

        return ThreadLocalRandom.current().nextInt(minDataPerTick, maxDataPerTick);
    }

    public long getSendDelay() {
        return sendDelay;
    }

    public void setSendDelay(long sendDelay) {
        this.sendDelay = sendDelay;
    }

    public int getConnectionCap() {
        return connectionCap;
    }

    public void setConnectionCap(int connectionCap) {
        this.connectionCap = connectionCap;
    }

    @Override public String toString() {
        return "WatchData{" +
                "id='" + id + '\'' +
                ", simDuration=" + simDuration +
                ", startTime=" + startTime +
                ", frequency=" + frequency +
                ", minDataPerTick=" + minDataPerTick +
                ", maxDataPerTick=" + maxDataPerTick +
                ", sendDelay=" + sendDelay +
                ", connectionCap=" + connectionCap +
                '}';
    }
}
