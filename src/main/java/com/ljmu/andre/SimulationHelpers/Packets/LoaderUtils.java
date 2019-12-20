package com.ljmu.andre.SimulationHelpers.Packets;

import com.ljmu.andre.SimulationHelpers.NetworkJob;
//import static com.ljmu.andre.SimulationHelpers.Packets.NETWORK_IN_NEW_CSV;

import com.ljmu.andre.SimulationHelpers.Utils.Logger;

import hu.mta.sztaki.lpds.cloud.simulator.iaas.PhysicalMachine;

public class LoaderUtils {
	
	 private static final Logger logger = new Logger(LoaderUtils.class);
	 


	static ClientMachine getClientMachine() throws NoSuchMethodException {
     PhysicalMachine clientMachine = MachineHandler_Socket.claimPM("ClientMachine");

     if (clientMachine == null)

         throw new NullPointerException("ClientMachine not found");

    // TraceFileReader traceFileReader = new TraceFileReader(

     /// "NetworkIN",

     // NETWORK_IN_CSV,

    // 0, Integer.MAX_VALUE, true,

     // NetworkJob.class);
    
     
             
     
     return new ClientMachine(clientMachine, null, 0, null, null, null);
     
     
     
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



     return new ServerMachine(server, 0, null, null, null);

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
