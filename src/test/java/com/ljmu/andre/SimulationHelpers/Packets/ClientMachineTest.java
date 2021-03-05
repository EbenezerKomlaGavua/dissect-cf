package com.ljmu.andre.SimulationHelpers.Packets;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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
import hu.mta.sztaki.lpds.cloud.simulator.iaas.PhysicalMachine;
import hu.mta.sztaki.lpds.cloud.simulator.iaas.resourcemodel.ResourceConsumption.ConsumptionEvent;
import hu.mta.sztaki.lpds.cloud.simulator.io.NetworkNode;
import hu.mta.sztaki.lpds.cloud.simulator.io.Repository;
import hu.mta.sztaki.lpds.cloud.simulator.io.NetworkNode.NetworkException;
import junit.framework.Assert;

//import org.junit.Assert;

public class ClientMachineTest extends Timed {
	private static final String USER_DIR = System.getProperty("user.dir");
	private ClientMachine ClientMachine;
	private static final Logger logger = new Logger(ClientMachineTest.class);
	private int PacketsCount = 5;
	protected  ServerMachine ServerMachine;
	private Cloud cloud;
	private static final int SUBSCRIBE_FREQ = 10;
	final static long expectedFires = 5;
	private static final String MACHINE_SOCKET_XML_PATH = USER_DIR + "/Machine_Socket1.xml";
		// TODO Auto-generated constructor stub
	private BasePacket packet;
    String message = "The PhysicalMachine is TurnON";
    private static int NumberOfPackets = 1;
	Boolean condition = true;
 private final static long RepoCapacity  = 40000000000L;
	Repository repository;
	private ArrayList<BasePacket>  packetList = new ArrayList<BasePacket>();
	
	 private static final int limit = 10;
	//private List<String> failedPacketIds = new ArrayList<String>();
	//ArrayList<BasePacket>Packet = new ArrayList<BasePacket>();
	 
	 String Id;
	 private SubscriptionPacket packett;
	 State connectionState;
	 private static int BindPacket=1;
	private PhysicalMachine PhysicalMachine;
	 	 
	 public final static int data =1;
	
	 
	 
	 
	 /*
		Check on 
		handle success
		print storage metrics
		registerPacketIfNotExist
		ConnectionFinished
		
			
	 
	  */
	 
		 class Packet{ 
		 String myid;
		 long mysize;
		 boolean vary;
		 
		  Packet(String myid,long mysize,boolean vary){
			  this.myid=myid;
			  this.mysize= mysize;
			  this.vary= vary;
		 
		  }
		  }
	 
	 BasePacket P1 = new  BasePacket("one", 3, true);
	 BasePacket P2 = new  BasePacket("two", 4, true);
	 BasePacket P3 = new  BasePacket("three", 5, true);
	 

	 public ArrayList<BasePacket>  PacketArray() { 
	 ArrayList< BasePacket> PacketArray = new ArrayList< BasePacket>();
	 PacketArray.add(P1);
	 PacketArray.add(P2);
	 PacketArray.add(P3);
	return  PacketArray;
	 }
	 
		 
	 private static ArrayList<BasePacket> sendPacket (final ConnectionEvent ClientMachine, final ConnectionEvent ServerMachine,final ArrayList<BasePacket> PacketArray){
				
			 logger.log("Attempting to send: " +  PacketArray.size() + " packets");
     
	// ArrayList<BasePacket> packetList = new ArrayList<BasePacket>();

     for (BasePacket packet :  PacketArray) {
         logger.log("Sending packet: " + packet.id);

         sendPacket(ClientMachine, ServerMachine, PacketArray);
     }

     if (  PacketArray.size() > 0)
         logger.log(" packetList: " +   PacketArray.size());

     return   PacketArray;
 }
			 
		
	 private static boolean  sendPacket (final ConnectionEvent ClientMachine, final ConnectionEvent ServerMachine, final BasePacket  packet) {
			// ConsumptionEvent consumptionEvent = getConsumptionEvent(ClientMachine,
			// ServerMachine, packet);
			///Scenarioo.logMessage(hashCode() + " Server is not responding, let's wait!");
			// Create a sendPacket object DataPacket to transfer and store a data unit from
			// the ClientMachine to the ServerMachine.
			BasePacket sendPacket = new DataPacket("Data", 10, true);
			PacketHandler.sendPacket(ClientMachine, ServerMachine, sendPacket);
			System.out.println("Packet Transfer");
			return true;
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
	
	private Repository getRepository() {
		// TODO Auto-generated method stub
		return PhysicalMachine.localDisk;
	}

		
	private void printStorageMetrics() {
		long freeCap = getRepository().getFreeStorageCapacity();
		long maxCap = getRepository().getMaxStorageCapacity();
		logger.log("Disk: [%s/%s]", freeCap, maxCap);

	}

	public  void stop() {
		unsubscribe();
	
		logger.log("Stopped: " + unsubscribe());
	}
	
	
		

	public void tick(long fires) {
		// TODO Auto-generated method stub

		while (NumberOfPackets < PacketsCount) {
			//ConsumptionEvent consumptionEvent = getConsumptionEvent(ClientMachine, ServerMachine, packet);
			PacketHandler.sendPacket(ClientMachine, ServerMachine, new DataPacket("Data", 10, true));
			// Displays the number of Packets transferred from the ClientMachine to the
			// ServerMachine.
			logger.log("Packet: " + NumberOfPackets);
			NumberOfPackets++;

		}
	}


	 		
	@Before
	public  void StartClientMachinetest() throws NetworkException, ParserConfigurationException, SAXException, IOException, NoSuchMethodException {
		
		MachineHandler_Socket.init(MACHINE_SOCKET_XML_PATH);
		ClientMachine = LoaderUtils.getClientMachine();
		ServerMachine = LoaderUtils.getServerMachine();
		cloud = LoaderUtils.getCloud();
		// ClientMachine.start();
		 //ServerMachine.start();
	     
			
	}
   	
	
	// Check to see if the BasePackets are actually in the Array
				@Test(timeout = 100)
			    public void CheckStatusOfArray() throws NetworkException {
					ClientMachineTest clientMachineTest = new ClientMachineTest();
					ArrayList< BasePacket> BasePacketArray = clientMachineTest. PacketArray();
					 assertNotNull("List shouldn't be null", BasePacketArray);
					} 
	
	// Check to see if the array size is correct
		@Test(timeout = 100)
		    public void CheckSizeOfArray() throws NetworkException {
				ClientMachineTest clientMachineTest = new ClientMachineTest();
				ArrayList< BasePacket> BasePacketArray = clientMachineTest. PacketArray();
				 assertEquals("wrong size", 3, BasePacketArray.size());
		} 
		
	
	// Transfer array of packets from ClientMachine to serverMachine

	@Test(timeout = 10000)
    public void TranferBasePacketArray() throws NetworkException {
		ClientMachineTest clientMachineTest = new ClientMachineTest();
		ArrayList< BasePacket> BasePacketArray = clientMachineTest. PacketArray();
		assertEquals("Packet should not be transferrable", P1, ClientMachineTest.sendPacket(ClientMachine, ServerMachine, BasePacketArray));
		
} 
	
	
	
	
	
	
	
	@Test(timeout = 100)
	//ClientMachine clientMachine;
	public void CheckClientMachineON() throws NetworkException, ParserConfigurationException, SAXException, IOException, NoSuchMethodException  {
		cloud.start();
		ClientMachine.start();
		 ServerMachine.start();
		assertEquals("No connection between machines", ServerMachine, ClientMachine.handleConnectionStarted(ServerMachine));
		
		 ClientMachine.stop();
		
	}
	
	

	
	//}
	
	
	/*	
	
	
	
	
	@Test
		public void CheckClientMachineConnectivityOFF() throws NetworkException {
	 ClientMachine.stop();
		assertEquals("No connection between machines", ServerMachine, ClientMachine.handleConnectionStarted(ServerMachine));
	     
		
	}
	
	
	@Test(timeout = 100)
	public void CheckServerMachineConnectivity() throws NetworkException {
		
	     
		 assertEquals("No connection between machines", ServerMachine, 
				ServerMachine.handleConnectionStarted(ServerMachine));
 	
	}

	
	

	
	
	

	       @Test(timeout = 1000)
       public void CheckBindingServerMachineToClientMachine()throws NetworkException  {
    	   
    	 //Checking if a subscription packet  can be sent to bind the ClientMachine and the ServerMachine	
    	   try {
    	   assertEquals("ServerMachine is was not linked up", 1, 
    				ClientMachineTest.bindServerMachine(ClientMachine, ServerMachine, packett));
    	   } catch (NetworkException ex) {
      			fail("Internal connectivity between machines should always work");
      		}
        
    	              
}
       
	       
       
       
       @Test(timeout = 100)	  
       public void CheckPacketTransferSuccess() {
    	   //assertTrue("Packet not successfully transferred", ClientMachineTest.registerPacketIfNotExist(ServerMachine, packet));
    	  //fail("Internal connectivity between machines should always work");
       }
	
      
               
       @Test
   	
   	public void CheckClientMachineCreation() {
   	   	
   			//Check the availability of a repository size of ServerMachine
   		
   			//assertEquals("The local disks is not available",RepoCapacity, ServerMachine.getRepository().getMaxStorageCapacity());
   			assertEquals("The local disks is not available",0, ServerMachine.getRepository().getFreeStorageCapacity());

   	}
   	
	
	
	
	
	*/
	
}
//}
	
	
	
		
