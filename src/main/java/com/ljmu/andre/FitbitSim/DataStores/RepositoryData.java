package com.ljmu.andre.FitbitSim.DataStores;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by Andre on 26/01/2017.
 */
public class RepositoryData implements Serializable {
    private String id;
    private long capacity;
    private long maxInBW;
    private long maxOutBW;
    private long diskBW;
    private Map<String, Integer> latencyMap;

    public RepositoryData() {

    }

    public RepositoryData(String id, long capacity, long maxInBW, long maxOutBW, long diskBW, Map<String, Integer> latencyMap) {
        this.id = id;
        this.capacity = capacity;
        this.maxInBW = maxInBW;
        this.maxOutBW = maxOutBW;
        this.diskBW = diskBW;
        this.latencyMap = latencyMap;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getCapacity() {
        return capacity;
    }

    public void setCapacity(long capacity) {
        this.capacity = capacity;
    }

    public long getMaxInBW() {
        return maxInBW;
    }

    public void setMaxInBW(long maxInBW) {
        this.maxInBW = maxInBW;
    }

    public long getMaxOutBW() {
        return maxOutBW;
    }

    public void setMaxOutBW(long maxOutBW) {
        this.maxOutBW = maxOutBW;
    }

    public long getDiskBW() {
        return diskBW;
    }

    public void setDiskBW(long diskBW) {
        this.diskBW = diskBW;
    }

    public Map<String, Integer> getLatencyMap() {
        return latencyMap;
    }

    public void setLatencyMap(Map<String, Integer> latencyMap) {
        this.latencyMap = latencyMap;
    }

    @Override public String toString() {
        return "RepositoryData{" +
                "id='" + id + '\'' +
                ", capacity=" + capacity +
                ", maxInBW=" + maxInBW +
                ", maxOutBW=" + maxOutBW +
                ", diskBW=" + diskBW +
                ", latencyMap=" + latencyMap +
                '}';
    }
}
