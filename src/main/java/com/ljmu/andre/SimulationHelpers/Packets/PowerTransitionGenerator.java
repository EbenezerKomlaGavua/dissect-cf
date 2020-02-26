package com.ljmu.andre.SimulationHelpers.Packets;

import java.util.EnumMap;
import java.util.Map;

import hu.mta.sztaki.lpds.cloud.simulator.energy.powermodelling.ConstantConsumptionModel;
import hu.mta.sztaki.lpds.cloud.simulator.energy.powermodelling.LinearConsumptionModel;
import hu.mta.sztaki.lpds.cloud.simulator.energy.powermodelling.PowerState;
import hu.mta.sztaki.lpds.cloud.simulator.iaas.PhysicalMachine;


import java.util.HashMap;

//public class PowerTransitionGenerator {

	//public interface PowerStateKind {

	//}

	/**
	 * When defining powertransitions for the PM one has to label each transiton's
	 * properties with a kind
	 * 
	 * @author "Gabor Kecskemeti, Laboratory of Parallel and Distributed Systems,
	 *         MTA SZTAKI (c) 2014"
	 * 
	 */
	//public static enum PowerStateKind {
		/**
		 * the powerstate definitions belong to the cpu and memory resources of the PM
		 */
		//host,
		/**
		 * the powerstate definitions belong to the local disk of the PM
		 */
		//storage,
		/**
		 * the powerstate definitions belong to the network interface of the PM
		 */
		//network
	//}
	
	
	
	
	/**
	 * The generator function that derives the power transition and power state
	 * definitions from a few simple parameters. The generated power states will
	 * all be based on the linear consumption model (except during power off
	 * state).
	 * 
	 * @param minpower
	 *            the power (in W) to be drawn by the PM while it is completely
	 *            switched off (but plugged into the wall socket)
	 * @param idlepower
	 *            the power (in W) to be drawn by the PM's CPU while it is
	 *            running but not doing any useful tasks.
	 * @param maxpower
	 *            the power (in W) to be drawn by the PM's CPU if it's CPU is
	 *            completely utilized
	 * @param diskDivider
	 *            the ratio of the PM's disk power draw values compared to the
	 *            it's CPU's power draw values
	 * @param netDivider
	 *            the ratio of the PM's network power draw values compared to
	 *            the it's CPU's power draw values
	/// * @return a power state setup useful for instantiating PMs
	 * @throws SecurityException
	 *             if the power state to be created failed to instantiate
	 *             properly
	 * @throws InstantiationException
	 *             if the power state to be created failed to instantiate
	 *             properly
	 * @throws IllegalAccessException
	 *             if the power state to be created failed to instantiate
	 *             properly
	 * @throws NoSuchFieldException
	 *             if the power state to be created failed to instantiate
	 *             properly
	 */
	//public static EnumMap<PhysicalMachine.PowerStateKind, EnumMap<PhysicalMachine.State, PowerState>> generateTransitions(
			//double minpower, double idlepower, double maxpower, double diskDivider, double netDivider)
					//throws SecurityException, InstantiationException, IllegalAccessException, NoSuchFieldException {
		//EnumMap<PhysicalMachine.PowerStateKind, EnumMap<PhysicalMachine.State, PowerState>> returner = new EnumMap<PhysicalMachine.PowerStateKind, EnumMap<PhysicalMachine.State, PowerState>>(
				//PhysicalMachine.PowerStateKind.class);
		//EnumMap<PhysicalMachine.State, PowerState> hostStates = new EnumMap<PhysicalMachine.State, PowerState>(
				///PhysicalMachine.State.class);
		//returner.put(PhysicalMachine.PowerStateKind.host, hostStates);
		//EnumMap<PhysicalMachine.State, PowerState> diskStates = new EnumMap<PhysicalMachine.State, PowerState>(
				///PhysicalMachine.State.class);
		//returner.put(PhysicalMachine.PowerStateKind.storage, diskStates);
		//EnumMap<PhysicalMachine.State, PowerState> netStates = new EnumMap<PhysicalMachine.State, PowerState>(
				//PhysicalMachine.State.class);
		//returner.put(PhysicalMachine.PowerStateKind.network, netStates);
		//PowerState hostDefault = new PowerState(idlepower, maxpower - idlepower, LinearConsumptionModel.class);
		//PowerState diskDefault = new PowerState(idlepower / diskDivider / 2, (maxpower - idlepower) / diskDivider / 2,
				//LinearConsumptionModel.class);
		//PowerState netDefault = new PowerState(idlepower / netDivider / 2, (maxpower - idlepower) / netDivider / 2,
				//LinearConsumptionModel.class);
		//for (PhysicalMachine.State aState : PhysicalMachine.StatesOfHighEnergyConsumption) {
			//hostStates.put(aState, hostDefault);
			//diskStates.put(aState, diskDefault);
			//netStates.put(aState, netDefault);
		//}

		//hostStates.put(PhysicalMachine.State.OFF, new PowerState(minpower, 0, ConstantConsumptionModel.class));
	//	diskStates.put(PhysicalMachine.State.OFF, new PowerState(0, 0, ConstantConsumptionModel.class));
		//netStates.put(PhysicalMachine.State.OFF, new PowerState(0, 0, ConstantConsumptionModel.class));
		//return returner;
	//}
	/**
	 * fetches the required power state from the corresponding power state map. If
	 * the new state is not listed, it serves back the default mapping
	 * 
	 * @param theMap
	 *            The map to look up the new power state
	 * @param newState
	 *            the textual spec of the power state
	 * @return the power state to be used in accordance to the textual spec
	 */
///	public static PowerState getPowerStateFromMap(final Map<String, PowerState> theMap, final String newState) {
		//PowerState returner;
		//if ((returner = theMap.get(newState)) == null) {
			//Object defaultPowerState = null;
			//returner = theMap.get(defaultPowerState);
		//}
		//return returner;
	//}

//}

