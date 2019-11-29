package com.ljmu.andre.SimulationHelpers.Packets;

import java.util.List;

import com.ljmu.andre.SimulationHelpers.ConnectionEvent;

import hu.mta.sztaki.lpds.cloud.simulator.Timed;
import hu.mta.sztaki.lpds.cloud.simulator.iaas.PhysicalMachine;
import hu.mta.sztaki.lpds.cloud.simulator.io.Repository;

public class ServerPacket extends Timed implements ConnectionEvent {
	  private PhysicalMachine physicalMachine;
	  private ServerPacket serverData;
	

	  
	  public ServerPacket(PhysicalMachine physicalMachine, ServerPacket serverData) {

	        this.physicalMachine = physicalMachine;

	        this.serverData = serverData;

	    }

  
	  
	@Override
	public void tick(long fires) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void connectionStarted(ConnectionEvent source) {
		// TODO Auto-generated method stub
		System.out.println("ServerPacket received connection init");
	}

	
	@Override
	public void connectionFinished(ConnectionEvent source, State connectionState, BasePacket packet) {
		// TODO Auto-generated method stub
		System.out.println("ServerPacket connection finished: " + connectionState);
        long freeCap = getRepository().getFreeStorageCapacity();
        long maxCap = getRepository().getMaxStorageCapacity();
        System.out.println("ServerPacket disk: " + freeCap + "/" + maxCap);
	}

	@Override
	public Repository getRepository() {
		// TODO Auto-generated method stub
		return physicalMachine.localDisk;
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
