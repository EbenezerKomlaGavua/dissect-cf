package com.ljmu.andre.SimulationHelpers.Packets;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import com.ljmu.andre.SimulationHelpers.ConnectionEvent;

import com.ljmu.andre.SimulationHelpers.ConnectionEvent.State;
import com.ljmu.andre.SimulationHelpers.Utils.Logger;

import hu.mta.sztaki.lpds.cloud.simulator.DeferredEvent;
import hu.mta.sztaki.lpds.cloud.simulator.Timed;
import hu.mta.sztaki.lpds.cloud.simulator.iaas.PhysicalMachine;

import hu.mta.sztaki.lpds.cloud.simulator.iaas.resourcemodel.ResourceConsumption;
import hu.mta.sztaki.lpds.cloud.simulator.iaas.resourcemodel.ResourceConsumption.ConsumptionEvent;
import hu.mta.sztaki.lpds.cloud.simulator.io.Repository;
import hu.mta.sztaki.lpds.cloud.simulator.io.NetworkNode.NetworkException;
import hu.mta.sztaki.lpds.cloud.simulator.DeferredEvent;

public class ClientMachine extends Timed implements ConsumptionEvent, ConnectionEvent {
	private static final Logger logger = new Logger(ClientMachine.class);
	private static final int SUBSCRIBE_FREQ = 5;
	private static PhysicalMachine ClientMachine;
	// private String Address;
	/// private int Port;
	private String Id;
	private static ServerMachine ServerMachine;
	// private List<String> failedPacketIds = new ArrayList<String>();
	private BasePacket packet;
	// private DataPacket datapacket;
	// private static int totalPackets = 0;
	// private static int successfulPackets = 0;
	// private static int failedPackets = 0;
	private static Queue<ConnectionEvent> ConnectionRoute;
	// private List<ConnectionEvent> connectedDevices = new
	// ArrayList<ConnectionEvent>();
	private List<String> Packet = new ArrayList<String>();

	// private List<Job> jobList;
	// private int jobNumber = 0;
	private int NumberOfPackets = 1;
	private int PacketsCount = 5;
	// private int routingPacket =10;
	// ConnectionEvent ClientMachine2;

	/**
	 * Call {@link this#connectDevice(ConnectionEvent)} if result is TRUE, update
	 * the Repository's Latency Map
	 *
	 * @param device  - The Device to connect to this one
	 * @param latency - The Latency of the connection
	 * @return True if successfully connected
	 */
	/**
	 * Initiate a new Device and claim a PhysicalMachine for it
	 *
	 * @param id - The ID of the Device
	 */
	public ClientMachine(PhysicalMachine ClientMachine, ServerMachine ServerMachine, BasePacket packet,
			Repository repository, String Id) {
		this.ClientMachine = ClientMachine;
		this.ServerMachine = ServerMachine;
		// this.Address= Address;
		// this.Port= Port;
		this.Id = Id;
		this.packet = packet;
	}

	/**
	 * Subscribe this device with a frequency of {@link this#SUBSCRIBE_FREQ}
	 */
	// public void start() {
	/// logger.log("Started [ID: %s] [Success: %s]", id, subscribe(SUBSCRIBE_FREQ));
	// }

	public PhysicalMachine getPhysicalMachine() {
		return ClientMachine;
	}

	public ConsumptionEvent getConsumptionEvent(final ConsumptionEvent ClientMachine,
			final ConnectionEvent ServerMachine, final BasePacket packet) {
		return ClientMachine;

	}

	// Binding the client to the server to establish a connection. The client must
	// subscribe for server to accept
	public void bindServerMachine(ServerMachine ServerMachine) {
		this.ServerMachine = ServerMachine;
		BasePacket bindPacket = new SubscriptionPacket(true).setShouldStore(true);
		PacketHandler.sendPacket(this, ServerMachine, bindPacket);
		// PacketHandler.sendPacket(this, ServerMachine, new SubscriptionPacket(false));
	}

	// public void sendDataClientMachine(ClientMachine ClientMachine, ServerMachine
	// ServerMachine, BasePacket packet) {
	/// BasePacket sendPacket = new RoutingPacket("Data", packet, ClientMachine,
	// null);
	// PacketHandler.sendPacket(ClientMachine, ServerMachine, sendPacket);
	// }

	public void sendPacket(final ConnectionEvent ClientMachine, final ConnectionEvent ServerMachine,
			BasePacket packet) {
		// ConsumptionEvent consumptionEvent = getConsumptionEvent(ClientMachine,
		// ServerMachine, packet);
		Scenarioo.logMessage(hashCode() + " Server is not responding, let's wait!");
		BasePacket sendPacket = new RoutingPacket("Data", packet, ClientMachine, null);
		PacketHandler.sendPacket(ClientMachine, ServerMachine, sendPacket);
	}

	@Override
	public void tick(long fires) {
		// TODO Auto-generated method stub

		while (NumberOfPackets < PacketsCount) {
			// ConsumptionEvent consumptionEvent = getConsumptionEvent();
			PacketHandler.sendPacket(this, ServerMachine, new DataPacket("Data", 10, true));

			logger.log("Packet: " + NumberOfPackets);
			NumberOfPackets++;

			// PacketHandler.sendPacket(this, ServerMachine, new SubscriptionPacket(false));

		}

		connectionFinished(ServerMachine, null, packet);
		// handleSuccess(ServerMachine, packet);

		stop();

		/// connectionFinished(ServerMachine, null, packet);
		// System.exit(5);
	}

	/**
	 * public static void sendPacket(final ConnectionEvent ClientMachine, final
	 * ConnectionEvent ServerMachine, BasePacket packet) { ConsumptionEvent
	 * consumptionEvent = getConsumptionEvent(); BasePacket sendPacket = new
	 * RoutingPacket("Data", packet, ClientMachine, null);
	 * PacketHandler.sendPacket(ClientMachine, ServerMachine, sendPacket); }
	 * 
	 * /**
	 * 
	 * public void sendPacket(final ServerMachine fromTheServerMachine) throws
	 * NetworkException { if (fromTheServerMachine.getRepository() == null) {
	 * Scenarioo.logMessage(hashCode() + " Server is not responding, let's wait!");
	 * // The ServerMachine is not yet ready, let's wait a minute. new
	 * DeferredEvent(60 * 1000) {
	 * 
	 * @Override protected void eventAction() { try {
	 * 
	 *           ConsumptionEvent consumptionEvent = getConsumptionEvent();
	 *           PacketHandler.sendPacket(ClientMachine, ServerMachine, packet);
	 * 
	 *           // sendPacket(fromTheServerMachine); } catch (NetworkException e) {
	 *           // TODO Auto-generated catch block e.printStackTrace(); } } }; //}
	 *           else {
	 * 
	 *           // Scenarioo.logMessage(hashCode() + " Server is not responding,
	 *           let's wait!"); // PacketHandler.sendPacket(this, ServerMachine,
	 *           datapacket); // PhysicalMachine consumptionEvent =
	 *           getConsumptionEvent();
	 * 
	 *           // PhysicalMachine consumptionEvent = getConsumptionEvent(); } }
	 **/
	protected static ConsumptionEvent getConsumptionEvent(final ConnectionEvent ClientMachine,
			final ConnectionEvent ServerMachine, BasePacket packet) {
		// TODO Auto-generated method stub
		return getConsumptionEvent(ClientMachine, ServerMachine, packet);
	}

	public static Queue<ConnectionEvent> getConnectionRoute(PhysicalMachine clientMachine, String ServerMachineId) {

		return ConnectionRoute;

	}

	@Override
	public void conComplete() {
		// TODO Auto-generated method stub
		logger.log("Packet[" + packet.id + "] successfully sent");
		getConnectionRoute(ClientMachine, "Id");
	}

	@Override
	public void conCancelled(ResourceConsumption problematic) {
		// TODO Auto-generated method stub
		// ClientMachine.connectionFinished(ClientMachine, State.FAILED, packet);
		logger.log("Cancelled: " + problematic.toString());

	}

	/**
	 * private static ConsumptionEvent getConsumptionEvent(final ConnectionEvent
	 * source, final ConnectionEvent target, final BasePacket packet) { return new
	 * ConsumptionEvent() {
	 * 
	 * @Override public void conComplete() { logger.log("Packet[" + packet.id + "]
	 *           successfully sent");
	 * 
	 *           //source.connectionFinished(source, State.SUCCESS, packet);
	 *           target.connectionFinished(source, State.SUCCESS, packet);
	 * 
	 *           // Packets that are not intended to stay after transfer are
	 *           deregistered here // This allows for failed packets to be wiped if
	 *           (packet.shouldDeregister(source))
	 *           source.getRepository().deregisterObject(packet); if
	 *           (packet.shouldDeregister(target))
	 *           target.getRepository().deregisterObject(packet); }
	 * 
	 * 
	 * @Override
	 * 
	 * 			public void conCancelled(ResourceConsumption problematic) {
	 * 
	 *           source.connectionFinished(source, State.FAILED, packet);
	 * 
	 *           logger.log("Cancelled: " + problematic.toString());
	 * 
	 *           }
	 * 
	 *           };
	 * 
	 *           }
	 * 
	 * 
	 **/
	private void stop() {

		logger.log("Stopped: " + unsubscribe());
	}

	@Override
	public void connectionStarted(ConnectionEvent ServerMachine) {

		logger.log("Received connection init: " + ServerMachine.getRepository().getName());

	}

	public void start() {

		logger.log("Started [Frequency: %s]", subscribe(0));

	}

	/**
	 * 
	 * 
	 * printStorageMetrics(); //handleSuccess(source, packet); if (connectionState
	 * == State.SUCCESS) logger.log("Connection finished: " + connectionState);
	 * handleSuccess(ClientMachine, packet);
	 * 
	 * }
	 **/

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
			if (ServerMachine == null || ServerMachine.getId().equals(this.getId())) {
				logger.log("Found Destination [Null? %s]", ServerMachine == null);
				this.connectionFinished(routingPacket.getSource(), connectionState, routingPacket.getPayload());
			} else {
				// Otherwise, forward the packet to the next target \\
				logger.log("Forwarding!");
				PacketHandler.sendPacket(this, ServerMachine, routingPacket);
			}
		} else {
			// packetTransaction(connectionState == State.SUCCESS);

			// If the packet is not a RoutingPacket or the connection FAILED \\
			// Signal the outer class that a full connection cycle has finished \\
			// connectionFinished(ServerMachine, connectionState, packet);
			logger.log("Connection finished: " + connectionState);
			printStorageMetrics();

		}
	}

	/**
	 * public static void packetTransaction(boolean successful) { if (successful)
	 * successfulPackets++; else failedPackets++;
	 * 
	 * totalPackets++; }
	 * 
	 **/
	private void printStorageMetrics() {
		long freeCap = getRepository().getFreeStorageCapacity();
		long maxCap = getRepository().getMaxStorageCapacity();
		logger.log("Disk: [%s/%s]", freeCap, maxCap);

	}

	private void handleSuccess(ConnectionEvent ServerMachine, BasePacket packet) {
		if (packet instanceof SubscriptionPacket) {
			SubscriptionPacket subPacket = (SubscriptionPacket) packet;
			logger.log("Subscription: " + subPacket.getSubState());
		} else if (packet instanceof DataPacket) {
			logger.log("packet: " + packet);
			// } else {
			// logger.log("Unknown packet type: " + packet.getClass().getName());

		}
	}

	@Override
	public Repository getRepository() {
		// TODO Auto-generated method stub
		return ClientMachine.localDisk;
	}

	@Override
	public List<ConnectionEvent> getConnectedDevices() {
		// TODO Auto-generated method stub
		return getConnectedDevices();
	}

	// If the connection is established, the Id of the server will be obtained
	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return getRepository().getName();
	}

}
