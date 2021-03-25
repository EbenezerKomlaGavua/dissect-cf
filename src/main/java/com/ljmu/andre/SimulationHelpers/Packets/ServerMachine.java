package com.ljmu.andre.SimulationHelpers.Packets;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;

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
    String MachineId;
    
	//protected PhysicalMachine ServerMachine;
    static final State connectionState = State.SUCCESS;
	//protected ClientMachine ClientMachine;
	//private boolean shouldStore = true;
     static int totalPackets = 0;
	 static int successfulPackets = 0;
	 static int failedPackets = 0;
	//private static Repository localDisk;
	//private DataPacket datapacket;
	 Repository repository;
	//private BasePacket packet;
	private List<String> SuccessfulPacketIds = new ArrayList<String>();
	private List<String> FailedPacketIds = new ArrayList<String>();

	private ConnectionEvent ServerMachine;
	private PhysicalMachine PhysicalMachine;
     final String name;
	
	//private ConsumptionEvent PhysicalMachine;

	

	
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
	public ServerMachine(PhysicalMachine ServerMachine,	Repository repository, String MachineId) {
		this.PhysicalMachine = ServerMachine;
		//this.packet = packet;
		//this.PhysicalMachine = ClientMachine;
		name = MachineId;
		this.repository = repository;
	}

	// Get the PhysicalMachine call ServerMachine
	//public PhysicalMachine getPhysicalMachine() {
		//return ServerMachine;
	//}
	// Get the repository of the ServerMachine
		
	public void setPhysicalMachine(PhysicalMachine  ServerMachine) {
		this.PhysicalMachine =  ServerMachine;
	}
	
	public ConnectionEvent getServerMachine() {
		return  ServerMachine;
		
	}
	
		
	public void setMachineId(String MachineId) {
		this.MachineId = "193.6.5.222";
	}
	
	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return getRepository().getName();
	}
	
	public void setRepository(Repository repository) {
		this.repository= repository;
	}
	
	@Override
	public Repository getRepository() {
		// TODO Auto-generated method stub
		return PhysicalMachine.localDisk;
	}
	
	// Get the Id of the ServerMachine
	
	
	
	
	
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
		//logger.log("Received connection init: " + ClientMachine.getRepository().getName());
	
	handleConnectionStarted(ClientMachine);
	}
	
		
public ConnectionEvent handleConnectionStarted(ConnectionEvent ClientMachine) {
	if(isSubscribed())
	return ClientMachine;
	else
		return null;
	}
	

public void stop() {
		
	logger.log("Stopped: " + unsubscribe());
}

	


/*
	// Receive packets sent from the clientMachine
	public void receivePacket(final ConnectionEvent ClientMachine, final ConnectionEvent ServerMachine,
			BasePacket packet) {
		final long reqStart = Timed.getFireCount();
		Scenarioo.logMessage(hashCode() + " Server is not responding, let's wait!");
		// if (ServerMachine.getState().e)

	}
	*/

	public ConsumptionEvent getConsumptionEvent(final ConsumptionEvent ServerMachine,final ConnectionEvent ClientMachine,
			 final BasePacket packet) {
		return ServerMachine;
	}
	
	
	
	/*
	 * Subscribe this device with a frequency of {@link this#SUBSCRIBE_FREQ}
	 */

	public void tick(long fires) {
		// TODO Auto-generated method stub
		logger.log("Tick: " + fires);
	}

	public static ConsumptionEvent getConsumptionEvent(final ConnectionEvent ServerMachine,
            final ConnectionEvent ClientMachine,
            final BasePacket P1) {
return new ConsumptionEvent() {

@Override 
public void conComplete() {
logger.log("Packet[" + P1.id + "] successfully sent");

//source.connectionFinished(source, State.SUCCESS, packet);
ClientMachine.connectionFinished(ClientMachine, State.SUCCESS, P1);

//Packets that are not intended to stay after transfer are deregistered here
//This allows for failed packets to be wiped

if (P1.shouldDeregister(ServerMachine)) {
	ServerMachine.getRepository().deregisterObject(P1);
}
else
if (P1.shouldDeregister(ClientMachine)) {
	ClientMachine.getRepository().deregisterObject(P1);
}

}

@Override 
public void conCancelled(ResourceConsumption problematic) {
	ServerMachine.connectionFinished(ServerMachine, State.FAILED, P1);
logger.log("Cancelled: " + problematic.toString());
}
};
}
	

	
//	}

	/**
	 * Signal that a packet has been transferred to this Device If the Packet is a
	 * RoutingPacket and is not destined for this device, it is then forwarded
	 *
	 * @param source          - The Device the Packet has been sent from
	 * @param connectionState - The State of the connection
	 * @param packet          - The Packet that was sent
	 */

	 
	@Override
	public void connectionFinished(ConnectionEvent clientMachine, State connectionState, BasePacket P1) {
		// TODO Auto-generated method stub
		
		//if (packet instanceof BasePacket && connectionState != State.FAILED)
		
		if (connectionState == State.FAILED) {
            getRepository().deregisterObject(P1.id);
            FailedPacketIds.add(P1.id);

        System.out.println("ClientMachine connection finished: " + connectionState);
        printStorageMetrics();
		}
		else
        if (connectionState == State.SUCCESS) {
        	
            handleSuccess(ServerMachine, P1);
            System.out.println("ClientMachine connection finished: " + connectionState);
             printStorageMetrics();
           System.out.println(" Packet receieved: " + P1);
            packetTransaction(connectionState == State.SUCCESS);
            long CompleteTime = Calendar.getInstance().getTimeInMillis();
         
           if(ClientMachine.Packetdetails.containsKey(P1.id)) {
        	 System.out.println("StartTime is " + " " + ClientMachine.Packetdetails.get(P1.id) + "ms in realtime");
        	 System.out.println("StopTime is " + " " + CompleteTime + "ms in realtime");
        	  
        	 long duration= CompleteTime - ClientMachine.Packetdetails.get(P1.id);
        	 
        	 ClientMachine.Packetdetails.replace(P1.id, duration);
        	  System.out.println("The duraton is " + P1.id  + " " +  ClientMachine.Packetdetails.get(P1.id) + "ms in realtime)");
        	  System.out.println(" Reply from  193.6.5.222 : " + "bytes = 32" + " time < " +  ClientMachine.Packetdetails.get(P1.id) + "ms TTL=64");
        	  
        	  
        	  
        	  
         }
        	
           Long maxValueInMap=(Collections.max(ClientMachine.Packetdetails.values()));
           for(Entry<String,Long>entry : ClientMachine.Packetdetails.entrySet()){
        	   if (entry.getValue()==maxValueInMap) {
        		   System.out.println( "Maximum value is : "  + "" + entry.getKey());
        	   }
           
           }
        		  
          }
    		
        }
    

	 public boolean handleSuccess(ConnectionEvent ServerMachine,BasePacket P1) {
	        if (P1 instanceof BasePacket) {
	            //System.out.println("packet Stored after transfer");
	            getRepository().deregisterObject(P1.id);
	            SuccessfulPacketIds.add(P1.id);
	            System.out.println("Packets received" + SuccessfulPacketIds);
	            logger.log(" Packets: received = " +   SuccessfulPacketIds.size());
	            logger.log(" Packets: Lost = " +   FailedPacketIds.size() + "(0% loss)"); //Check this out!!!
	            return true;
	           
	        }
	        else
			return false;
	 }
	 
	 private void printStorageMetrics() {
			long freeCap = getRepository().getFreeStorageCapacity();
			long maxCap = getRepository().getMaxStorageCapacity();
			//logger.log("Disk: " + freeCap + "/" + maxCap);

		}
 
	 
	 
	 
		
		
/*
	When the transfer of data packets is completed, the number of packets
	transferred successfully or failed must be determined.
	Since there is probability for any of this success or failure to occur, the
	total number of packets must also determined.
		
	*/
	
	public static void packetTransaction(boolean successful) {
		if (successful) {
			successfulPackets++;
			// System.out.println("successful  " + successfulPackets++);
		}
		else
		{
			failedPackets++;
			//System.out.println("failed packets  " + failedPackets++);
		}

		totalPackets++;
		//System.out.println(" Total packets  " + totalPackets++);
	}


	

	@Override
	public List<ConnectionEvent> getConnectedDevices() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void conComplete() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void conCancelled(ResourceConsumption problematic) {
		// TODO Auto-generated method stub
		
	}

	
	

	

}
