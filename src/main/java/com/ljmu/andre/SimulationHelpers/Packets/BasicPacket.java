package com.ljmu.andre.SimulationHelpers.Packets;

import java.util.ArrayList;

import hu.mta.sztaki.lpds.cloud.simulator.io.StorageObject;

public class BasicPacket extends StorageObject {
	 private static long totalPackets = 0;
	    private ArrayList<Object> deregistryList = new ArrayList<Object>();
	    private boolean shouldStore = true;

	    BasicPacket(String myid) {
	        super(myid + totalPackets++);
	    }
 
	public BasicPacket(String myid, long mysize, boolean vary) {
		super(myid, mysize, vary);
		// TODO Auto-generated constructor stub
	}

}
  