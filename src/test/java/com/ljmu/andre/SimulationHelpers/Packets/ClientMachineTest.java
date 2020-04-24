package com.ljmu.andre.SimulationHelpers.Packets;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.xml.sax.SAXException;

import hu.mta.sztaki.lpds.cloud.simulator.io.NetworkNode.NetworkException;
import junit.framework.Assert;



public class ClientMachineTest {
	private static final String USER_DIR = System.getProperty("user.dir");
	private ClientMachine ClientMachine;
	protected  ServerMachine ServerMachine;
	private Cloud cloud;
	private static final String MACHINE_SOCKET_XML_PATH = USER_DIR + "/Machine_Socket1.xml";
		// TODO Auto-generated constructor stub
	
    String message = "The PhysicalMachine is TurnON";
	
	Boolean condition = true;
	
	
	@Before
	public  void StartClientMachinetest() throws NetworkException, ParserConfigurationException, SAXException, IOException, NoSuchMethodException {
		
	
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
    	
    	ClientMachine.connectionStarted(ServerMachine);
    	
    	assertTrue("The PhysicalMachine is TurnON", true);
    }
      
	
		
	// Checking whether the ClientMachine was able to bind with the ServerMachine
	@Test
	public void bindServerMachineTest() throws NetworkException , ParserConfigurationException, SAXException, IOException, NoSuchMethodException {
		
		
		ClientMachine.bindServerMachine(ServerMachine);
		String bindingMessage = "The ClientMachine is bound to the ServerMachine";
		
		assertTrue(bindingMessage, true);
		
	}
	
	//Checking the stop method of the ClientMachine
	@Test
	public void StopClientMachine() {
		ClientMachine.stop();
		String StopMessage = "The ClientMachine has stopped working";
		
		assertTrue(StopMessage,true);
	}
		
}