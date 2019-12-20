package com.ljmu.andre.SimulationHelpers.Packets;

import com.ljmu.andre.SimulationHelpers.Utils.Logger;

import hu.mta.sztaki.lpds.cloud.simulator.Timed;

public class Scenarioo extends Timed {
	private static final Logger logger = new Logger(Scenarioo.class);
	 private static final String USER_DIR = System.getProperty("user.dir");
	 public static final String NETWORK_IN_CSV = USER_DIR + "/network_in_new.csv";
	private static final String MACHINE_SOCKET_XML_PATH = USER_DIR + "/Machine_Socket.xml";
	private ClientMachine clientmachine;
    private ServerMachine servermachine;
	
	
	// The gap between packet transfer
		public static int timeIncrement = 5;

		
		   Scenarioo() throws Exception {
			   subscribe(1);
			   logger.log("Starting Scenario");

		        MachineHandler_Socket.init( MACHINE_SOCKET_XML_PATH);
		        servermachine = LoaderUtils.getServerMachine();
		        clientmachine = LoaderUtils.getClientMachine();
		        clientmachine.start();
		        servermachine.start();
		        clientmachine.bindServerMachine(servermachine);
		       
		        Timed.simulateUntil(2);
}
		
		

		@Override
		public void tick(long fires) {
			// TODO Auto-generated method stub
			  if (! servermachine.isSubscribed() && !clientmachine.isSubscribed()) {
		            unsubscribe();
		            logger.log("No more subscribers... ENDING");

		        }

		        try {

		            Thread.sleep(1);

		        } catch (InterruptedException e) {

		            e.printStackTrace();

		        }
		}
}
