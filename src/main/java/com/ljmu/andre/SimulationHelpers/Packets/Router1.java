package com.ljmu.andre.SimulationHelpers.Packets;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.ljmu.andre.SimulationHelpers.ConnectionEvent;
import com.ljmu.andre.SimulationHelpers.Utils.Logger;

import hu.mta.sztaki.lpds.cloud.simulator.Timed;
import hu.mta.sztaki.lpds.cloud.simulator.iaas.PhysicalMachine;
import hu.mta.sztaki.lpds.cloud.simulator.iaas.resourcemodel.ResourceConsumption;
import hu.mta.sztaki.lpds.cloud.simulator.iaas.resourcemodel.ResourceConsumption.ConsumptionEvent;
import hu.mta.sztaki.lpds.cloud.simulator.io.Repository;



public class Router1 extends Timed implements ConsumptionEvent, ConnectionEvent{
	private static final Logger logger = new Logger(Router1.class);
	private static final int SUBSCRIBE_FREQ = 5;
	private ConnectionEvent router;
	private PhysicalMachine PhysicalMachine;
   
	Repository repository;
	String name;
	String MachineId;
	String routerId;
	//private ConnectionEvent serverMachine;
	protected ClientMachine clientMachine;
	protected  ServerMachine serverMachine;
	protected  Router2 router2;
	protected  ServerMachine3 serverMachine3;
	
	public List<ConnectionEvent> connectedDevices = new ArrayList<ConnectionEvent>();	
	Set<Integer> visited = new HashSet<Integer>();
	
	
public Router1(PhysicalMachine router1, Repository repository, String routerId) {
			this.PhysicalMachine = router1;
			name = routerId;
			this.repository = repository;
		}

		
		public void setPhysicalMachine(PhysicalMachine  router) {
			this.PhysicalMachine =  router;
		}
		
		public ConnectionEvent getRouter() {
			return  router;
		
		}
		
		@Override
		public String getId() {
			// TODO Auto-generated method stub
			return "193.168.10.1";
		}
		
		
			
		public void setMachineId(String routerId) { 
			this.routerId = "193.168.1.1";
		}
		
		
		
		public void setRepository(Repository repository) {
			this.repository= repository;
		}
		
		@Override
		public Repository getRepository() {
			// TODO Auto-generated method stub
			return PhysicalMachine.localDisk;
		}
		
		
		
		
		
	public void start() {
			logger.log("Started [Frequency: %s]", subscribe(SUBSCRIBE_FREQ));
			
		}

		// Initial connection with the ClientMachine
		@Override
		public void connectionStarted(ConnectionEvent clientMachine) {
			
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


	 public boolean connectDevice(ConnectionEvent clientMachine, ConnectionEvent serverMachine,ConnectionEvent router2,ConnectionEvent serverMachine3) {
	        boolean success = connectedDevices.add( serverMachine);
	        boolean success1 = connectedDevices.add(clientMachine);
	        boolean success2 = connectedDevices.add(router2);
	        boolean success3 = connectedDevices.add(serverMachine3);
	        if (!success) {
	            logger.log("router was already connected: " +  serverMachine.getId());
	        }
	        if(!success1) {
	        	logger.log("serverMachine was already connected: " + clientMachine.getId());
	         
	        }
	       if (!success2) {
	            logger.log("serverMachine2 was already connected: " + router2.getId());
	        }
	        if(!success3) {
	        	logger.log("serverMachine3 was already connected: " + serverMachine3.getId());
	         
	        }
	      
	        return success1;
	   }
	
	  
	/*  
	  public boolean disconnectDevice(ConnectionEvent clientMachine) {
	        boolean success = connectedDevices.remove(clientMachine);
	        if (!success)
	            logger.log("clientMachine was not connected: " + clientMachine.getId());

	        return success;
	    }
  
	 */ 
	  
	  
	  @Override
		public List<ConnectionEvent> getConnectedDevices() {
			// TODO Auto-generated method stub
		 		
		  
		  return connectedDevices;
		} 
	  
	 
	  public Set<String> getvisited() { 
			Set<String> visited = new HashSet<String>() ;
				        
				       // visited.add("serverMachine");
				        visited.add("router2");
				        visited.add("serverMachine3");
				       // visited.add("clientMachine");
				        
				        return visited;
				        
			} 
	  
	  
	 /* 
	  public static ConnectionEvent getUnvisited(ConnectionEvent clientMachine, Set<String> visited) {
	        logger.log("Checking [Node: %s] for unvisited children [Potential: %s]",
	                clientMachine.getId(),
	              clientMachine.getConnectedDevices().size());


	        // Iterate through all of the node's children \\
	        for (ConnectionEvent serverMachine : clientMachine.getConnectedDevices()) {

	            // If the Set does not contain the child, it has not been visited \\
	            if (!visited.contains(serverMachine.getId())) {
	                logger.log("Found unvisited child [ID: %s]", serverMachine.getId());
	                return serverMachine;
	            }
	        }

	        logger.log("No unvisited children");
	        return null;
	  }
	  
	 */ 
	  

	@Override
	public void connectionFinished(ConnectionEvent source, State connectionState, BasePacket packet) {
		// TODO Auto-generated method stub
		
	}

	
	

	
	@Override
	public void conComplete() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void conCancelled(ResourceConsumption problematic) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void tick(long fires) {
		// TODO Auto-generated method stub
		
	}

}
