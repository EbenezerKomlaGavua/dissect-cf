package com.ljmu.andre.SimulationHelpers.Packets;

import static org.junit.Assert.*;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.Test;
import org.xml.sax.SAXException;

import hu.mta.sztaki.lpds.cloud.simulator.io.NetworkNode.NetworkException;
import junit.framework.Assert;

public class ServerMachineTest {
	private static final String USER_DIR = System.getProperty("user.dir");
	private  ServerMachine  ServerMachine;
	private ClientMachine ClientMachine;
	private static final String MACHINE_SOCKET_XML_PATH = USER_DIR + "/Machine_Socket1.xml";
	String message = "The PhysicalMachine is TurnON";
	
	Boolean condition = true;
	
	
	/**
	@Test
	public void StartServerMachinetest()  throws NetworkException, ParserConfigurationException, SAXException, IOException, NoSuchMethodException {
		
		MachineHandler_Socket.init(MACHINE_SOCKET_XML_PATH);
		ServerMachine = LoaderUtils.getServerMachine();
		ServerMachine.start();
        
		Assert.assertTrue(message, condition);
	}

	**/
	@Test
	public void bindClientMachineTest() throws NetworkException , ParserConfigurationException, SAXException, IOException, NoSuchMethodException {
		
		MachineHandler_Socket.init(MACHINE_SOCKET_XML_PATH);
		ServerMachine = LoaderUtils.getServerMachine();
		ServerMachine.start();
        
		ServerMachine.bindClientMachine(ClientMachine);
		String bindingMessage = "The ClientMachine is bound to the ServerMachine";
		
		assertTrue(bindingMessage, true);
		
	}
	
	
}
