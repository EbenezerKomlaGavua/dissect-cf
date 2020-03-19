package com.ljmu.andre.SimulationHelpers.Packets;

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
//import hu.unimiskolc.iit.distsys.ExercisesBase;

public class ServerMachine extends Timed  implements ConsumptionEvent, ConnectionEvent{
	 private static final Logger logger = new Logger(ServerMachine.class);
	 private static final int SUBSCRIBE_FREQ = 4;
	 private String id;
	PhysicalMachine ServerMachine;
	//private String Address;
	private ClientMachine ClientMachine;
	//private int Port;
     private static int totalPackets = 0;
	 private static int successfulPackets = 0;
	 private static int failedPackets = 0;
	private DataPacket datapacket;
	
	
	
	//Create the constructor for the serverMachine
	public ServerMachine(PhysicalMachine ServerMachine, DataPacket datapacket, ClientMachine ClientMachine) {
		this.ServerMachine= ServerMachine;
		this.datapacket = datapacket;
		//this.Address= Address;
		//this.Port= Port;
		this.ClientMachine = ClientMachine;
		 //this.id = id;
	}

	/**
     * Subscribe this device with a frequency of {@link this#SUBSCRIBE_FREQ}
     */
	
	 public void start() {
	        logger.log("Started [ID: %s] [Success: %s]", id, subscribe(SUBSCRIBE_FREQ));
	    }

	   
	 public void bindClientMachine(ClientMachine ClientMachine) {
	        this.ClientMachine = ClientMachine;

	    } 
	 
		public PhysicalMachine getPhysicalMachine() {
	        return ServerMachine;
	    }
	
	
				
	public void tick(long fires) {
		// TODO Auto-generated method stub
		logger.log("Tick: " + fires);
	}
	
	@Override
	public void connectionStarted(ConnectionEvent client) {
		// TODO Auto-generated method stub
		logger.log("Received connection init: " + client.getRepository().getName());
	}
	
	
	
	@Override
	public void conComplete() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void conCancelled(ResourceConsumption problematic) {
		// TODO Auto-generated method stub
		
	}

	//public String getAddress() {
		//return Address;
//	}

	//public void setAddress(String address) {
	//	Address = address;
//	}

//	public int getPort() {
	//	return Port;
	//}

	//public void setPort(int port) {
		//Port = port;
	//}

	 /**
     * Signal that a packet has been transferred to this Device
     * If the Packet is a RoutingPacket and is not destined for this device, it is then forwarded
     *
     * @param source          - The Device the Packet has been sent from
     * @param connectionState - The State of the connection
     * @param packet          - The Packet that was sent
     */
	
	
// close connection and print storage metrics
	@Override
	public void connectionFinished(ConnectionEvent client, State connectionState, BasePacket packet) {
		// TODO Auto-generated method stub
		
		// Check if the packet is a RoutingPacket and if the connection has failed \\
        if (packet instanceof RoutingPacket && connectionState != State.FAILED) {
            RoutingPacket routingPacket = (RoutingPacket) packet;

            // Get the Device this packet should move to next and remove it from the queue \\
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
        //} else {
                  packetTransaction(connectionState==State.SUCCESS);

            // If the packet is not a RoutingPacket or the connection FAILED \\
            // Signal the outer class that a full connection cycle has finished \\
            connectionFinished(client, connectionState, packet);
            logger.log("Connection finished: " + connectionState);
            printStorageMetrics();

        }
	}

        public static void packetTransaction(boolean successful) {
            if(successful)
                successfulPackets++;
            else
                failedPackets++;

            totalPackets++;
        }

		

//}
//}

		
		
        
      // if (connectionState == State.SUCCESS);
        //    handleSuccess(client, packet);
	//}
	
	private void printStorageMetrics() {
        long freeCap = getRepository().getFreeStorageCapacity();
        long maxCap = getRepository().getMaxStorageCapacity();
        logger.log("Disk: " + freeCap + "/" + maxCap);
	
	}
	
	
// handle received subscription  packets
	private void handleSuccess(ConnectionEvent client, BasePacket packet) {
		
	}
	
	
	@Override
	public Repository getRepository() {
		// TODO Auto-generated method stub
		return ServerMachine.localDisk;
	}


	
        
	@Override
	public List<ConnectionEvent> getConnectedDevices() {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return null;
	}

}
