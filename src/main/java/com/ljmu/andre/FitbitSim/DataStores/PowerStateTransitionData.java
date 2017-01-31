package com.ljmu.andre.FitbitSim.DataStores;

import java.io.Serializable;
import java.util.Arrays;
import java.util.EnumMap;

import javax.xml.bind.annotation.XmlAccessOrder;
import javax.xml.bind.annotation.XmlAccessorOrder;

import hu.mta.sztaki.lpds.cloud.simulator.energy.powermodelling.PowerState;
import hu.mta.sztaki.lpds.cloud.simulator.energy.powermodelling.PowerState.ConsumptionModel;
import hu.mta.sztaki.lpds.cloud.simulator.iaas.PhysicalMachine;
import hu.mta.sztaki.lpds.cloud.simulator.iaas.PhysicalMachine.PowerStateKind;
import hu.mta.sztaki.lpds.cloud.simulator.iaas.PhysicalMachine.State;

/**
 * Created by Andre on 28/01/2017.
 */
@XmlAccessorOrder(XmlAccessOrder.ALPHABETICAL)
public class PowerStateTransitionData implements Serializable {
    private PowerKind[] powerKinds;

    public PowerStateTransitionData() {
    }

    public PowerStateTransitionData(PowerKind[] powerKinds) {
        this.powerKinds = powerKinds;
    }

    public EnumMap<PowerStateKind, EnumMap<State, PowerState>> getPowerTransitionFromData() throws IllegalAccessException, NoSuchFieldException, InstantiationException, ClassNotFoundException {
        EnumMap<PowerStateKind, EnumMap<State, PowerState>> masterMap = new EnumMap<PowerStateKind, EnumMap<State, PowerState>>(PowerStateKind.class);

        for(PowerKind powerKind : powerKinds) {
            PowerStateKind powerStateKind = PowerStateKind.valueOf(powerKind.getKind().toLowerCase());


            EnumMap<State, PowerState> powerStateMap = new EnumMap<State, PowerState>(PhysicalMachine.State.class);

            for (Power power : powerKind.getPowers()) {
                String strInState = power.getInState().toUpperCase();
                PowerState powerState = new PowerState(power.getIdle(), power.getMax(), (Class<? extends ConsumptionModel>) Class.forName(power.getModel()));

                // Assign DEFAULT logic to everything without a value
                if (strInState.equals("DEFAULT")) {
                    for (PhysicalMachine.State state : PhysicalMachine.State.values()) {
                        if (powerStateMap.get(state) == null)
                            powerStateMap.put(state, powerState);
                    }
                } else {
                    State state = State.valueOf(strInState);
                    powerStateMap.put(state, powerState);
                }
            }

            masterMap.put(powerStateKind, powerStateMap);
        }

        return  masterMap;
    }

    public PowerKind[] getPowerKinds() {
        return powerKinds;
    }

    public void setPowerKinds(PowerKind[] powerKinds) {
        this.powerKinds = powerKinds;
    }

    @Override public String toString() {
        return "PowerStateTransitionData{" +
                "powerKinds=" + Arrays.toString(powerKinds) +
                '}';
    }

    public static class PowerKind implements Serializable {
        private String kind;
        private Power[] powers;

        public PowerKind() {}

        public PowerKind( String kind, Power[] powers ) {
            this.kind = kind;
            this.powers = powers;
        }

        public String getKind() {
            return kind;
        }

        public void setKind(String kind) {
            this.kind = kind;
        }

        public Power[] getPowers() {
            return powers;
        }

        public void setPowers(Power[] powers) {
            this.powers = powers;
        }

        @Override public String toString() {
            return "PowerKind{" +
                    "kind='" + kind + '\'' +
                    ", powers=" + Arrays.toString(powers) +
                    '}';
        }
    }

    public static class Power implements Serializable {
        private String model;
        private double idle;
        private double max;
        private String inState;

        public Power() {

        }

        public Power(
                String model,
                double idle,
                double max,
                String inState) {
            this.model = model;
            this.idle = idle;
            this.max = max;
            this.inState = inState;
        }

        public String getModel() {
            return model;
        }

        public void setModel(String model) {
            this.model = model;
        }

        public double getIdle() {
            return idle;
        }

        public void setIdle(double idle) {
            this.idle = idle;
        }

        public double getMax() {
            return max;
        }

        public void setMax(double max) {
            this.max = max;
        }

        public String getInState() {
            return inState;
        }

        public void setInState(String inState) {
            this.inState = inState;
        }

        @Override public String toString() {
            return "Power{" +
                    "model=" + model +
                    ", idle=" + idle +
                    ", max=" + max +
                    ", inState='" + inState + '\'' +
                    '}';
        }
    }
}
