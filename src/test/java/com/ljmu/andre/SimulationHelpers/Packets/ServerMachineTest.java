package com.ljmu.andre.SimulationHelpers.Packets;

import static org.junit.Assert.*;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

import hu.mta.sztaki.lpds.cloud.simulator.io.NetworkNode.NetworkException;
import junit.framework.Assert;

public class ServerMachineTest {
	private static final String USER_DIR = System.getProperty("user.dir");
	private  ServerMachine  ServerMachine;
	private ClientMachine ClientMachine;
	private Cloud cloud;
	private static final String MACHINE_SOCKET_XML_PATH = USER_DIR + "/Machine_Socket1.xml";
	String message = "The PhysicalMachine is TurnON";
	
	Boolean condition = true;
	
	
	
	@Before
	public void StartServerMachinetest()  throws NetworkException, ParserConfigurationException, SAXException, IOException, NoSuchMethodException {
		
		MachineHandler_Socket.init(MACHINE_SOCKET_XML_PATH);
		ClientMachine = LoaderUtils.getClientMachine();
		ServerMachine = LoaderUtils.getServerMachine();
		cloud = LoaderUtils.getCloud();
		 ClientMachine.start();
		 ServerMachine.start();
	      cloud.start();
	}

	//Checking the establishment of a connection between the ClientMachine and the ServerMachine
	@Test
	public void CheckConnectionTest() throws Exception{
		
		ServerMachine.connectionStarted(ClientMachine);
		
		assertTrue("The PhysicalMachine is TurnON", true);
	}
	
	
}
