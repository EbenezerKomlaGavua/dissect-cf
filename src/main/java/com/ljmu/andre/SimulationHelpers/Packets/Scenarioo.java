package com.ljmu.andre.SimulationHelpers.Packets;

import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Calendar;

import com.ljmu.andre.SimulationHelpers.Utils.Logger;

import hu.mta.sztaki.lpds.cloud.simulator.Timed;
import hu.mta.sztaki.lpds.cloud.simulator.iaas.PhysicalMachine;

public class Scenarioo extends Timed {
	private static final Logger logger = new Logger(Scenarioo.class);
	private static final String USER_DIR = System.getProperty("user.dir");
	// public static final String NETWORK_IN_CSV = USER_DIR + "/network_in_new.csv";
	private static final String MACHINE_SOCKET_XML_PATH = USER_DIR + "/Machine_Socket1.xml";
	private BasePacket packet;
	private  ClientMachine ClientMachine;
	private ServerMachine ServerMachine;
	private Cloud cloud;
	private SubscriptionPacket packett;
	
	ArrayList<DataPacket> PacketArray = new ArrayList< DataPacket>();
	// The gap between packet transfer
	/// public static int timeIncrement = 5;
	public static int timeIncrement = 100;
	//DataPacket P1 = new  DataPacket("one", 3, true);
	
		
	public static void logMessage(String message) {
		System.out.println("@ T+" + Timed.getFireCount() + "ms " + message);
	}
	
	public ArrayList<DataPacket>  PacketArray() { 
		ArrayList<DataPacket> PacketArray = new ArrayList< DataPacket>();
		PacketArray.add(P1);
		PacketArray.add(P2);
		PacketArray.add(P3);
		PacketArray.add(P4);
		return  PacketArray;
		}

	DataPacket P1 = new  DataPacket("one",32,true);
	DataPacket P2 = new  DataPacket("two",32,true);
	DataPacket P3 = new  DataPacket("three",32,true);
	DataPacket P4 = new  DataPacket("four",32,true);


	
	
	
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
		//long beforeSimu1 = Calendar.getInstance().getTimeInMillis();
		//ClientMachine.bindServerMachine(ServerMachine);
		///long afterSimu = Calendar.getInstance().getTimeInMillis();
		//long duration = afterSimu - beforeSimu1;
		//System.out.println("Simulation terminated " + afterSimu + " (took " + duration + "ms in realtime)");
		
		
		//Timed.getFireCount();
		//ServerMachine.bindClientMachine(ClientMachine);
		
		
		
		
		// Send packets from ClientMachine to ServerMachine
		//ArrayList<DataPacket> PacketArray = ClientMachine.PacketArray();
		//ClientMachine.sendPackets(ClientMachine, ServerMachine, PacketArray);
		long StartTime = Calendar.getInstance().getTimeInMillis();
		//System.out.println("This simulation began at " + StartTime + "ms in realtime)");
		// ClientMachine.sendPacket(ClientMachine, ServerMachine, P1);
		ArrayList<DataPacket> PacketArray = ClientMachine.PacketArray();
		ClientMachine.sendPackets(ClientMachine, ServerMachine, PacketArray);
		 long FinishTime = Calendar.getInstance().getTimeInMillis();
	  //  System.out.println("This simulation ended at " + FinishTime + "ms in realtime)");
		//long duration = StartTime - FinishTime;
		//System.out.println("Data Transfer took " + duration + "ms in realtime)");
			
		
		//Receive packet from the ServerMachine
	
		 subscribe(1000);
		ClientMachine.stop();
		 ServerMachine.stop();
		 cloud.stop();
		//Timed.getFireCount();
		Timed.simulateUntilLastEvent();
		//Timed.simulateUntil(100);
		///System.out.println("This simulation run for " +  Timed.getFireCount() + "ms in realtime)");
		
		 // Subscribe and simulate
		subscribe(1);

		
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
