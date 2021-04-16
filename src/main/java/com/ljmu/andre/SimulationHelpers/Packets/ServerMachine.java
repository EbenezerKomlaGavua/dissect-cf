package com.ljmu.andre.SimulationHelpers.Packets;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
    //String serverMachineId;
    long maxValueInMap= 0;
    long minValueInMap=Long.MAX_VALUE;
    long average = 0;
    int counter =0;
    protected Router1 router1;
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
	public List<ConnectionEvent> connectedDevices = new ArrayList<ConnectionEvent>();
	private ConnectionEvent serverMachine;
	private PhysicalMachine PhysicalMachine;
    // final String name;
    String serverMachineId;
	
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
	public ServerMachine(PhysicalMachine serverMachine,	Repository repository, String serverMachineId) {
		this.PhysicalMachine = serverMachine;
		this.serverMachineId = serverMachineId;
		this.repository = repository;
	}

	// Get the PhysicalMachine call ServerMachine
	//public PhysicalMachine getPhysicalMachine() {
		//return ServerMachine;
	//}
	// Get the repository of the ServerMachine
		
	public void setPhysicalMachine(PhysicalMachine  serverMachine) {
		this.PhysicalMachine =  serverMachine;
	}
	
	public ConnectionEvent getServerMachine() {
		return  serverMachine;
		
	}
	
		
	public void setMachineId(String serverMachineId) {
		this.serverMachineId = "10.10.10.2";
	}
	
	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return "10.10.10.2";
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
	public void connectionStarted(ConnectionEvent clientMachine) {
		// TODO Auto-generated method stub
		//logger.log("Received connection init: " + ClientMachine.getRepository().getName());
	
	handleConnectionStarted(clientMachine);
	}
	
		
public ConnectionEvent handleConnectionStarted(ConnectionEvent clientMachine) {
	if(isSubscribed())
	return clientMachine;
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
        	
            handleSuccess(serverMachine, P1);
           // System.out.println("ClientMachine connection finished: " + connectionState);
         
           
          // System.out.println(" Packet receieved: " + P1);
            packetTransaction(connectionState == State.SUCCESS);
           // long CompleteTime = Calendar.getInstance().getTimeInMillis();
            long CompleteTime = System.currentTimeMillis();
            ///System.out.println("StopTime is " + " " + CompleteTime0 + "ms in realtime");
           // long CompleteTime = CompleteTime0 *1000;
            //System.out.println("StopTime is " + " " + CompleteTime + "ms in realtime");
           if(ClientMachine.Packetdetails.containsKey(P1.id)) {
        	 //System.out.println("StartTime is " + " " + ClientMachine.Packetdetails.get(P1.id) + "ms in realtime");
        	 //System.out.println("StopTime is " + " " + CompleteTime + "ms in realtime");
        	  
        	 long duration= CompleteTime - ClientMachine.Packetdetails.get(P1.id);
        	 if(maxValueInMap<duration) maxValueInMap=duration;
        	 if(minValueInMap>duration) minValueInMap=duration;
        	 average+=duration;
        	double AvgValue = ((double) average/ClientMachine.Packetdetails.size());
        //	 if (average > ClientMachine.Packetdetails.size(); counter++)
        		 
        		 
        	 ClientMachine.Packetdetails.replace(P1.id, duration);
        	 
        	  System.out.println("The duration is " + P1.id  + " " +  ClientMachine.Packetdetails.get(P1.id) + "탎 in realtime");
        	  System.out.println(" Reply from  193.6.5.222 : " + "bytes = 32" + " time < " +  ClientMachine.Packetdetails.get(P1.id) + "탎 TTL=64");
        	  
        	  System.out.println( "Maximum value is : "  + "" + maxValueInMap + "탎 in realtime");  
        	  System.out.println( "Minimum value is : "  + "" + minValueInMap + "탎 in realtime"); 
        	  System.out.println( "Average value is : "  + "" + AvgValue + "탎 in realtime");
        	  
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
	          //  logger.log(" Packets: Lost = " +   FailedPacketIds.size() + "(0% loss)"); //Check this out!!!
	          //  printStatistics();
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


	
	 public boolean connectDevice(ConnectionEvent clientMachine, ConnectionEvent router,ConnectionEvent serverMachine2,ConnectionEvent serverMachine3) {
	        boolean success = connectedDevices.add(  clientMachine);
	        boolean success1 = connectedDevices.add(router);
	        boolean success2 = connectedDevices.add(serverMachine2);
	        boolean success3 = connectedDevices.add(serverMachine3);
	        if (!success) {
	            logger.log("clientMachine was already connected: " +  clientMachine.getId());
	        }
	        if(!success1) {
	        	logger.log("router was already connected: " + router.getId());
	         
	        }
	       if (!success2) {
	            logger.log("serverMachine2 was already connected: " + serverMachine2.getId());
	        }
	        if(!success3) {
	        	logger.log("serverMachine3 was already connected: " + serverMachine3.getId());
	         
	        }
	      
	        return success;
	   }
	
	
	
	 public Set<String> getvisited() { 
			Set<String> visited = new HashSet<String>() ;
				        visited.add("router");
				      //visited.add("serverMachine");
				        visited.add("serverMachine2");
				        visited.add("serverMachine3");
				        visited.add("clientMachine");
				        
				        return visited;
				        
			}

	@Override
	public List<ConnectionEvent> getConnectedDevices() {
					return connectedDevices;
			
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
