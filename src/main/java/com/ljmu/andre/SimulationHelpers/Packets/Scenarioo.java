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
	private ServerMachine servermachine;
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
		MachineHandler_Socket.init(MACHINE_SOCKET_XML_PATH);
		servermachine = LoaderUtils.getServerMachine();
		ClientMachine = LoaderUtils.getClientMachine();
		cloud = LoaderUtils.getCloud();

		ClientMachine.start();
		servermachine.start();
		cloud.start();

		ClientMachine.bindServerMachine(servermachine);
		servermachine.bindClientMachine(ClientMachine);
		cloud.bindClientMachine(ClientMachine);

		// ClientMachine.sendDataClientMachine(ClientMachine, servermachine, Packet);
		ClientMachine.sendPacket(ClientMachine, servermachine, Packet);
		servermachine.receivePacket(servermachine, ClientMachine, Packet);
		// subscribe(1000);

		// simulateUntilLastEvent();
		subscribe(1);

		Timed.simulateUntil(100000);

		// Timed.simulateUntilLastEvent();
	}

	@Override
	public void tick(long fires) {
		// TODO Auto-generated method stub

		if (!servermachine.isSubscribed() && !ClientMachine.isSubscribed()) {
			unsubscribe();
			logger.log("No more subscribers... ENDING");
			// System.exit(5);

		}
	}
}
