package com.ljmu.andre.FitbitSim.DataStores;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlID;

import hu.mta.sztaki.lpds.cloud.simulator.iaas.PhysicalMachine;
import hu.mta.sztaki.lpds.cloud.simulator.iaas.constraints.ConstantConstraints;

/**
 * Created by Andre on 26/01/2017.
 */
public class PhysicalMachineData implements Serializable {
    private double cores;
    private double perCoreProcessing;
    private long memory;
    private int onDelay;
    private int offDelay;
    private RepositoryData repositoryData;

    public PhysicalMachineData() {
    }

    public PhysicalMachineData(double cores, double perCoreProcessing, long memory, int onDelay,
                               int offDelay, RepositoryData repositoryData) {
        this.cores = cores;
        this.perCoreProcessing = perCoreProcessing;
        this.memory = memory;
        this.onDelay = onDelay;
        this.offDelay = offDelay;
        this.repositoryData = repositoryData;
    }

    public double getCores() {
        return cores;
    }

    public void setCores(double cores) {
        this.cores = cores;
    }

    public double getPerCoreProcessing() {
        return perCoreProcessing;
    }

    public void setPerCoreProcessing(double perCoreProcessing) {
        this.perCoreProcessing = perCoreProcessing;
    }

    public long getMemory() {
        return memory;
    }

    public void setMemory(long memory) {
        this.memory = memory;
    }

    public int getOnDelay() {
        return onDelay;
    }

    public void setOnDelay(int onDelay) {
        this.onDelay = onDelay;
    }

    public int getOffDelay() {
        return offDelay;
    }

    public void setOffDelay(int offDelay) {
        this.offDelay = offDelay;
    }

    public RepositoryData getRepositoryData() {
        return repositoryData;
    }

    public void setRepositoryData(RepositoryData repositoryData) {
        this.repositoryData = repositoryData;
    }


    @Override public String toString() {
        return "PhysicalMachineData{" +
                "cores=" + cores +
                ", perCoreProcessing=" + perCoreProcessing +
                ", memory=" + memory +
                ", onDelay=" + onDelay +
                ", offDelay=" + offDelay +
                ", repositoryData=" + repositoryData +
                '}';
    }
}
