package com.ljmu.andre.SimulationHelpers.Packets;

import java.io.RandomAccessFile;

import com.ljmu.andre.SimulationHelpers.Utils.Logger;

import hu.mta.sztaki.lpds.cloud.simulator.Timed;

public class Scenarioo extends Timed {
	private static final Logger logger = new Logger(Scenarioo.class);
	private static final String USER_DIR = System.getProperty("user.dir");
	// public static final String NETWORK_IN_CSV = USER_DIR + "/network_in_new.csv";
	private static final String MACHINE_SOCKET_XML_PATH = USER_DIR + "/Machine_Socket1.xml";
	private static final BasePacket Packet = null;
	private ClientMachine ClientMachine;
	private ServerMachine ServerMachine;
	private Cloud cloud;

	// The gap between packet transfer
	/// public static int timeIncrement = 5;
	public static int timeIncrement = 100;

	public static void logMessage(String message) {
		System.out.println("@ T+" + Timed.getFireCount() + "ms " + message);
	}

	Scenarioo() throws Exception {
		// subscribe(1);
		logger.log("Starting Scenario");

		RandomAccessFile raf = new RandomAccessFile(MACHINE_SOCKET_XML_PATH, "r");
		System.out.println(raf.read());
		raf.close();
		
		// 	Initialise the machines with the xml file
		MachineHandler_Socket.init(MACHINE_SOCKET_XML_PATH);
		//load the Server machine with LoaderUtils 
		ServerMachine = LoaderUtils.getServerMachine();
		//load the ClientMachine machine with LoaderUtils 
		ClientMachine = LoaderUtils.getClientMachine();
		//load the Cloud machine with LoaderUtils 
		cloud = LoaderUtils.getCloud();

		//Start the machines clientMachine, ServiceMachine and the Cloud
		ClientMachine.start();
		ServerMachine.start();
		cloud.start();
		
		//Bind ClientMachine to ServerMachine
		ClientMachine.bindServerMachine(ServerMachine);
		
		ServerMachine.bindClientMachine(ClientMachine);
		//Bind   Cloud to ClientMachine
		cloud.bindClientMachine(ClientMachine);

		
		
		// Send packet from ClientMachine to ServerMachine
		//ClientMachine.sendPacket(ClientMachine, Servermachine, Packet);
		
		//Receive packet from the ServerMachine
		ServerMachine.receivePacket(ServerMachine, ClientMachine, Packet);
		// subscribe(1000);

		// simulateUntilLastEvent();
		
		 // Subscribe and simulate
		subscribe(1);

		Timed.simulateUntil(100000);

		// Timed.simulateUntilLastEvent();
	}

	@Override
	public void tick(long fires) {
		// TODO Auto-generated method stub

		if (!ServerMachine.isSubscribed() && !ClientMachine.isSubscribed()) {
			unsubscribe();
			logger.log("No more subscribers... ENDING");
			// System.exit(5);

		}
	}
}
