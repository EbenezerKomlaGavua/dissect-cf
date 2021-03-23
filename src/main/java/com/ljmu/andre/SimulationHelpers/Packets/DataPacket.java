package com.ljmu.andre.SimulationHelpers.Packets;

/**
 * Created by Andre on 29/03/2017.
 */
public class DataPacket extends BasePacket {
   private String myid;
   private long l;
   private boolean vary;

// public Object sendList;
	//private final long l = 32;

	public DataPacket(String myid, long l, boolean vary) {
        super(myid, l, vary);
         //this.myid=myid;
         //this.l=l;
         //this.vary= vary;
    }
	/*
	public void setMyid(String myid) {
		this.myid=myid;
	}
	
	public String getId() {
		return myid;
	}
	
	public void setL(long l) {
		this.l=l;
	}
	public long getI() {
		return l;
	}
	*/
}
