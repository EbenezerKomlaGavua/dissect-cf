package com.ljmu.andre.SimulationHelpers.Packets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.Stack;

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
	ClientMachine clientMachine;
	
	private static final Logger logger = new Logger(ClientMachineTest.class);
	private int PacketsCount = 5;
	protected  ServerMachine serverMachine;
	protected  Router2 router2;
	protected  ServerMachine3 serverMachine3;
	String serverMachineId = "10.10.10.2";
	String router2Id = "10.10.10.1";
	private Cloud cloud;
	protected Router1 router1;
	private static final int SUBSCRIBE_FREQ = 10;
	final static long expectedFires = 5;
	private static final String MACHINE_SOCKET_XML_PATH = USER_DIR + "/Machine_Socket3.xml";
		// TODO Auto-generated constructor stub
	private BasePacket packet;
    String message = "The PhysicalMachine is TurnON";
    private static int NumberOfPackets = 1;
	Boolean condition = true;
 private final static long RepoCapacity  = 40000000000L;
	Repository repository1,repository2;
	private ArrayList<BasePacket>  PacketArray = new ArrayList<BasePacket>();
	public List<ConnectionEvent> connectedDevices = new ArrayList<ConnectionEvent>();
	ArrayList<ConnectionEvent> Devices = new ArrayList< ConnectionEvent>();
	LinkedList<ConnectionEvent> Aroute = new LinkedList<ConnectionEvent>();
	private RoutingPacket Packet;
	 private static final int limit = 10;
	//private List<String> failedPacketIds = new ArrayList<String>();
	//ArrayList<BasePacket>Packet = new ArrayList<BasePacket>();
	 
	 String Id1,Id2;
	 private SubscriptionPacket packett;
	 State connectionState;
	 private static int BindPacket=2;
	private PhysicalMachine PhysicalMachine;
	BasePacket bindingPacket;
	//DataPacket P1 = new  DataPacket("one", 32, true);
	 	 boolean success;
	 public final static int data =1;
	
	 //public Set<String> visited = new HashSet<String>();
	 
	 
	 /*
		Check on 
		handle success
		print storage metrics
		registerPacketIfNotExist
		ConnectionFinished
		
	
	*/
	
	 public ArrayList<DataPacket>  PacketArray() { 
			ArrayList<DataPacket> PacketArray = new ArrayList< DataPacket>();
			PacketArray.add(P1);
			PacketArray.add(P2);
			PacketArray.add(P3);
			PacketArray.add(P4);
			return  PacketArray;
			}

		DataPacket P1 = new  DataPacket("one",32,false);
		DataPacket P2 = new  DataPacket("two",32,false);
		DataPacket P3 = new  DataPacket("three",32,false);
		DataPacket P4 = new  DataPacket("four",32,false);
		
		RoutingPacket R1 = new RoutingPacket("FIVE", P1, clientMachine, Aroute);	

		
		
		
		
	public void tick(long fires) {
		// TODO Auto-generated method stub

		while (NumberOfPackets < PacketsCount) {
			//ConsumptionEvent consumptionEvent = getConsumptionEvent(ClientMachine, ServerMachine, packet);
			PacketHandler.sendPacket(clientMachine, serverMachine, new DataPacket("Data", 10, true));
			// Displays the number of Packets transferred from the ClientMachine to the
			// ServerMachine.
			logger.log("Packet: " + NumberOfPackets);
			NumberOfPackets++;

		}
	}


	 		
	@Before
	public  void StartMachinetest() throws NetworkException, ParserConfigurationException, SAXException, IOException, NoSuchMethodException {
		
		MachineHandler_Socket.init(MACHINE_SOCKET_XML_PATH);
		clientMachine = LoaderUtils.getClientMachine();
		serverMachine = LoaderUtils.getServerMachine();
		router2 = LoaderUtils.getRouter2();
		serverMachine3 = LoaderUtils.getServerMachine3();
		cloud = LoaderUtils.getCloud();
		router1 = LoaderUtils.getRouter1();
		     
		
	}
	/*
	 * 
	
	  
	//Cross-Check ClientMachine Id with ServerMachine Id
		 @Test
		  	public void StartClientMachineTest_WithServerId() throws NetworkException {
				cloud.start();
		        ClientMachine.start();
		        ServerMachine.start();
			 assertNotEquals("The local disks is not available",ServerMachine.getRepository().getName(), ClientMachine.getId());
				
			}
	 
		 
	//Check the availability of an Id of ClientMachine
		 @Test(timeout = 100)
		  	public void StartServerMachineTest_Id() {
				cloud.start();
		        ClientMachine.start();
		        ServerMachine.start();		
				//assertEquals("The local disks is not available",RepoCapacity, ServerMachine.getRepository().getMaxStorageCapacity());
				assertEquals("The local disks is not available",ServerMachine.getRepository().getName(), ServerMachine.getId());
				System.out.println(ServerMachine.getRepository().getName());
		
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
		System.out.println(PacketArray());
		Timed.getFireCount();
		 assertNotNull("List shouldn't be null", PacketArray);
		 
		} 
	
	
	//Check the availability of an Id of ClientMachine
		 @Test
		  	public void StartClientMachineTest_ID() throws NetworkException {
			    cloud.start();
				clientMachine.start();
				serverMachine.start();
				router.start();
				assertEquals("The local disks is not available",clientMachine.getRepository().getName(), clientMachine.getId());
				System.out.println(clientMachine.getRepository().getName());
			}
	
	
		  //Check the availability of an Id of ClientMachine
		 @Test
		  	public void CheckRoouterIdCreation() throws NetworkException {
				cloud.start();
		        clientMachine.start();
		        serverMachine.start();
		        router.start();
				assertEquals("The local disks is not available",router.getRepository().getName(), router.getId());
		 
		 }
		 
		 @Test(timeout = 1000)
				public void CheckConnectionStartedTest_ON() throws NetworkException, ParserConfigurationException, SAXException, IOException, NoSuchMethodException  {
				cloud.start();
				clientMachine.start();
				 serverMachine.start();
				 router.start();
				 assertEquals("No connection between machines", serverMachine, clientMachine.handleConnectionStarted(serverMachine));
				// ClientMachine.stop();
			
			}
				
		 
		 
		 
		 
		 
		
		 
		 
	
		
		
			// Check to see if the BasePackets are actually in the Array
						@Test(timeout = 100)
					    public void CheckConnectedDevicesArray() throws NetworkException {
							cloud.start();
							clientMachine.start();
							 serverMachine.start();
							 router.start();
							 clientMachine.handleConnectionStarted(router);
							List<ConnectionEvent> connectedDevices = clientMachine.getConnectedDevices();
							System.out.println(connectedDevices);
							//Timed.getFireCount();
							// assertNotNull("List shouldn't be null", connectedDevices);
							 //assertEquals("No connection between machines",router, clientMachine.handleConnectionStarted(router));
							assertEquals("No connection between machines",2, connectedDevices.size());
								
						} 	   
			
			 
			
			
			
			
			
			
			@Test
			 public void CheckConnectedDevices() throws NetworkException{
				 
				 cloud.start();
			        clientMachine.start();
			        serverMachine.start();
			        router.start();
			        clientMachine.handleConnectionStarted(router);
			        System.out.println("see somthing" +" "+ clientMachine.disconnectDevice(router));
			        assertEquals("There are no connected devices", success, clientMachine.disconnectDevice(router));
			        
			   
			 }
			
	
	
	@Test
	 public void CheckConnectedDevicesFrom_ClientMachineTo_Router() throws NetworkException{
		 
		 cloud.start();
	        clientMachine.start();
	        serverMachine.start();
	        router.start();
	        serverMachine2.start();
			 serverMachine3.start();
			 clientMachine.handleConnectionStarted(router);
	       // System.out.println("see somthing" +" "+ clientMachine.connectDevice(router));
	       
	      //  assertTrue("clientMachine is not connected to the router",clientMachine.connectDevice(router));
	        
	   
	 }
	
	
	// Check to see if the BasePackets are actually in the Array
				@Test(timeout = 100)
			    public void CheckStatusOfArray() throws NetworkException {
					cloud.start();
					clientMachine.start();
					 serverMachine.start();
					 serverMachine2.start();
					 serverMachine3.start();
					 router.start();
					 clientMachine.handleConnectionStarted(router);
					 ClientMachine clientMachine = new ClientMachine(PhysicalMachine, repository1, Id1);
					 clientMachine.connectDevice(router, serverMachine, serverMachine2, serverMachine3);
					List<ConnectionEvent> connectedDevices = clientMachine.getConnectedDevices();
					System.out.println(connectedDevices);
					assertEquals("No connection between machines",4, clientMachine.getConnectedDevices().size());
						
				}
				
				 	
			
			 @Test(timeout = 100)
		public void ChildrenVisitedTest() throws NetworkException {
			
			
			cloud.start();
			clientMachine.start();
			 serverMachine.start();
			 router.start();
			 serverMachine2.start();
			 serverMachine3.start();
			 router.handleConnectionStarted(clientMachine);
			 clientMachine.handleConnectionStarted(router);
			 serverMachine.handleConnectionStarted(clientMachine);
			/// ClientMachine clientMachine = new ClientMachine(PhysicalMachine, repository1, Id1);
			 //clientMachine.connectDevice(router, serverMachine, serverMachine2, serverMachine3);
			//ClientMachine clientmachine = new ClientMachine(PhysicalMachine, repository1, Id1);
			//clientmachine.connectDevice(router, serverMachine, serverMachine2, serverMachine3);
			//Router router = new Router(PhysicalMachine, repository1, Id1);
			//router.connectDevice(clientMachine, serverMachine, serverMachine2, serverMachine3);
			ServerMachine2 serverMachine2 = new ServerMachine2(PhysicalMachine, repository1, Id1);
			serverMachine2.connectDevice(clientMachine, serverMachine3, router, serverMachine);
			 	//List<ConnectionEvent> connectedDevices = router.getConnectedDevices();
			 	List<ConnectionEvent> connectedDevices = serverMachine2.getConnectedDevices();
			 	
			 	System.out.println(connectedDevices);
			 	Set<String> visited = clientMachine.getvisited();
		           System.out.println(visited);
		           
		          // assertEquals("No connection between machines",3, clientmachine.getvisited().size());
				assertEquals("There are no children",clientMachine, clientMachine.getUnvisited(serverMachine2, visited));
		}
					
	            
					
	
	
	
	@Test(timeout = 1000)
	public void GetConnectionRouteTest() throws NetworkException {
		
		
		cloud.start();
		clientMachine.start();
		 serverMachine.start(); 
		 router.start();
		 serverMachine2.start();
		 serverMachine3.start();
		 router.handleConnectionStarted(clientMachine);
		 clientMachine.handleConnectionStarted(router);
		 serverMachine2.handleConnectionStarted(clientMachine);
		 ClientMachine clientMachine = new ClientMachine(PhysicalMachine, repository1, Id1);
		 clientMachine.connectDevice(router, serverMachine, serverMachine2, serverMachine3);
		//ClientMachine clientmachine = new ClientMachine(PhysicalMachine, repository1, Id1);
		//clientmachine.connectDevice(router, serverMachine, serverMachine2, serverMachine3);
		Router router = new Router(PhysicalMachine, repository1, Id1);
		router.connectDevice(clientMachine, serverMachine, serverMachine2, serverMachine3);
		ServerMachine2 serverMachine2 = new ServerMachine2(PhysicalMachine, repository1, Id1);
		serverMachine2.connectDevice(clientMachine, serverMachine3, router, serverMachine);
		 	List<ConnectionEvent> connectedDevices = clientMachine.getConnectedDevices();
		 	///System.out.println(connectedDevices);
		 	Set<String> visited = clientMachine.getvisited();
	         ///  System.out.println(visited);
	           //clientMachine.getUnvisited(router, visited);
	           Stack<ConnectionEvent> route =  clientMachine.getroute();
	         // System.out.println (clientMachine.getUnvisited(router, visited));
	         // System.out.println(route);
	         //  LinkedList<ConnectionEvent> Aroute = clientMachine.Aroute();
	           
	           
	           
			//assertEquals("There are no children", route, clientMachine.getConnectionRoute(serverMachine2, serverMachineId) );
	}
	
	*/
	
	@Test(timeout = 1000)
	public void SendRoutingPacketTest() throws NetworkException {
		
		
		cloud.start();
		clientMachine.start();
		 serverMachine.start(); 
		 router1.start();
		 router2.start();
		 serverMachine3.start();
		 router1.handleConnectionStarted(clientMachine);
		 clientMachine.handleConnectionStarted(router1);
		 router2.handleConnectionStarted(clientMachine);
		 ClientMachine clientMachine = new ClientMachine(PhysicalMachine, repository1, Id1);
		 clientMachine.connectDevice(router1, serverMachine, router2, serverMachine3);
		//ClientMachine clientmachine = new ClientMachine(PhysicalMachine, repository1, Id1);
		//clientmachine.connectDevice(router, serverMachine, serverMachine2, serverMachine3);
		Router1 router = new Router1(PhysicalMachine, repository1, Id1);
		router.connectDevice(clientMachine, serverMachine, router2, serverMachine3);
		Router2 router2 = new Router2(PhysicalMachine, repository1, Id1);
		router2.connectDevice(clientMachine, serverMachine3, router, serverMachine);
		 	List<ConnectionEvent> connectedDevices = clientMachine.getConnectedDevices();
		 	///System.out.println(connectedDevices);
		 	Set<String> visited = clientMachine.getvisited();
	         ///  System.out.println(visited);
	           //clientMachine.getUnvisited(router, visited);
	           Stack<ConnectionEvent> route =  clientMachine.getroute();
	         // System.out.println (clientMachine.getUnvisited(router, visited));
	         // System.out.println(route);
	         //  LinkedList<ConnectionEvent> Aroute = clientMachine.Aroute();
	           
	           
	           
			assertEquals("There are no children", true, clientMachine.sendRoutingPacket(clientMachine, serverMachineId, R1) );
	}
	
	
	
	
	
	
	
	/*
	
	
			@Test
			 public void CheckDisconnectedDevices1() throws NetworkException{
				 
				 cloud.start();
			        clientMachine.start();
			        serverMachine.start();
			        router.start();
			        clientMachine.handleConnectionStarted(router);
			        System.out.println("see somthing" +" "+ clientMachine.disconnectDevice(router));
			       // assertTrue( clientMachine.connectDevice(router));
			        assertEquals("I expected positive answer ", success, clientMachine.disconnectDevice(router));
			        
			   
			 }
				
			
			
			@Test
			 public void CheckConnectedDevicesFrom_ClientMachineTo_Router() throws NetworkException{
				 
				 cloud.start();
			        clientMachine.start();
			        serverMachine.start();
			        router.start();
			        clientMachine.handleConnectionStarted(router);
			       // System.out.println("see somthing" +" "+ clientMachine.connectDevice(router));
			       
			        assertTrue(clientMachine.connectDevice(router));
			        
			   
			 }
				
		
			
			/* 
			
			@Test(timeout = 1000)
			public void ConnectedDevicesTest() throws NetworkException, ParserConfigurationException, SAXException, IOException, NoSuchMethodException  {
			cloud.start();
			clientMachine.start();
			 serverMachine.start();
			 router.start();
			 clientMachine.handleConnectionStarted(router);
		       List<ConnectionEvent> connectedDevices = router.getConnectedDevices();
			 assertEquals("No connection between machines", connectedDevices, router.getConnectedDevices());
			// ClientMachine.stop();
		
		}
		 
			/*
			 * 
			 * 
			
		// Check to see if the BasePackets are actually in the Array
			@Test(timeout = 100)
		    public void CheckStatusOfArray1() throws NetworkException {
				cloud.start();
				clientMachine.start();
				 serverMachine.start();
				 
				ArrayList<DataPacket> PacketArray = clientMachine.PacketArray();
				System.out.println(PacketArray());
				//Timed.getFireCount();
				 assertNotNull("List shouldn't be null", PacketArray);
				 
				} 
			
		 @Test(timeout = 1000)
			public void CheckConnectionStartedTest_ON() throws NetworkException, ParserConfigurationException, SAXException, IOException, NoSuchMethodException  {
			cloud.start();
			clientMachine.start();
			 serverMachine.start();
			 assertEquals("No connection between machines", router, clientMachine.handleConnectionStarted(router));
			// ClientMachine.stop();
		
		} 
		// Transfer array of packets from ClientMachine to serverMachine

		@Test(timeout = 1000)
	    public void TranferPacketArray() throws NetworkException { 
			cloud.start();
			clientMachine.start();
			 serverMachine.start();
			ArrayList<DataPacket> PacketArray = clientMachine.PacketArray();
			long StartTime = Calendar.getInstance().getTimeInMillis();
			long req= Timed.getFireCount();
			//Timed.simulateUntil(1000);
			System.out.println("This simulation began at " + StartTime + "ms in realtime)");
			System.out.println("This simulation began at " + req + "ms in realtime)");
			assertEquals("Packet should not be transferrable", PacketArray, clientMachine.sendPackets(clientMachine, serverMachine, PacketArray));
			clientMachine.stop();
			cloud.stop();
			serverMachine.stop();
			long StopTime = Calendar.getInstance().getTimeInMillis();
			long reb= Timed.getFireCount();
			Timed.simulateUntil(1000);
			System.out.println("This simulation ended at " + StopTime + "ms in realtime)");
			System.out.println("This simulation ended at " + reb + "ms in realtime)");
			long duration = StopTime - StartTime ;
			System.out.println("This simulation took " + duration + "ms in realtime)");
		} 	
	
	
	 	// Checking if a the method is not efficient.		
			 @Test(timeout = 100)	
			 public void SendPacketTest_NotWorking() throws   NetworkException {
		     cloud.start();
			 ClientMachine.start();
			  ServerMachine.start();
			 assertNotEquals("Packet was not delivered", false, ClientMachine.sendPacket(ClientMachine, ServerMachine, P1));
				}	
					 
	//Checking if the method is efficient.
		 @Test(timeout = 100)
		 public void BindServerMachineTest_NotWorking()throws NetworkException  {
			 cloud.start();
						 ClientMachine.start();
						 ServerMachine.start();
				    	   assertNotEquals("ServerMachine is was not linked up", false,ClientMachine.bindServerMachine(ServerMachine));
				    				 
					 }
					 
	@Test(timeout = 1000)
		public void conCompleteTest() throws NetworkException {
		 cloud.start();
				ClientMachine.start();
				 ServerMachine.start();
				 assertEquals("Packet Transfer not completed", true, ServerMachine.handleSuccess(ServerMachine, P1));
				
			} 
			
		
		//Checking if a subscription packet  can be sent to bind the ClientMachine and the ServerMachine
			 @Test(timeout = 100)
			    public void BindServerMachineTest()throws NetworkException  {
			    cloud.start();
				 ClientMachine.start();
				ServerMachine.start();
				assertEquals("ServerMachine is was not linked up", true,ClientMachine.bindServerMachine(ServerMachine));
			}	

		
	
	
	
			
	// Checking if a packet can be sent and registered at a target			
		@Test(timeout = 100)	
			public void SendPacketTest() throws   NetworkException {
				cloud.start();
				ClientMachine.start();
				ServerMachine.start();
				assertEquals("Packet was not delivered", true, ClientMachine.sendPacket(ClientMachine, ServerMachine, P1));
				 }
				
		    
					 	

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

	
	
	
		
