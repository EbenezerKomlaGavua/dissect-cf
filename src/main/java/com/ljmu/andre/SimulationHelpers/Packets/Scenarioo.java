package com.ljmu.andre.SimulationHelpers.Packets;

import java.io.RandomAccessFile;

import com.ljmu.andre.SimulationHelpers.Utils.Logger;

import hu.mta.sztaki.lpds.cloud.simulator.Timed;

public class Scenarioo extends Timed {
	private static final Logger logger = new Logger(Scenarioo.class);
	 private static final String USER_DIR = System.getProperty("user.dir");
	 //public static final String NETWORK_IN_CSV = USER_DIR + "/network_in_new.csv";
	private static final String MACHINE_SOCKET_XML_PATH = USER_DIR + "/Machine_Socket1.xml";
	private ClientMachine ClientMachine;
    private ServerMachine servermachine;
    private Cloud cloud;
	
	// The gap between packet transfer
		public static int timeIncrement = 5;

		
		   Scenarioo() throws Exception {
			 //  subscribe(1);
			   logger.log("Starting Scenario");

			   RandomAccessFile raf=new RandomAccessFile(MACHINE_SOCKET_XML_PATH,"r");
			   System.out.println(raf.read());
			   raf.close();
		        MachineHandler_Socket.init( MACHINE_SOCKET_XML_PATH);
		        servermachine = LoaderUtils.getServerMachine();
		        ClientMachine = LoaderUtils.getClientMachine();
		        cloud = LoaderUtils.getCloud();
		        ClientMachine.start();
		        servermachine.start();
		        cloud.start();
		        
		        servermachine.bindClientMachine(ClientMachine);
		        ClientMachine.bindServerMachine(servermachine);
		        cloud.bindClientMachine(ClientMachine);
		        
		        
		        
		        subscribe(2);
		       
		       // Timed.simulateUntil(3);
		        Timed.simulateUntilLastEvent();
}
		
		
		@Override
		public void tick(long fires) {
			// TODO Auto-generated method stub
			  if (! servermachine.isSubscribed() && !ClientMachine.isSubscribed()) {
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
