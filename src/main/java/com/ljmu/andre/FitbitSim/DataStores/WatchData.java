package com.ljmu.andre.FitbitSim.DataStores;

import java.io.Serializable;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Andre on 26/01/2017.
 */
public class WatchData implements Serializable {
    private String id;
    private long simDuration;
    private long startTime;
    private long frequency;
    private int minDataPerTick;
    private int maxDataPerTick;
    private long sendDelay;

    public WatchData() {
    }

    public WatchData(
            String id,
            long simDuration,
            long startTime,
            long frequency,
            int minDataPerTick,
            int maxDataPerTick,
            long sendDelay) {
        this.id = id;
        this.simDuration = simDuration;
        this.startTime = startTime;
        this.frequency = frequency;
        this.minDataPerTick = minDataPerTick;
        this.maxDataPerTick = maxDataPerTick;
        this.sendDelay = sendDelay;
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
        return getStartTime() + getSimDuration();
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
        if(minDataPerTick == maxDataPerTick)
            return minDataPerTick;
        else if(minDataPerTick == -1 || maxDataPerTick == -1)
            return Math.max(minDataPerTick, maxDataPerTick);

        return ThreadLocalRandom.current().nextInt(minDataPerTick, maxDataPerTick);
    }

    public long getSendDelay() {
        return sendDelay;
    }

    public void setSendDelay(long sendDelay) {
        this.sendDelay = sendDelay;
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
                '}';
    }
}
