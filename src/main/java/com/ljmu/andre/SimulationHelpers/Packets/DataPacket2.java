package com.ljmu.andre.SimulationHelpers.Packets;

import java.util.ArrayList;

public class DataPacket2 extends BasicPacket  {
	String myid;
	 long mysize;
	 boolean vary;
	 
	public DataPacket2(String myid, long mysize, boolean vary) {
        super(myid, mysize, vary);
        this.myid=myid;
		  this.mysize= mysize;
		  this.vary= vary;
    }
	
		
	
	

}


