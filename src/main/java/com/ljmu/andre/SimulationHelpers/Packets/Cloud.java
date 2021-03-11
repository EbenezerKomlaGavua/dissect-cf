package com.ljmu.andre.SimulationHelpers.Packets;

import java.util.List;

import com.ljmu.andre.SimulationHelpers.ConnectionEvent;
import com.ljmu.andre.SimulationHelpers.NetworkJob;
import com.ljmu.andre.SimulationHelpers.Utils.Logger;

import hu.mta.sztaki.lpds.cloud.simulator.Timed;
import hu.mta.sztaki.lpds.cloud.simulator.helpers.job.Job;
import hu.mta.sztaki.lpds.cloud.simulator.iaas.PhysicalMachine;
import hu.mta.sztaki.lpds.cloud.simulator.iaas.resourcemodel.ResourceConsumption;
import hu.mta.sztaki.lpds.cloud.simulator.iaas.resourcemodel.ResourceConsumption.ConsumptionEvent;
import hu.mta.sztaki.lpds.cloud.simulator.io.Repository;

public class Cloud extends Timed implements ConsumptionEvent, ConnectionEvent {
	private static final Logger logger = new Logger(Cloud.class);
	
	protected Cloud cloud;
	// private PhysicalMachine CloudMachine;

	private PhysicalMachine PhysicalMachine;
    String Id;
	private Repository repository;

	private Repository localdisk;

	 
	 
	// private List<Job> jobList;
	  ///  private int jobNumber = 0;
	 	 
	   
	   public Cloud(PhysicalMachine claimPM) {
			// TODO Auto-generated constructor stub
		}

		public Cloud(PhysicalMachine cloud,Repository repository, String Id) {
			this.PhysicalMachine = cloud;
			//this.PhysicalMachine = ServerMachine;
			this.repository = repository;
			this.Id = Id;
		}

		
		public void setPhysicalMachine(PhysicalMachine cloud) {
			this.PhysicalMachine = cloud;
		}
		
		public PhysicalMachine getCloudMachine() {
			return PhysicalMachine;
			
		}
		
					
		public void setId(String Id) {
			this.Id= Id;
		}
		
		@Override
		public String getId() {
			// TODO Auto-generated method stub
			return  Id;
		}
	   
	   
		
		
		public void setRepository(Repository repository) {
			this.repository= repository;
		}  
	   
	   		
		@Override
		public Repository getRepository() {
			// TODO Auto-generated method stub
			//return CloudMachine.localDisk;
			return repository;
			
		}

		
		
		
		@Override
		public List<ConnectionEvent> getConnectedDevices() {
			// TODO Auto-generated method stub
			return null;
		}

		
	   
	   
	   
	   
	   
	   
	   
	   
	   
	   
	   
//Bind the cloud to the clientMachine
	  // public void bindClientMachine(ClientMachine ClientMachine) {
	      //  this.ClientMachine = ClientMachine;

	 ///   } 
	    
	    @Override
		public void tick(long fires) {
			// TODO Auto-generated method stub
	    //	NetworkJob currentJob = (NetworkJob) jobList.get(jobNumber);
	   //  PacketHandler.sendPacket(this, ClientMachine, new DataPacket("Data", currentJob.getPacketSize(), false));
	    	
	    // logger.log("Job: " + jobNumber + "/" + jobList.size());
/**
	       if (++jobNumber < jobList.size()) {
	            Job nextJob = jobList.get(jobNumber);
	            long timeDiff = nextJob.getSubmittimeSecs() - currentJob.getSubmittimeSecs();
	            logger.log("TimeDiff: " + timeDiff);
	            this.updateFrequency(timeDiff);
	        } else {
	            PacketHandler.sendPacket(this, ClientMachine, new SubscriptionPacket(false));
	            stop();
	        }
	    	    }
	 **/
	    }
	    
	    
	    // Start and and subscribe the cloud
	    public void start() {
	        logger.log("Started [Frequency: %s]", subscribe(0));
	    }
	    
	 private void stop() {
	        logger.log("Stopped: " + unsubscribe());
	    }


	   
	@Override
	public void connectionStarted(ConnectionEvent source) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void connectionFinished(ConnectionEvent source, State connectionState, BasePacket packet) {
		// TODO Auto-generated method stub
		
	}
      
	// Return the repository of the cloud
	

	@Override
	public void conComplete() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void conCancelled(ResourceConsumption problematic) {
		// TODO Auto-generated method stub
		
	}

	

}
