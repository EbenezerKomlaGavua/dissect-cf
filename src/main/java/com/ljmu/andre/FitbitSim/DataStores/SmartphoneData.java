package com.ljmu.andre.FitbitSim.DataStores;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Andre on 31/01/2017.
 */
public class SmartphoneData {
    @SerializedName("Id")
    private String id;

    @SerializedName("Simulation Duration")
    private long simDuration;

    @SerializedName("Start Time")
    private long startTime;

    @SerializedName("Frequency")
    private long frequency;

    @SerializedName("Linked Watch IDs")
    private String[] watchIds;

    public SmartphoneData() {
    }

    public String getId() {
        return id;
    }

    public long getSimDuration() {
        return simDuration;
    }

    public long getStartTime() {
        return startTime;
    }

    public long getFrequency() {
        return frequency;
    }

    public String[] getWatchIds() {
        return watchIds;
    }
}
