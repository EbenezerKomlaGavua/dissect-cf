package com.ljmu.andre.FitbitSim;

import com.ljmu.andre.FitbitSim.Interfaces.ConnectionEvent;

import hu.mta.sztaki.lpds.cloud.simulator.Timed;
import hu.mta.sztaki.lpds.cloud.simulator.iaas.PhysicalMachine;
import hu.mta.sztaki.lpds.cloud.simulator.io.Repository;

/**
 * Created by Andre on 31/01/2017.
 */
public class Smartphone extends Timed implements ConnectionEvent {
    private PhysicalMachine physicalMachine;

    public Smartphone(PhysicalMachine physicalMachine) {
        this.physicalMachine = physicalMachine;
    }

    public PhysicalMachine getPhysicalMachine() {
        return physicalMachine;
    }

    public Repository getRepository() {
        return physicalMachine.localDisk;
    }

    @Override public void tick(long fires) {
    }

    @Override public void connectionStarted() {
        System.out.println("Smartphone received connection init");
    }

    @Override public void connectionFinished(State connectionState) {
        System.out.println("Smartphone connection finished: " + connectionState);
        long freeCap = getRepository().getFreeStorageCapacity();
        long maxCap = getRepository().getMaxStorageCapacity();

        System.out.println("Smartphone disk: " + freeCap + "/" + maxCap);
    }
}
