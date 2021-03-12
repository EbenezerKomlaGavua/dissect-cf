package com.ljmu.andre.SimulationHelpers.Packets;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import com.ljmu.andre.SimulationHelpers.ConnectionEvent;

import com.ljmu.andre.SimulationHelpers.ConnectionEvent.State;
import com.ljmu.andre.SimulationHelpers.Utils.Logger;

//import hu.mta.sztaki.lpds.cloud.simulator.DeferredEvent;
import hu.mta.sztaki.lpds.cloud.simulator.Timed;
import hu.mta.sztaki.lpds.cloud.simulator.iaas.PhysicalMachine;
///import com.ljmu.andre.SimulationHelpers.Packets.DataPacket2;
//import com.ljmu.andre.SimulationHelpers.Packets.RoutingPacket1;
import com.ljmu.andre.SimulationHelpers.Packets.SubscriptionPacket;

import hu.mta.sztaki.lpds.cloud.simulator.iaas.resourcemodel.ResourceConsumption;
import hu.mta.sztaki.lpds.cloud.simulator.iaas.resourcemodel.ResourceConsumption.ConsumptionEvent;
import hu.mta.sztaki.lpds.cloud.simulator.io.NetworkNode;
import hu.mta.sztaki.lpds.cloud.simulator.io.Repository;
import hu.mta.sztaki.lpds.cloud.simulator.io.NetworkNode.NetworkException;

public class ClientMachine extends Timed implements ConsumptionEvent, ConnectionEvent {
	private static final Logger logger = new Logger(ClientMachine.class);
	private static final int SUBSCRIBE_FREQ = 5;
	private static final State connectionState = null;
	protected ClientMachine ClientMachine;
	private boolean activeSubscription = false;
	
	/// private int Port;
      String Id;
	Repository repository;
	protected ServerMachine ServerMachine;
	//private List<String> failedPacketIds = new ArrayList<String>();
	private RoutingPacket packet;
	private SubscriptionPacket packett;
	ArrayList<DataPacket> PacketArray = new ArrayList< DataPacket>();
	private static Queue<ConnectionEvent> ConnectionRoute;
	private List<ConnectionEvent> connectedDevices = new ArrayList<ConnectionEvent>();
	//private List<Packets> BasePacket = new ArrayList<Packets>();
	private static int successfulPackets;
	private static int failedPackets;
	private static int totalPackets = 0;
	private int NumberOfPackets = 1;
	private int PacketsCount = 5;
	//private BasePacket BindPacket=1;
	private PhysicalMachine PhysicalMachine;
	//private RoutingPacket packets;
	private boolean shouldStore = true;
	BasePacket bindingPacket;
	//private ArrayList<DataPacket>  PacketArray = new ArrayList<DataPacket>();
	private static final int limit = 10;
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
	 * @param id 
	 * @param repository2 
	 *
	 * 
	 */

	
	// Create the constructor of the ClientMachine with the necessary attributes
	// that can transfer packet to the ServerMachine
	// The key attributes are the packet (data unit), repository (storage location)
	// and the ID
	public ClientMachine(PhysicalMachine ClientMachine,Repository repository, String Id) {
		this.PhysicalMachine = ClientMachine;
		//this.PhysicalMachine = ServerMachine;
		this.repository = repository;
		this.Id = Id;
	}

	
	public void setPhysicalMachine(PhysicalMachine ClientMachine) {
		this.PhysicalMachine = ClientMachine;
	}
	
	public PhysicalMachine getClientMachine() {
		return PhysicalMachine;
		
	}
	
	
		
	public void setId(String Id) {
		this.Id= Id;
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
	
	
	public void setRepository(Repository repository) {
		this.repository= repository;
	}
	
	
	// The repository of the clientMachine must be set up for effective transfer of
	// packets
	// This also enables establishment of connection between the two devices
	@Override
	public Repository getRepository() {
		// TODO Auto-generated method stub
		return PhysicalMachine.localDisk;
	}

	
     
		
	// The clientMachine must be started and subscribed for the simulation to
	// commence.
	// The method ensures that all the attributes are made accessible.
	// Subscribe this device with a frequency of {@link this#SUBSCRIBE_FREQ}
	public void start() {
		logger.log("Started [Frequency: %s]", subscribe(SUBSCRIBE_FREQ));
	}



	// When the ClientMachine is started, a connection must be established to ensure
	// data transfer.
	// This is ascertained by returning the Id of the target Machine.
	// If the connection is established, the Id of the server will be obtained

	/// @return True if successfully connected
	@Override
	public void connectionStarted(ConnectionEvent ServerMachine) {
		logger.log("Received connection init: " + ServerMachine.getRepository().getName());
		
		handleConnectionStarted(ServerMachine);
	}

public ConnectionEvent handleConnectionStarted(ConnectionEvent ServerMachine) {
	logger.log("Received connection init: " + ServerMachine.getRepository().getName());
	if(isSubscribed())
		
		return ServerMachine;
	else
		
		return null;
	
	}

public void stop() {
	logger.log("Stopped: " + unsubscribe());
}

	
	// Before a formal connection can be established for packets to be transferred,
	// the clientMachine must be bound to the ServerMachine
	// Bind the clientMachine to the serverMachine to establish a connection. The
	// clientMachine must send a subscription packet to the serverMachine
	// The subscription packet is transferred and stored on the serverMachine.
	//public int bindServerMachine(final ConnectionEvent ClientMachine, final ConnectionEvent ServerMachine,
			//final BasePacket  packet) {


public ArrayList<DataPacket>  PacketArray() { 
	ArrayList<DataPacket> PacketArray = new ArrayList< DataPacket>();
	PacketArray.add(P1);
	PacketArray.add(P2);
	PacketArray.add(P3);
	return  PacketArray;
	}

DataPacket P1 = new  DataPacket("one", 3, true);
DataPacket P2 = new  DataPacket("two", 4, true);
DataPacket P3 = new  DataPacket("three", 5, true);

RoutingPacket Packet = new RoutingPacket("new packet", P1, ClientMachine, null);



/*
class RoutePacket{
	 private BasePacket payload;
	    private Queue<ConnectionEvent> route;
	    private ConnectionEvent ClientMachine;
		private Boolean ShouldStore;
	    
	    RoutePacket(String myid, BasePacket payload, ConnectionEvent ClientMachine, Queue<ConnectionEvent> route, Boolean ShouldStore) {
	        //super(myid, payload.size, false);
	        this.payload = payload;
	        this.ClientMachine = ClientMachine;
	        this.route = route;
	        this.ShouldStore= ShouldStore;
	        
	       // setShouldStore(false);
	    }

	    public void setShouldStore(boolean ShouldStore) {
		
	    	this.ShouldStore= ShouldStore;
	    	
	    	
			
		}

		public void setPayload(BasePacket payload) {
			this.payload= payload;
			
		}
	    
	    public BasePacket getPayload() {
	        return payload;
	    }

	    
	    public void setSource(ConnectionEvent ClientMachine) {
	    	this.ClientMachine= ClientMachine;
	    }
	    
	    public ConnectionEvent getSource() {
	        return ClientMachine;
	    }

	   public void setRoute(Queue<ConnectionEvent> route) {
		   this.route = route;
		   
	   }
	    
	    
	    public Queue<ConnectionEvent> getRoute() {
	        return route;
	    }
	    
	
}
*/





public ArrayList<DataPacket> sendPacketArray (final ConnectionEvent ClientMachine, final ConnectionEvent ServerMachine,
		final ArrayList<DataPacket> PacketArray){
	
	 logger.log("Attempting to send: " +  PacketArray.size() + " packets");

// ArrayList<BasePacket> packetList = new ArrayList<BasePacket>();

for (BasePacket packet :  PacketArray) {
logger.log("Sending packet: " + packet.id);

}

if (  PacketArray.size() > 0)
logger.log(" packetList: " +   PacketArray.size());

return   PacketArray;
}



public  boolean sendPacket(final ConnectionEvent ClientMachine, final ConnectionEvent ServerMachine, BasePacket P1) {
    try {
        logger.log("Sending [From: %s][To: %s]", ClientMachine.getId(), ServerMachine.getId());

        
/*
        // If the packet is a RoutingPacket and the route is empty, unbox the payload \\
        if (packet instanceof RoutingPacket &&
                ((RoutingPacket) packet).getRoute().isEmpty()) {
            logger.log("Route is empty, assuming destination achieved... Unboxing");
            packet = ((RoutingPacket) packet).getPayload();
        }
*/
        // Signal the target that an incoming connection is being made \\
        ServerMachine.connectionStarted(ClientMachine);

        // Build an appropriate consumption event \\
        ConsumptionEvent consumptionEvent = getConsumptionEvent(ClientMachine, ServerMachine, P1);

        // Determine whether the packet should be saved to disk \\
        if (P1.getShouldStore()) {
            // Register the packet to the source \\
            if(!registerPacketIfNotExist(ClientMachine, P1)) {
                logger.err("No more drive space left on [Device: %s]", ClientMachine.getId());

                ServerMachine.connectionFinished(ClientMachine, State.FAILED,P1);
                return false;
            }

            // Attempt to send and save the packet on the target \\

            boolean hasDelivered = ClientMachine.getRepository()
                    .requestContentDelivery(
                    		P1.id,
                            ServerMachine.getRepository(),
                            consumptionEvent) != null;

            if(!hasDelivered) {
            	ServerMachine.connectionFinished(ClientMachine, State.FAILED, P1);
                logger.err("Could not deliver packet [Source: %s] [Target: %s]",
                		ClientMachine.getId(), ServerMachine.getId());
            }

            return hasDelivered;
            /*
        } else {
            // Attempt to transfer the packet accross the network \\
            NetworkNode.initTransfer(
                    packet.size,
                    limit,
                    ClientMachine.getRepository(),
                    ServerMachine.getRepository(),
                    consumptionEvent);

            // If the initTransfer passed, a connection was able to be established \\
            return true;
        }
        */
            
        }
    } catch (NetworkException e) {
        logger.err("Failed to send packet [ID: %s]",P1.id);
        e.printStackTrace();

        // Check if the packet was registered before we tried to send it
        boolean packetIsRegistered = ClientMachine.getRepository().lookup(P1.id) != null;

        // If it was not already registered, we can assume it was not intended to stay
        // Register the object so that we can grab it to send again later
        // Force the packet to deregister from the source once successfully transferred
        if (!packetIsRegistered &&
        		ClientMachine.getRepository().registerObject(P1))
        	P1.addDeregisterObject(ClientMachine);

    }

    // Alert the source that the packet failed
    ClientMachine.connectionFinished(ClientMachine, State.FAILED, P1);
    return false;
}


public String bindServerMachine(final ConnectionEvent ServerMachine) {
		
		
		// Create a bindPacket Object DataPacket for binding the ClientMachine to the
		// ServerMachine, the Object must be stored after transfer.
			
		//BasePacket bindPacket = new DataPacket("Data", 1, true).setShouldStore(true);
			BasePacket bindingPacket = new SubscriptionPacket(true).setShouldStore(true);
	               
		
		// Execute the packet transfer from the ClientMachine to the ServerMachine
		//PacketHandler.sendPacket(this, ServerMachine, bindPacket);
	    PacketHandler.sendPacket(this, ServerMachine, bindingPacket);
	    logger.log("Attempting to Subscribe ClientMachine to ServerMachine with the: " +   " bindingPacket");
		System.out.println("Binding successful with :  " +    bindingPacket.id );
		System.out.println(bindingPacket.id);
		return bindingPacket.id ;
		

	}

//Used to check if the packet has been delivered
		public static boolean registerPacketIfNotExist(ConnectionEvent ServerMachine, BasePacket  P1) {
	        if (ServerMachine.getRepository().lookup(P1.id) == null) {
	            logger.log("Registering packet: " +  P1);
	            return ServerMachine.getRepository().registerObject(P1);
	        }

	        return true;
	    }





// In order to ensure that connected devices utilises the appropriate system
	// resources, the consumptionEvent method is called.
	// This ensures the systems resources are availed before packet transfer
	// commences.
	public static ConsumptionEvent getConsumptionEvent(final ConnectionEvent  ClientMachine,
			final ConnectionEvent ServerMachine, final BasePacket packet) {
		return ConsumptionEvent(ClientMachine,ServerMachine,packet);
	}

	private static ConsumptionEvent ConsumptionEvent(ConnectionEvent ClientMachine, ConnectionEvent ServerMachine,
			BasePacket packet) {
		// TODO Auto-generated method stub
		return null;
	}

	// when the clietMachine is bound to the ServerMachine, the connection between
	// the two devices is established.
	// However, in order to utilise a routing packet, the connectionRoute must be
	// determined,
	// This method ensures that the connectionRoute is obtained.
	public static Queue<ConnectionEvent> getConnectionRoute(PhysicalMachine ClientMachine, String ServerMachineId) {

		return ConnectionRoute;
	}

	// After the clientMachine is bound to the serverMachine, packets can now be
	// transferred.
	// This can be achieved by utilising the connection created between the
	// clientMachine and the serverMachine.
	// A data packet or routing packet is transferred from the clientMachine.
	// Once the packet is successfully transferred, the ServerMachine must produce a
	// notification of having received the data packet. The notification should
	// display the packet size and the time of arrival. The absence of the
	// of this notification should alert the ClientMachine that the data packet was
	// not successfully transferred.
	
	/*
	public int sendPacket (final ConnectionEvent ClientMachine, final ConnectionEvent ServerMachine,
			final BasePacket  packet) {
		 ConsumptionEvent consumptionEvent = getConsumptionEvent(ClientMachine,
		 ServerMachine, packet);
		Scenarioo.logMessage(hashCode() + " Server is not responding, let's wait!");
		// Create a sendPacket object DataPacket to transfer and store a data unit from
		// the ClientMachine to the ServerMachine.
		BasePacket sendPacket = new DataPacket("Data", 10, true);
		PacketHandler.sendPacket(ClientMachine, ServerMachine, sendPacket);
		System.out.println("Packet Transfer");
		return NumberOfPackets;
	}
   */
	// After the ClientMachine is bind to the ServerMachine,
	// the packets must be transferred and monitored.
	/// This is ensured by the tick method providing sufficient time
	/// and consumption of resources to transfer the packets.
	// After the transfer is completed, the simulation is stopped and the connection
	// closes.
	@Override
	public void tick(long fires) {
		// TODO Auto-generated method stub

		while (NumberOfPackets < PacketsCount) {
			ConsumptionEvent consumptionEvent = getConsumptionEvent(ClientMachine, ServerMachine, packet);
			PacketHandler.sendPacket(this, ServerMachine, new DataPacket("Data", 10, true));
			// Displays the number of Packets transferred from the ClientMachine to the
			// ServerMachine.
			logger.log("Packet: " + NumberOfPackets);
			NumberOfPackets++;

		}
		// After the packets are transferred from the clientMachine to ServerMachine,
		// the connection must closed
		// but not before the transfer is checked to ascertain the success of the
		// transfer. This is certified by the connectionState.
		connectionFinished(ServerMachine, connectionState, packet);
		handleSuccess(ServerMachine, packet);
		printStorageMetrics();
		stop();
	}

	// When the packets have been successful transferred from ClientMachine to
	// ServerMachine.
	// The simulation must be stopped, thus after the connection is closed and the
	// serverMachine has confirmed receipt of the packets.
	// The ClientMachine can now unsubscribe from the frequency.
	

	// Successful transfer of packets calls for the execution of this method.
	// This method states the Id of the packets transferred and the returns the path
	// utilised for the transfer.
	@Override
	public void conComplete() {
		// TODO Auto-generated method stub
		logger.log("Packet[" + packet.id + "] successfully sent");
		//getConnectionRoute(ClientMachine, "Id");
	}

	// When the transfers of data is incomplete, this method is called to notify the
	// clientMachine.
	// This usually happens when the target device has issues or the frequency isn't
	// stable.
	// The error generated is printed for analysis.
	@Override
	public void conCancelled(ResourceConsumption problematic) {
		// TODO Auto-generated method stub
		/// ClientMachine.connectionFinished(ClientMachine, State.FAILED, packet);
		logger.log("Cancelled: " + problematic.toString());
	}

	// The devices to be used for the experiment (ClientMachine and ServerMachine)
	// must be availed as a connectionEvent.
	// This ensures that they can transfer packets and receive them as well.
	//@Override
	public List<ConnectionEvent> getConnectedDevices() {
		// TODO Auto-generated method stub
		return connectedDevices;
	}

	// When the packets are transferred from the clientMachine to the ServerMachine,
	// this method is called to close up the connection.
	// Its main work is to assess if the packets were accurately delivered with the
	// contents intact.
	// close connection and print storage metrics
		
	
	/*
	@Override
		public void connectionFinished(ConnectionEvent ClientMachine, State connectionState, BasePacket packet) {
			// TODO Auto-generated method stub

			// Check if the packet is a BasePacket and if the connection has failed \\
			if (packet instanceof RoutingPacket && connectionState != State.FAILED) {
				RoutingPacket packets= (RoutingPacket) packet;

				// Get the Device this packet should move to next and remove it from the queue
				// \\
				//ConnectionEvent target = routingPacket.getRoute().poll();

				// If the target is Null or the TargetID is that of this Device \\
				// Unbox the Payload and recurse this method again with the payload \\
				if (ServerMachine == null || ServerMachine.getId().equals(this.getId())) {
					logger.log("Found Destination [Null? %s]", ServerMachine == null);
					//this.connectionFinished(ServerMachine.getSource(), connectionState, RoutingPacket.getPayload());// Work on this
					 } else {
					// Otherwise, forward the packet to the next target \\
					 logger.log("Forwarding!");
					 PacketHandler.sendPacket(this, ServerMachine, packets);
				}
				// } else {
				packetTransaction(connectionState == State.SUCCESS);

				// If the packet is not a RoutingPacket or the connection FAILED \\
				// Signal the outer class that a full connection cycle has finished \\
				connectionFinished(ClientMachine, connectionState, packet);
				logger.log("Connection finished: " + connectionState);
				printStorageMetrics();
				registerPacketIfNotExist(ServerMachine, packet);

			}
		}

		*/
	
	@Override
	public void connectionFinished(ConnectionEvent ServerMachine, State connectionState, BasePacket P1) {
		// TODO Auto-generated method stub
		if (connectionState == State.FAILED)
            getRepository().deregisterObject(packet.id);

        System.out.println("ClientMachine connection finished: " + connectionState);
        printStorageMetrics();

        if (connectionState == State.SUCCESS)
            handleSuccess(ServerMachine, P1);
    }

	 public BasePacket handleSuccess(ConnectionEvent ServerMachine,BasePacket BindingPacket) {
	        if (BindingPacket instanceof SubscriptionPacket) {
	            System.out.println("packet Stored after transfer");
	            getRepository().deregisterObject(BindingPacket.id);
	            return BindingPacket;
	        }
			return BindingPacket;
	 }
		
				
		// When the data transfer is completed.
	// The connection is closed.
	// and storage metrics are printed out.
	// This determines the quantity of storage capacity utilised for data storage.
	// It ascertains the presence of packets within the storage device.
	private void printStorageMetrics() {
		long freeCap = getRepository().getFreeStorageCapacity();
		long maxCap = getRepository().getMaxStorageCapacity();
		logger.log("Disk: [%s/%s]", freeCap, maxCap);

	}

	// When the packet is transferred from the source to the target, the
	// connectionFinished Method is called to close the connection.
	// After that, the target(ServerMachine) must be checked to determine the type
	// of packet transferred. That is the essence of this method.
	// Since either dataPacket or subscriptionPacket can be employed, the actual
	// type stored must be ascertained.
	/*
	private void handleSuccess(ConnectionEvent ServerMachine, BasePacket packet) {
		if (packet instanceof SubscriptionPacket) {
			SubscriptionPacket  packett = (SubscriptionPacket) packet;
			logger.log("Subscription: " +  packett.getSubState());
		} else if (packet instanceof DataPacket) {
			logger.log("packet: " + packet);
			 } else {
			 logger.log("Unknown packet type: " + packet.getClass().getName());

		}
	}
	*/

	// When the transfer of data packets is completed, the number of packets
	// transferred successfully or failed must be determined.
	// Since there is probability for any of this success or failure to occur, the
	// total number of packets must also determined.
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
