package com.ljmu.andre.SimulationHelpers.Packets;

import java.util.ArrayList;
import java.util.List;

import com.ljmu.andre.SimulationHelpers.ConnectionEvent;
import com.ljmu.andre.SimulationHelpers.Device;
import com.ljmu.andre.SimulationHelpers.NetworkJob;
import com.ljmu.andre.SimulationHelpers.SimulationFileReader;
import com.ljmu.andre.SimulationHelpers.Utils.Logger;

import hu.mta.sztaki.lpds.cloud.simulator.Timed;
import hu.mta.sztaki.lpds.cloud.simulator.helpers.job.Job;
import hu.mta.sztaki.lpds.cloud.simulator.iaas.PhysicalMachine;
import hu.mta.sztaki.lpds.cloud.simulator.iaas.resourcemodel.ResourceConsumption;
import hu.mta.sztaki.lpds.cloud.simulator.iaas.resourcemodel.ResourceConsumption.ConsumptionEvent;
import hu.mta.sztaki.lpds.cloud.simulator.io.Repository;
//import hu.unimiskolc.iit.distsys.ExercisesBase;

public class ClientMachine  extends Timed implements ConsumptionEvent,ConnectionEvent{
  private static final Logger logger = new Logger(ClientMachine.class);
 private static final int SUBSCRIBE_FREQ = 100;
	PhysicalMachine ClientMachine;
	//private String Address;
	///private int Port;
	 private String id;
	private ServerMachine ServerMachine;
	private List<String> failedPacketIds = new ArrayList<String>();
	private BasePacket packet;
	private Repository repository;
	private DataPacket datapacket;
	
	
	private List<Job> jobList;
	private int jobNumber = 0;
	
	//private SimulationFileReader simulationFileReader;
	 /**
     * Initiate a new Device and claim a PhysicalMachine for it
     *
     * @param id            - The ID of the Device
  */
	public ClientMachine(PhysicalMachine ClientMachine,ServerMachine ServerMachine, DataPacket datapacket, Repository repository) {
		this.ClientMachine= ClientMachine;
		this.ServerMachine= ServerMachine;
		//this.Address= Address;
		//this.Port= Port;
		//this.id = id;
		this.datapacket= datapacket;
		this.repository = ClientMachine.localDisk;
	}
	
	
//	public ClientMachine(
           // PhysicalMachine client,
           // SimulationFileReader simulationFileReader) {
        //this.client = client;
       // this.simulationFileReader = simulationFileReader;
      //  jobList = simulationFileReader.getAllJobs();

   // }
	
	
	
	 /**
     * Subscribe this device with a frequency of {@link this#SUBSCRIBE_FREQ}
     */
  //  public void start() {
       /// logger.log("Started [ID: %s] [Success: %s]", id, subscribe(SUBSCRIBE_FREQ));
   // }

    public PhysicalMachine getPhysicalMachine() {
        return ClientMachine;
    }
	
	
	// Binding the client to the server to establish a connection. The client must subscribe for server to accept
	public void bindServerMachine(ServerMachine ServerMachine) {
		this.ServerMachine = ServerMachine;
		//BasePacket bindPacket= new SubscriptionPacket(true).setShouldStore(true);
		//PacketHandler.sendPacket(this, ServerMachine, bindPacket);
		  PacketHandler.sendPacket(this, ServerMachine, new DataPacket("Data", 10,true));
	}

	
	 
		@Override
		public void tick(long fires) {
			// TODO Auto-generated method stub
			NetworkJob currentJob = (NetworkJob) jobList.get(jobNumber);
	       /// PacketHandler.sendPacket(this, ServerMachine, new DataPacket("Data", currentJob.getPacketSize(), false));
	        logger.log("Job: " + jobNumber + "/" + jobList.size());

	        if (++jobNumber < jobList.size()) {
	            Job nextJob = jobList.get(jobNumber);
	            long timeDiff = nextJob.getSubmittimeSecs() - currentJob.getSubmittimeSecs();
	            logger.log("TimeDiff: " + timeDiff);
	            this.updateFrequency(timeDiff);

	        } else {

	            PacketHandler.sendPacket(this, ServerMachine, new SubscriptionPacket(false));

	            stop();

	        }
			
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
	//}

	//public void setAddress(String address) {
		//Address = address;
	//}

	//public int getPort() {
		//return Port;
	//}

	//public void setPort(int port) {
	//	Port = port;
	//}

	private void stop() {

        logger.log("Stopped: " + unsubscribe());

    }

	@Override public void connectionStarted(ConnectionEvent source) {

        logger.log("Received connection init: " + source.getRepository().getName());

    }
	
	public void start() {

        logger.log("Started [Frequency: %s]", subscribe(0));

    }
	

	 @Override public void connectionFinished(ConnectionEvent source, State connectionState, BasePacket packet) {
	        logger.log("Connection finished: " + connectionState);
	        printStorageMetrics();
        if (connectionState == State.SUCCESS)
	            handleSuccess(source, packet);

	    }
	 
	 private void printStorageMetrics() {
	        long freeCap = getRepository().getFreeStorageCapacity();
	        long maxCap = getRepository().getMaxStorageCapacity();
	        logger.log("Disk: [%s/%s]", freeCap, maxCap);

	    }

	 private void handleSuccess(ConnectionEvent source, BasePacket packet) {
	        if (packet instanceof SubscriptionPacket) {
	            SubscriptionPacket subPacket = (SubscriptionPacket) packet;
	            logger.log("Subscription: " + subPacket.getSubState());
	        } else if (packet instanceof DataPacket) {
	            logger.log("packet: " + packet);
	        } else {
	            logger.log("Unknown packet type: " + packet.getClass().getName());

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
		return null;
	}
	  //If the connection is established, the Id of the server will be obtained
	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return getRepository().getName();
	}


	
	
	

}
