package com.ljmu.andre.SimulationHelpers.Packets;

import com.ljmu.andre.SimulationHelpers.NetworkJob;
//import static com.ljmu.andre.SimulationHelpers.Packets.NETWORK_IN_NEW_CSV;

import com.ljmu.andre.SimulationHelpers.Utils.Logger;

import hu.mta.sztaki.lpds.cloud.simulator.helpers.trace.file.TraceFileReaderFoundation;
import hu.mta.sztaki.lpds.cloud.simulator.iaas.PhysicalMachine;
import hu.mta.sztaki.lpds.cloud.simulator.io.Repository;

public class LoaderUtils {
	
	 private static final Logger logger = new Logger(LoaderUtils.class);
	//private static final String Address = null;
	//private static final int Port = 0;
	//private static DataPacket datapacket;
	private static BasePacket packet;
	static PhysicalMachine ClientMachine;
	static PhysicalMachine ServerMachine;
    static Repository repository;
	static String Id;
	private Repository localDisk;

	
	
	static ServerMachine getServerMachine() throws NoSuchMethodException {

	     PhysicalMachine ServerMachine = MachineHandler_Socket.claimPM("193.6.5.222");

	     if (ServerMachine == null)

	         throw new NullPointerException("ServerMachine not found");


	     	//DataPacket NewdataPacket= datapacket;
	    // FitbitTraceFileReader traceFileReader = new FitbitTraceFileReader(

	             
		return new ServerMachine(ServerMachine, repository, Id);
		
	 }	
	
	

	static ClientMachine getClientMachine() throws NoSuchMethodException {
     PhysicalMachine ClientMachine = MachineHandler_Socket.claimPM("193.6.5.176");

     if (ClientMachine == null)

         throw new NullPointerException("ClientMachine not found");
     
    // DataPacket NewdataPacket= datapacket;

   // TraceFileReaderFoundation traceFileReader = new TraceFileReader(

                  
      
	
	return new ClientMachine(ClientMachine,repository,Id);
     
	//public ClientMachine(PhysicalMachine ServerMachine, BasePacket packet,
		//	Repository repository, String Id) {
 
}


	 static Cloud getCloud() throws NoSuchMethodException {

	    /// FitbitTraceFileReader traceFileReader = new FitbitTraceFileReader(

	            // "NetworkIN",

	             //NETWORK_IN_CSV,

	            // 0, Integer.MAX_VALUE, true,

	            // NetworkJob.class);

	     return new Cloud(MachineHandler_Socket.claimPM("Cloud"));

	// }

 }
 }




