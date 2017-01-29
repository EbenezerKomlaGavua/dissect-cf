package com.ljmu.andre.FitbitSim.DataStores;

import java.io.Serializable;
import java.util.EnumMap;

import hu.mta.sztaki.lpds.cloud.simulator.energy.powermodelling.PowerState;
import hu.mta.sztaki.lpds.cloud.simulator.iaas.PhysicalMachine.PowerStateKind;
import hu.mta.sztaki.lpds.cloud.simulator.iaas.PhysicalMachine.State;
import hu.mta.sztaki.lpds.cloud.simulator.util.PowerTransitionGenerator;

/**
 * Created by Andre on 28/01/2017.
 */
public class PowerStateTransitionData implements Serializable {
    private double minPower;
    private double idlePower;
    private double maxPower;
    private double diskDivider;
    private double netDivider;

    public PowerStateTransitionData() {
    }

    public PowerStateTransitionData(double minPower, double idlePower, double maxPower, double diskDivider, double netDivider) {
        this.minPower = minPower;
        this.idlePower = idlePower;
        this.maxPower = maxPower;
        this.diskDivider = diskDivider;
        this.netDivider = netDivider;
    }

    public EnumMap<PowerStateKind, EnumMap<State, PowerState>> getPowerTransitionFromData() {
        try {
            return PowerTransitionGenerator.generateTransitions(
                    this.getMinPower(),
                    this.getIdlePower(),
                    this.getMaxPower(),
                    this.getDiskDivider(),
                    this.getNetDivider()
            );
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        return null;
    }

    public double getMinPower() {
        return minPower;
    }

    public void setMinPower(double minPower) {
        this.minPower = minPower;
    }

    public double getIdlePower() {
        return idlePower;
    }

    public void setIdlePower(double idlePower) {
        this.idlePower = idlePower;
    }

    public double getMaxPower() {
        return maxPower;
    }

    public void setMaxPower(double maxPower) {
        this.maxPower = maxPower;
    }

    public double getDiskDivider() {
        return diskDivider;
    }

    public void setDiskDivider(double diskDivider) {
        this.diskDivider = diskDivider;
    }

    public double getNetDivider() {
        return netDivider;
    }

    public void setNetDivider(double netDivider) {
        this.netDivider = netDivider;
    }
}
