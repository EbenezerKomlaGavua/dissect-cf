package com.ljmu.andre.SimulationHelpers.Packets;

import com.ljmu.andre.SimulationHelpers.NetworkJob;
//import static com.ljmu.andre.SimulationHelpers.Packets.NETWORK_IN_NEW_CSV;

import com.ljmu.andre.SimulationHelpers.Utils.Logger;

import hu.mta.sztaki.lpds.cloud.simulator.helpers.trace.file.TraceFileReaderFoundation;
import hu.mta.sztaki.lpds.cloud.simulator.iaas.PhysicalMachine;
import hu.mta.sztaki.lpds.cloud.simulator.io.Repository;

public class LoaderUtils {
	
	 private static final Logger logger = new Logger(LoaderUtils.class);
	private static final String Address = null;
	private static final int Port = 0;
	 


	static ClientMachine getClientMachine() throws NoSuchMethodException {
     PhysicalMachine client = MachineHandler_Socket.claimPM("ClientMachine");

     if (client == null)

         throw new NullPointerException("ClientMachine not found");

   // TraceFileReaderFoundation traceFileReader = new TraceFileReader(

     // "NetworkIN",

    //  NETWORK_IN_CSV,

    // 0, Integer.MAX_VALUE, true,

    //  NetworkJob.class);
    
     
             
     
     ServerMachine server = null;
	int port = 0;
	String id = null;
	Repository repository = null;
	return new ClientMachine(client, server, port, Address, id, repository);
     
     
     
    // FitbitTraceFileReader traceFileReader = new FitbitTraceFileReader(

             //"BluetoothIN",

            // BLUETOOTH_IN_CSV,

            // 0, Integer.MAX_VALUE, true,

            // NetworkJob.class);


 


}
//}


 static ServerMachine getServerMachine() throws NoSuchMethodException {

     PhysicalMachine server = MachineHandler_Socket.claimPM("Server");

     if (server == null)

         throw new NullPointerException("ServerMachine not found");



    // FitbitTraceFileReader traceFileReader = new FitbitTraceFileReader(

             //"NetworkOUT",

            // NETWORK_OUT_CSV,

            // 0, Integer.MAX_VALUE, true,

            // NetworkJob.class);



     String id = null;
	ClientMachine client = null;
	return new ServerMachine(server,  Port, Address, client, id);
	
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



// static Cloud getCloud() throws NoSuchMethodException {

    /// FitbitTraceFileReader traceFileReader = new FitbitTraceFileReader(

            // "NetworkIN",

             //NETWORK_IN_CSV,

            // 0, Integer.MAX_VALUE, true,

            // NetworkJob.class);

     //return new Cloud(MachineHandler.claimPM("Cloud"), traceFileReader);

// }

//}
