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
	protected PhysicalMachine ClientMachine;
	// private String Address;
	/// private int Port;
	String Id;
	Repository repository;
	protected ServerMachine ServerMachine;
	// private List<String> failedPacketIds = new ArrayList<String>();
	protected BasePacket packet;

	private static Queue<ConnectionEvent> ConnectionRoute;
	 private List<ConnectionEvent> connectedDevices = new ArrayList<ConnectionEvent>();
	private List<String> Packet = new ArrayList<String>();
	private static int successfulPackets;
	private static int failedPackets;
	private static int totalPackets = 0;
	private int NumberOfPackets = 1;
	private int PacketsCount = 5;
	
	

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
	 * 
	 */

	// Create the constructor of the ClientMachine with the necessary attributes
	// that can transfer packet to the ServerMachine
	// The key attributes are the packet (data unit), repository (storage location)
	// and the ID
	public ClientMachine(PhysicalMachine ClientMachine, ServerMachine ServerMachine, BasePacket packet,
			Repository repository, String Id) {
		this.ClientMachine = ClientMachine;
		this.ServerMachine = ServerMachine;
		// this.Address= Address;
		// this.Port= Port;
		this.Id = Id;
		this.packet = packet;
	}

	// The repository of the clientMachine must be set up for effective transfer of
	// packets
	// This also enables establishment of connection between the two devices
	@Override
	public Repository getRepository() {
		// TODO Auto-generated method stub
		return ClientMachine.localDisk;
	}

	// The Id of the ClientMachine is activated by this method and made accessible
	// to all devices on the frequency.
	// The name of the device is easily obtained from the Id.
	// * @param id - The ID of the Device
	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return getRepository().getName();
	}

	// The clientMachine must be started and subscribed for the simulation to commence.
	// The method ensures that all the attributes are made accessible.
	// Subscribe this device with a frequency of {@link this#SUBSCRIBE_FREQ}
	public void start() {

		logger.log("Started [Frequency: %s]", subscribe(0));
	}

	
	// When the ClientMachine is started, a connection must be established to ensure
	// data transfer.
	// This is ascertained by returning the Id of the target Machine.
	// If the connection is established, the Id of the server will be obtained

	///  @return True if successfully connected
	@Override
	public void connectionStarted(ConnectionEvent ServerMachine) {
		logger.log("Received connection init: " + ServerMachine.getRepository().getName());
	}

	
	// Before a formal connection can be established for packets to be transferred,
	// the clientMachine must be bound to the ServerMachine
	// Bind the clientMachine to the serverMachine to establish a connection. The
	// clientMachine must send a subscription packet to the serverMachine
	// The subscription packet is transferred and stored on the serverMachine.
	public void bindServerMachine(ServerMachine ServerMachine) {
		this.ServerMachine = ServerMachine;
		BasePacket bindPacket = new SubscriptionPacket(true).setShouldStore(true);
		PacketHandler.sendPacket(this, ServerMachine, bindPacket);
		// PacketHandler.sendPacket(this, ServerMachine, new SubscriptionPacket(false));
	}

	// In order to ensure that connected devices utilises the appropriate system resources, the consumptionEvent method is called.
	// This ensures the systems resources are availed before packet transfer commences.
		public ConsumptionEvent getConsumptionEvent(final ConsumptionEvent ClientMachine,
				final ConnectionEvent ServerMachine, final BasePacket packet) {
			return ClientMachine;
		}
	
	// when the clietMachine is bound to the ServerMachine, the connection between the two devices is established.
	//However, in order to utilise a routing packet, the connectionRoute must be determined, 
	//This method ensures that the connectionRoute is obtained.
	public static Queue<ConnectionEvent> getConnectionRoute(PhysicalMachine clientMachine, String ServerMachineId) {

		return ConnectionRoute;
	}
	
	
	// After the clientMachine is bound to the serverMachine, packets can now be transferred. 
	//This can be achieved by utilising the connection created between the clientMachine and the serverMachine.
	// A data packet or routing packet is transferred from the clientMachine.
	public void sendPacket(final ConnectionEvent ClientMachine, final ConnectionEvent ServerMachine,
			BasePacket packet) {
		// ConsumptionEvent consumptionEvent = getConsumptionEvent(ClientMachine,
		// ServerMachine, packet);
		Scenarioo.logMessage(hashCode() + " Server is not responding, let's wait!");
		BasePacket sendPacket = new RoutingPacket("Data", packet, ClientMachine, null);
		PacketHandler.sendPacket(ClientMachine, ServerMachine, sendPacket);
	}

	
	// The tick method is called upon to ensure the simulation of data transfer
	// depending on the amount of time specified.
	// This is to ensure that sufficient time is allocated for all the data packets
	// to be transferred from the clientMachine to the ServerMachine.
	// After the transfer is completed, the simulation is stopped and the connection
	// closes.
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
		handleSuccess(ServerMachine, packet);
		stop();
	}

	
	// When the packets have been successful transferred from ClientMachine to ServerMachine
	// This method will be called stop the simulation after the connection is
	// finished and ServerMachine has confirmed successful
	// receipt of packets. The ClientMachine can now unsubscribe from the frequency.
	public void stop() {

		logger.log("Stopped: " + unsubscribe());
	}

	// Successful transfer of packets calls for the execution of this method.
	// This method states the Id of the packets transferred and the returns the path
	// utilised for the transfer.
	@Override
	public void conComplete() {
		// TODO Auto-generated method stub
		logger.log("Packet[" + packet.id + "] successfully sent");
		getConnectionRoute(ClientMachine, "Id");
	}

	// When the transfers of data is incomplete, this method is called to notify the clientMachine.
	//This usually happens when the target device has issues or the frequency isn't stable.
	//The error generated is printed for analysis.
	@Override
	public void conCancelled(ResourceConsumption problematic) {
		// TODO Auto-generated method stub
		/// ClientMachine.connectionFinished(ClientMachine, State.FAILED, packet);
		logger.log("Cancelled: " + problematic.toString());
	}

	
	//This method is designed to obtain the list of connected devices (ClientMachine,ServerMachine) to utilise the connectionEvent.
	@Override
	public List<ConnectionEvent> getConnectedDevices() {
		// TODO Auto-generated method stub
		return connectedDevices;
	}

	
	
	// When the packets are transferred from the clientMachine to the ServerMachine, this method is called to close up the connection.
	//Its main work is to assess if the packets were accurately delivered with the contents intact.
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
			packetTransaction(connectionState == State.SUCCESS);

			// If the packet is not a RoutingPacket or the connection FAILED \\
			// Signal the outer class that a full connection cycle has finished \\
			// connectionFinished(ServerMachine, connectionState, packet);
			logger.log("Connection finished: " + connectionState);
			printStorageMetrics();

		}
	}

	
	// When the data transfer is completed.
		// The connection is closed.
		// and storage metrics are printed out.
		private void printStorageMetrics() {
			long freeCap = getRepository().getFreeStorageCapacity();
			long maxCap = getRepository().getMaxStorageCapacity();
			logger.log("Disk: [%s/%s]", freeCap, maxCap);

		}

		// The method is utilised to determined the successful transfer of data packet. 
		// It is executed after the connection is completed.
		// When the packet is transferred, this method determines the type of packet transferred.
		private void handleSuccess(ConnectionEvent ServerMachine, BasePacket packet) {
			if (packet instanceof SubscriptionPacket) {
				SubscriptionPacket subPacket = (SubscriptionPacket) packet;
				logger.log("Subscription: " + subPacket.getSubState());
			} else if (packet instanceof DataPacket) {
				logger.log("packet: " + packet);
			} else {
				logger.log("Unknown packet type: " + packet.getClass().getName());

			}
		}	
	
	
	
	
	//This method is utilised to determine the number of successful or failed packets when the connection is closed.
	//Since there is probability for any of this to occur, the total number of packets is also determined.
	public static void packetTransaction(boolean successful) {
		if (successful)
			successfulPackets++;

		else
			failedPackets++;

			totalPackets++;
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

	/**
	 * 
	 * 
	 * printStorageMetrics(); //handleSuccess(source, packet); if (connectionState
	 * == State.SUCCESS) logger.log("Connection finished: " + connectionState);
	 * handleSuccess(ClientMachine, packet);
	 * 
	 * }
	 **/

}
