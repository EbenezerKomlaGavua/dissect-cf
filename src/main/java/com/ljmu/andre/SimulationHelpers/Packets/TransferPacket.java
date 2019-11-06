package com.ljmu.andre.SimulationHelpers.Packets;

import java.util.List;

import com.ljmu.andre.SimulationHelpers.ConnectionEvent;

import hu.mta.sztaki.lpds.cloud.simulator.Timed;
import hu.mta.sztaki.lpds.cloud.simulator.io.Repository;

public class TransferPacket  extends Timed implements ConnectionEvent{

	@Override
	public void connectionStarted(ConnectionEvent source) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void connectionFinished(ConnectionEvent source, State connectionState, BasePacket packet) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Repository getRepository() {
		// TODO Auto-generated method stub
		return null;
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

	@Override
	public void tick(long fires) {
		// TODO Auto-generated method stub
		
	}

	
	
	
}
