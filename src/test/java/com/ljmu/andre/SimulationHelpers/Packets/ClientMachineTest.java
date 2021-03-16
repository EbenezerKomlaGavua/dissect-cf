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

import com.ljmu.andre.SimulationHelpers.Packets.RoutingPacket1;

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
	Repository repository1,repository2;
	private ArrayList<DataPacket>  PacketArray = new ArrayList<DataPacket>();
	private RoutingPacket Packet;
	 private static final int limit = 10;
	//private List<String> failedPacketIds = new ArrayList<String>();
	//ArrayList<BasePacket>Packet = new ArrayList<BasePacket>();
	 
	 String Id1,Id2;
	 private SubscriptionPacket packett;
	 State connectionState;
	 private static int BindPacket=2;
	private PhysicalMachine PhysicalMachine1,PhysicalMachine2;
	BasePacket bindingPacket;
	DataPacket P1 = new  DataPacket("one", 3, true);
	 	 
	 public final static int data =1;
	
	 
	 
	 
	 /*
		Check on 
		handle success
		print storage metrics
		registerPacketIfNotExist
		ConnectionFinished
		
	
	*/
	
	
		

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
	public  void StartMachinetest() throws NetworkException, ParserConfigurationException, SAXException, IOException, NoSuchMethodException {
		
		MachineHandler_Socket.init(MACHINE_SOCKET_XML_PATH);
		ClientMachine = LoaderUtils.getClientMachine();
		ServerMachine = LoaderUtils.getServerMachine();
		cloud = LoaderUtils.getCloud();
		     
			
	}
   		
	
	
	
	//Check the availability of an Id of ClientMachine
	 @Test
	  	public void StartClientMachineTest_ID() throws NetworkException {
		    cloud.start();
			ClientMachine.start();
			ServerMachine.start();
			//assertEquals("The local disks is not available",RepoCapacity, ServerMachine.getRepository().getMaxStorageCapacity());
		
			assertEquals("The local disks is not available",ClientMachine.getRepository().getName(), ClientMachine.getId());
			
		}
	 
	 
	  
	//Cross-Check ClientMachine Id with ServerMachine Id
		 @Test
		  	public void StartClientMachineTest_WithServerId() throws NetworkException {
				cloud.start();
		        ClientMachine.start();
		        ServerMachine.start();
				//assertEquals("The local disks is not available",RepoCapacity, ServerMachine.getRepository().getMaxStorageCapacity());
			
				//assertEquals("The local disks is not available",ClientMachine.getRepository().getName(), ClientMachine.getId());
			 assertNotEquals("The local disks is not available",ServerMachine.getRepository().getName(), ClientMachine.getId());
				//assertNotEquals(message, first, second);
				
			}
	 
		 
	//Check the availability of an Id of ClientMachine
		 @Test(timeout = 100)
		  	public void StartServerMachineTest_Id() {
				cloud.start();
		        ClientMachine.start();
		        ServerMachine.start();		
				//assertEquals("The local disks is not available",RepoCapacity, ServerMachine.getRepository().getMaxStorageCapacity());
				assertEquals("The local disks is not available",ServerMachine.getRepository().getName(), ServerMachine.getId());
		
		}
	 
		 	
		//Cross-Check ServerMachine Id with ClientMachine Id
		 @Test
		  	public void StartServerMachineTest_WithClientMachineId() throws NetworkException {
					cloud.start();
		            ClientMachine.start();
		            ServerMachine.start();	
				//assertEquals("The local disks is not available",RepoCapacity, ServerMachine.getRepository().getMaxStorageCapacity());
				assertNotEquals("The local disks is not available",ClientMachine.getRepository().getName(), ServerMachine.getId());
		}
	 	 		 
		  //Check the availability of an Id of ClientMachine
		 @Test
		  	public void CheckCloudMachineIdCreation() throws NetworkException {
				cloud.start();
		        ClientMachine.start();
		        ServerMachine.start();
				assertEquals("The local disks is not available",cloud.Id, cloud.getId());

		 
		 }
		 
		 
		
		//Check the availability of an Id of ClientMachine
		 @Test
		  	public void CheckCloudMachineRepoCreation() throws NetworkException {
				
				//assertEquals("The local disks is not available",RepoCapacity, ServerMachine.getRepository().getMaxStorageCapacity());
			
				assertEquals("The local disks is not available",cloud.getCloudMachine(), cloud.getRepository());
 
		 }
			 
		  @Test(timeout = 1000)
		public void CheckConnectionStartedTest_ON() throws NetworkException, ParserConfigurationException, SAXException, IOException, NoSuchMethodException  {
		cloud.start();
		ClientMachine.start();
		 ServerMachine.start();
		 assertEquals("No connection between machines", ServerMachine, ClientMachine.handleConnectionStarted(ServerMachine));
		// ClientMachine.stop();
	
	}
		 
	// Check to see if the ServerMachine is still reachable from the ClientMachine, when the clientMachine is turned off.
	@Test(timeout=1000)
		public void CheckConnectionStartedTest_OFF() throws NetworkException {
		cloud.start();
		ServerMachine.start();
		ClientMachine.stop();
	 assertNotEquals("No connection between machines", ServerMachine, ClientMachine.handleConnectionStarted(ServerMachine));
	     
		}
	
	
	// Check to see if the BasePackets are actually in the Array
	@Test(timeout = 100)
    public void CheckStatusOfArray() throws NetworkException {
		cloud.start();
		ClientMachine.start();
		 ServerMachine.start();
		ArrayList<DataPacket> PacketArray = ClientMachine. PacketArray();
		 assertNotNull("List shouldn't be null", PacketArray);
		} 
	
	// Check to see if the array size is correct
	@Test(timeout = 100)
	    public void CheckSizeOfArray() throws NetworkException {
		cloud.start();
		ClientMachine.start();
		 ServerMachine.start();
		ArrayList<DataPacket> PacketArray = ClientMachine. PacketArray();
			 assertEquals("wrong size", 3, PacketArray.size());
	} 
	
	// Transfer array of packets from ClientMachine to serverMachine

	@Test(timeout = 1000)
    public void TranferPacketArray() throws NetworkException { 
		cloud.start();
		ClientMachine.start();
		 ServerMachine.start();
		ArrayList<DataPacket> PacketArray = ClientMachine. PacketArray();
		assertEquals("Packet should not be transferrable", PacketArray, ClientMachine.sendPacketArray(ClientMachine, ServerMachine, PacketArray));
} 		
		 
				 
	// Checking if a packet can be sent and registered at a target			
		@Test(timeout = 100)	
		public void SendPacketTest() throws   NetworkException {
			cloud.start();
		ClientMachine.start();
		 ServerMachine.start();
		 assertEquals("Packet was not delivered", true, ClientMachine.sendPacket(ClientMachine, ServerMachine, P1));
			 }
					

		// Checking if a the method is not efficient.		
		 @Test(timeout = 100)	
		 public void SendPacketTest_NotWorking() throws   NetworkException {
	     cloud.start();
		 ClientMachine.start();
		  ServerMachine.start();
		 assertNotEquals("Packet was not delivered", false, ClientMachine.sendPacket(ClientMachine, ServerMachine, P1));
			}
	
				 
		//Checking if a subscription packet  can be sent to bind the ClientMachine and the ServerMachine
	 @Test(timeout = 100)
	    public void BindServerMachineTest()throws NetworkException  {
	    cloud.start();
		 ClientMachine.start();
		ServerMachine.start();
		assertEquals("ServerMachine is was not linked up", true,ClientMachine.bindServerMachine(ServerMachine));
	}
					 
	//Checking if the method is efficient.
		 @Test(timeout = 100)
		 public void BindServerMachineTest_NotWorking()throws NetworkException  {
			 cloud.start();
						 ClientMachine.start();
						 ServerMachine.start();
				    	   assertNotEquals("ServerMachine is was not linked up", false,ClientMachine.bindServerMachine(ServerMachine));
				    				 
					 }
			
					 
	/*				 	

	//Check the availability of a repository of ClientMachine
	 @Test
	  	public void CheckRepoCreation() {
			//assertEquals("The local disks is not available",RepoCapacity, ServerMachine.getRepository().getMaxStorageCapacity());
			assertEquals("The local disks is not available",ServerMachine.repository, ClientMachine.getRepository());
	}

	
	//Check the availability of a repository of ClientMachine
		 @Test
		  	public void CheckClientRepoCreation() throws NetworkException {
			
				//assertEquals("The local disks is not available",RepoCapacity, ServerMachine.getRepository().getMaxStorageCapacity());
				assertEquals("The local disks is not available", ServerMachine.repository, ClientMachine.getRepository());
		}
	 
	 
		//Check the availability of a repository of ClientMachine
		 @Test
		  	public void CheckServerRepoCreation()throws NetworkException {
					//assertEquals("The local disks is not available",RepoCapacity, ServerMachine.getRepository().getMaxStorageCapacity());
				assertEquals("The local disks is not available", ClientMachine.repository, ServerMachine.getRepository());
		}
	  
	
   	
	
	*/
	
	
	
	
}

	
	
	
		
