package com.ljmu.andre.SimulationHelpers.Packets;

import java.util.Queue;

import com.ljmu.andre.SimulationHelpers.ConnectionEvent;

public class RoutingPacket1 extends BasePacket{
private BasePacket payload;
private Queue<ConnectionEvent> route;
private ConnectionEvent ClientMachine;
private Boolean shouldStore= true;


RoutingPacket1(String myid, BasePacket payload, ConnectionEvent ClientMachine, Queue<ConnectionEvent> route, Boolean shouldStore) {
    super(myid, payload.size, false);
    this.payload = payload;
    this.ClientMachine = ClientMachine;
    this.route = route;
    this.shouldStore= shouldStore;
    
   // setShouldStore(false);
}




public BasePacket setShouldStore(boolean shouldStore) {

	this.shouldStore= shouldStore;
	return this;
		
	}

public boolean getShouldStore() {
    return shouldStore;
}


public void setPayload(BasePacket payload) {
	this.payload= payload;
	
}

public BasePacket getPayload() {
    return payload;
}


public void setSource(ConnectionEvent ClientMachine) {
	this.ClientMachine= ClientMachine;
}

public ConnectionEvent getSource() {
    return ClientMachine;
}

public void setRoute(Queue<ConnectionEvent> route) {
   this.route = route;
   
}


public Queue<ConnectionEvent> getRoute() {
    return route;
}


}
