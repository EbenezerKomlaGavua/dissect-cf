package com.ljmu.andre.FitbitSim;

import com.ljmu.andre.FitbitSim.DataStores.PhysicalMachineData;
import com.ljmu.andre.FitbitSim.DataStores.RepositoryData;
import com.ljmu.andre.FitbitSim.DataStores.WatchData;
import com.ljmu.andre.FitbitSim.Models.GenericModel;
import com.ljmu.andre.FitbitSim.Models.WatchModel;

import java.io.Serializable;

import hu.mta.sztaki.lpds.cloud.simulator.Timed;
import hu.mta.sztaki.lpds.cloud.simulator.iaas.PhysicalMachine;
import hu.mta.sztaki.lpds.cloud.simulator.io.Repository;

/**
 * Created by Andre on 26/01/2017.
 */
public class Watch extends Timed implements Serializable {
    private transient Repository targetRepo;
    private transient PhysicalMachine physicalMachine;

    private WatchModel watchModel;


    private boolean isBuilt = false;


    public Watch(PhysicalMachineData pmData, WatchData watchData, Repository targetRepo) {
        createPMachineFromData(pmData);
        createWatchModelFromData(watchData);
        this.targetRepo = targetRepo;
    }

    @Override
    public void tick(long fires) {

    }

    public void createWatchModelFromData(WatchData watchData) {
        this.watchModel = new WatchModel(
                watchData.getId(),
                watchData.getSimDuration(),
                watchData.getStartTime(),
                watchData.getStopTime(),
                watchData.getFrequency(),
                watchData.getFileSize(),
                watchData.getActionDelay()
        );
    }

    public void createPMachineFromData(PhysicalMachineData pmData) {
        RepositoryData repoBuilder = pmData.getRepositoryData();

        Repository pmRepo = new Repository(
                repoBuilder.getCapacity(),
                repoBuilder.getId(),
                repoBuilder.getMaxInBW(),
                repoBuilder.getMaxOutBW(),
                repoBuilder.getDiskBW(),
                repoBuilder.getLatencyMap()
        );

        this.physicalMachine =
                new PhysicalMachine(
                        pmData.getCores(),
                        pmData.getPerCoreProcessing(),
                        pmData.getMemory(),
                        pmRepo,
                        pmData.getOnDelay(),
                        pmData.getOffDelay(),
                        null
                );
    }

    public Repository getTargetRepo() {
        return targetRepo;
    }

    public void setTargetRepo(Repository targetRepo) {
        this.targetRepo = targetRepo;
    }

    public PhysicalMachine getPhysicalMachine() {
        return physicalMachine;
    }

    public void setPhysicalMachine(PhysicalMachine physicalMachine) {
        this.physicalMachine = physicalMachine;
    }

    public GenericModel getWatchModel() {
        return watchModel;
    }
}
