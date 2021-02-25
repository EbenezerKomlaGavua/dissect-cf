package com.ljmu.andre.SimulationHelpers.Packets;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import javax.xml.parsers.ParserConfigurationException;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.xml.sax.SAXException;

import com.ljmu.andre.SimulationHelpers.ConnectionEvent;
import com.ljmu.andre.SimulationHelpers.ConnectionEvent.State;
import com.ljmu.andre.SimulationHelpers.Utils.Logger;

import at.ac.uibk.dps.cloud.simulator.test.ConsumptionEventAssert;
import hu.mta.sztaki.lpds.cloud.simulator.Timed;
import hu.mta.sztaki.lpds.cloud.simulator.io.NetworkNode;
import hu.mta.sztaki.lpds.cloud.simulator.io.Repository;
import hu.mta.sztaki.lpds.cloud.simulator.io.NetworkNode.NetworkException;
import junit.framework.Assert;

//import org.junit.Assert;

public class ClientMachineTest {
	private static final String USER_DIR = System.getProperty("user.dir");
	private ClientMachine ClientMachine;
	private static final Logger logger = new Logger(ClientMachineTest.class);
	
	protected  ServerMachine ServerMachine;
	private Cloud cloud;
	private static final String MACHINE_SOCKET_XML_PATH = USER_DIR + "/Machine_Socket1.xml";
		// TODO Auto-generated constructor stub
	private BasePacket packet;
    String message = "The PhysicalMachine is TurnON";
    private static int NumberOfPackets = 1;
	Boolean condition = true;
 private final static long RepoCapacity  = 40000000000L;
	Repository repository;
	private ArrayList<BasePacket> packets = new ArrayList<BasePacket>();
	 String Id;
	 private SubscriptionPacket packett;
	 State connectionState;
	 private static int BindPacket=1;
	
	 	 
	 public final static int data =1;
	 
	 /*
		Check on 
		handle success
		print storage metrics
		registerPacketIfNotExist
		ConnectionFinished
		
		*/	
	 
	 
	 private static int sendPacket (final ConnectionEvent ClientMachine, final ConnectionEvent ServerMachine,
				final ArrayList<BasePacket>  packets)throws NetworkException {
				BasePacket sendPacket = new DataPacket("Data", 10, true);
			PacketHandler.sendPacket(ClientMachine, ServerMachine, sendPacket);
			System.out.println("Packet Transfer");
			return NumberOfPackets;
		}

	
	private static int bindServerMachine(final ConnectionEvent ClientMachine, final ConnectionEvent ServerMachine,
			final SubscriptionPacket  packett)throws NetworkException {
			BasePacket bindingPacket = new SubscriptionPacket(true).setShouldStore(true);
               PacketHandler.sendPacket(ClientMachine, ServerMachine, bindingPacket);
	      System.out.println("Binding");
	     return BindPacket;
	}
		
	private static boolean registerPacketIfNotExist(final ConnectionEvent ServerMachine, BasePacket packets) {
        if (ServerMachine.getRepository().lookup(packets.id) == null) {
            logger.log("Registering packet: " + packets);
            return ServerMachine.getRepository().registerObject(packets);
        }

        return true;
    }
	
		 
	 		
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
   
	
	@Test(timeout = 100)
	public void CheckMachinesConnectivity() throws NetworkException {
		assertEquals("No connection between machines", ClientMachine, 
				ClientMachine.handleConnectionStarted(ClientMachine));
	     
		 assertEquals("No connection between machines", ServerMachine, 
				ServerMachine.handleConnectionStarted(ServerMachine));
 	
	}

	
       @Test(timeout = 100)
       public void CheckDataTransferMachinesConnectivity(){
    	   
    	 //Checking if a subscription packet  can be sent to bind the ClientMachine and the ServerMachine	
    	   try {
    	   assertEquals("ServerMachine is was not linked up", 1, 
    				ClientMachineTest.bindServerMachine(ClientMachine, ServerMachine, packett));
    	   } catch (NetworkException ex) {
      			fail("Internal connectivity between machines should always work");
      		}
        
    	   try {
   			assertEquals("Packet should be able to beInternal connectivity for node source is not without latency", 1,
   					ClientMachineTest.sendPacket(ClientMachine, ServerMachine, packets));
   		} catch (NetworkException ex) {
   			fail("Internal connectivity between machines should always work");
   		}
           
}
       /*  
       @Test(timeout = 100)	  
       public void CheckPacketTransferSuccess() {
    	   try {
    		  
    		   assertTrue("Packet not successfully transferred", ClientMachineTest.registerPacketIfNotExist(ServerMachine, packet));
    		  
    	   } catch (NetworkException ex) {
    		   
    	   }
    	   fail("Internal connectivity between machines should always work");
       }
	
      
       */     
       
       @Test
   	
   	public void CheckClientMachineCreation() {
   	
   	
   			//Check the availability of a repository size of ServerMachine
   		
   			assertEquals("The local disks is not available",RepoCapacity, ServerMachine.getRepository().getMaxStorageCapacity());

   	}
   	
	


	
	
	
	
	
	
	
	
	
	
}
	
	
	
		
