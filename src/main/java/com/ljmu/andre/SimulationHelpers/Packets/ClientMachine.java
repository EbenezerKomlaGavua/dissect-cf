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
	PhysicalMachine client;
	private String Address;
	private int Port;
	 private String id;
	private ServerMachine server;
	private List<String> failedPacketIds = new ArrayList<String>();
	private BasePacket packet;
	private Repository repository;
	
	private List<Job> jobList;
	private int jobNumber = 0;
	
	private SimulationFileReader simulationFileReader;
	 /**
     * Initiate a new Device and claim a PhysicalMachine for it
     *
     * @param id            - The ID of the Device
  */
	public ClientMachine(PhysicalMachine client,ServerMachine server,int Port,String Address,String id, Repository repository) {
		this.client= client;
		this.server= server;
		this.Address= Address;
		this.Port= Port;
		this.id = id;
		this.repository = client.localDisk;
	}
	
	
	public ClientMachine(
            PhysicalMachine client,
            SimulationFileReader simulationFileReader) {
        this.client = client;
        this.simulationFileReader = simulationFileReader;
        jobList = simulationFileReader.getAllJobs();

    }
	
	
	
	 /**
     * Subscribe this device with a frequency of {@link this#SUBSCRIBE_FREQ}
     */
    public void start() {
        logger.log("Started [ID: %s] [Success: %s]", id, subscribe(SUBSCRIBE_FREQ));
    }

    public PhysicalMachine getPhysicalMachine() {
        return client;
    }
	
	
	// Binding the client to the server to establish a connection. The client must subscribe for server to accept
	public void bindServerMachine(ServerMachine server) {
		this.server = server;
		BasePacket bindPacket= new SubscriptionPacket(true).setShouldStore(true);
		PacketHandler.sendPacket(this, server, bindPacket);
	}

	
	 
		@Override
		public void tick(long fires) {
			// TODO Auto-generated method stub
			NetworkJob currentJob = (NetworkJob) jobList.get(jobNumber);
	        PacketHandler.sendPacket(this, server, new DataPacket("Data", currentJob.getPacketSize(), false));
	        logger.log("Job: " + jobNumber + "/" + jobList.size());

	        if (++jobNumber < jobList.size()) {
	            Job nextJob = jobList.get(jobNumber);
	            long timeDiff = nextJob.getSubmittimeSecs() - currentJob.getSubmittimeSecs();
	            logger.log("TimeDiff: " + timeDiff);
	            this.updateFrequency(timeDiff);

	        } else {

	            PacketHandler.sendPacket(this, server, new SubscriptionPacket(false));

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

	public String getAddress() {
		return Address;
	}

	public void setAddress(String address) {
		Address = address;
	}

	public int getPort() {
		return Port;
	}

	public void setPort(int port) {
		Port = port;
	}


	@Override
	public void connectionStarted(ConnectionEvent source) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void connectionFinished(ConnectionEvent source, State connectionState, BasePacket packet) {
		// TODO Auto-generated method stub
		 if (connectionState == State.FAILED && source == this) {
	            failedPacketIds.add(packet.id);
	            System.out.println("Added failed packet");
	        }
	}

	@Override
	public Repository getRepository() {
		// TODO Auto-generated method stub
		return client.localDisk;
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


	 private void stop() {
	        unsubscribe();

	    }
	
	

}
