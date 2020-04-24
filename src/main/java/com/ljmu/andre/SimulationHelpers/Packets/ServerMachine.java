package com.ljmu.andre.SimulationHelpers.Packets;

import java.util.ArrayList;
import java.util.List;

import com.ljmu.andre.SimulationHelpers.Application;
import com.ljmu.andre.SimulationHelpers.ConnectionEvent;
import com.ljmu.andre.SimulationHelpers.ConnectionEvent.State;
import com.ljmu.andre.SimulationHelpers.Utils.Logger;

import hu.mta.sztaki.lpds.cloud.simulator.Timed;
import hu.mta.sztaki.lpds.cloud.simulator.iaas.PhysicalMachine;
import hu.mta.sztaki.lpds.cloud.simulator.iaas.resourcemodel.ResourceConsumption;
import hu.mta.sztaki.lpds.cloud.simulator.iaas.resourcemodel.ResourceConsumption.ConsumptionEvent;
import hu.mta.sztaki.lpds.cloud.simulator.io.Repository;

public class ServerMachine extends Timed implements ConsumptionEvent, ConnectionEvent {
	private static final Logger logger = new Logger(ServerMachine.class);
	private static final int SUBSCRIBE_FREQ = 5;
	private String ServerMachineId;
	PhysicalMachine ServerMachine;
	
	private ClientMachine ClientMachine;
	
	private static int totalPackets = 0;
	private static int successfulPackets = 0;
	private static int failedPackets = 0;
	//private DataPacket datapacket;
	 Repository repository;
	private BasePacket packet;
	private List<String> Packet = new ArrayList<String>();

	/**
	 * 
	 * Call {@link this#connectDevice(ConnectionEvent)}
	 * 
	 * if result is TRUE, update the Repository's Latency Map
	 *
	 * @param device  - The Device to connect to this one
	 * 
	 * @param latency - The Latency of the connection
	 * 
	 * @return True if successfully connected
	 * 
	 */


	// Create the constructor for the serverMachine
	public ServerMachine(PhysicalMachine ServerMachine, BasePacket packet, ClientMachine ClientMachine,
			Repository repository, String ServerMachineId) {
		this.ServerMachine = ServerMachine;
		this.packet = packet;
		this.ClientMachine = ClientMachine;
		this.ServerMachineId = ServerMachineId;
		this.repository = ServerMachine.localDisk;
	}

	// Get the PhysicalMachine call ServerMachine
	public PhysicalMachine getPhysicalMachine() {
		return ServerMachine;
	}
	// Get the repository of the ServerMachine
	@Override
	public Repository getRepository() {
		// TODO Auto-generated method stub
		return ServerMachine.localDisk;
	}
	
	// Get the Id of the ServerMachine
	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return getRepository().getName();
	}
	
	public PhysicalMachine getConsumptionEvent() {
		return ServerMachine;

	}

	
	//Start the ServerMachine and subscribe it to the frequency
	public void start() {
		
		logger.log("Started [Frequency: %s]", subscribe(SUBSCRIBE_FREQ));
		// logger.log("Started [ID: %s] [Success: %s]", getRepository().getName(),
				// subscribe(SUBSCRIBE_FREQ));
	}

	// Initial connection with the ClientMachine
	@Override
	public void connectionStarted(ConnectionEvent ClientMachine) {
		// TODO Auto-generated method stub
		logger.log("Received connection init: " + ClientMachine.getRepository().getName());
	}
	
	// Bind the ClientMachine to the ServerMachine
	public void bindClientMachine(ClientMachine ClientMachine) {
		this.ClientMachine = ClientMachine;

	}
	
	// Receive packets sent from the clientMachine
	public void receivePacket(final ConnectionEvent ClientMachine, final ConnectionEvent ServerMachine,
			BasePacket packet) {
		final long reqStart = Timed.getFireCount();
		Scenarioo.logMessage(hashCode() + " Server is not responding, let's wait!");
		// if (ServerMachine.getState().e)

	}

	/**
	 * public void receivePacket(final ClientMachine fromTheClientMachine) { final
	 * long reqStart = Timed.getFireCount(); Scenarioo.logMessage(hashCode() + "
	 * Server is not responding, let's wait!"); //if (ServerMachine.getState().e)
	 * 
	 * 
	 * 
	 * }
	 **/
	/*
	 * Subscribe this device with a frequency of {@link this#SUBSCRIBE_FREQ}
	 */

	public void tick(long fires) {
		// TODO Auto-generated method stub
		logger.log("Tick: " + fires);
	}

	

	@Override
	public void conComplete() {
		// TODO Auto-generated method stub
		logger.log("Packet[" + packet.id + "] successfully sent");

		// source.connectionFinished(source, State.SUCCESS, packet);
		// ServerMachine.connectionFinished(ClientMachine, State.SUCCESS, packet);
	}

	@Override
	public void conCancelled(ResourceConsumption problematic) {
		// TODO Auto-generated method stub

	}

	// public String getAddress() {
	// return Address;
//	}

	

	/**
	 * Signal that a packet has been transferred to this Device If the Packet is a
	 * RoutingPacket and is not destined for this device, it is then forwarded
	 *
	 * @param source          - The Device the Packet has been sent from
	 * @param connectionState - The State of the connection
	 * @param packet          - The Packet that was sent
	 */

// close connection and print storage metrics
	@Override
	public void connectionFinished(ConnectionEvent ClientMachine, State connectionState, BasePacket packet) {
		// TODO Auto-generated method stub

		// Check if the packet is a RoutingPacket and if the connection has failed \\
		if (packet instanceof RoutingPacket && connectionState != State.FAILED) {
			RoutingPacket routingPacket = (RoutingPacket) packet;

			// Get the Device this packet should move to next and remove it from the queue
			// \\
			ConnectionEvent target = routingPacket.getRoute().poll();

			// If the target is Null or the TargetID is that of this Device \\
			// Unbox the Payload and recurse this method again with the payload \\
			if (target == null || target.getId().equals(this.getId())) {
				logger.log("Found Destination [Null? %s]", target == null);
				this.connectionFinished(routingPacket.getSource(), connectionState, routingPacket.getPayload());
				// } else {
				// Otherwise, forward the packet to the next target \\
				// logger.log("Forwarding!");
				// PacketHandler.sendPacket(this, target, routingPacket);
			}
			// } else {
			packetTransaction(connectionState == State.SUCCESS);

			// If the packet is not a RoutingPacket or the connection FAILED \\
			// Signal the outer class that a full connection cycle has finished \\
			connectionFinished(ClientMachine, connectionState, packet);
			logger.log("Connection finished: " + connectionState);
			printStorageMetrics();

		}
	}

	public static void packetTransaction(boolean successful) {
		if (successful)
			successfulPackets++;
		else
			failedPackets++;

		totalPackets++;
	}


	private void printStorageMetrics() {
		long freeCap = getRepository().getFreeStorageCapacity();
		long maxCap = getRepository().getMaxStorageCapacity();
		logger.log("Disk: " + freeCap + "/" + maxCap);

	}


	@Override
	public List<ConnectionEvent> getConnectedDevices() {
		// TODO Auto-generated method stub
		return null;
	}

	

	public void consumptionEvent() {
		// TODO Auto-generated method stub

	}

}
