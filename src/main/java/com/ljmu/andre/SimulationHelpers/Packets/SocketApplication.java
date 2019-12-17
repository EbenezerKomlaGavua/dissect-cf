package com.ljmu.andre.SimulationHelpers.Packets;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

//import com.ljmu.andre.SimulationHelpers.Application;
import com.ljmu.andre.SimulationHelpers.Device;
import com.ljmu.andre.SimulationHelpers.MachineHandler;
import com.ljmu.andre.SimulationHelpers.Utils.Logger;
import com.ljmu.andre.SimulationHelpers.XMLModels.DeviceModel;
import com.ljmu.andre.SimulationHelpers.XMLModels.SimulationModel;

import hu.mta.sztaki.lpds.cloud.simulator.Timed;

public class SocketApplication {
	
	
	
	private static final Logger logger = new Logger(SocketApplication.class);
    private static int totalPackets = 0;
    private static int successfulPackets = 0;
    private static int failedPackets = 0;
    private static SocketApplication instance;
    private static boolean isInitialised;
    private static List<Device> devices = new ArrayList<Device>();


    /**
     * Load the Simulation Data such as Machine Data and Device Models
     */
    public static void loadSimData(String machine_xml, String simulation_model_xml) {
    	 try {
             // Initialise the Machine Handler \\
    		 MachineHandler_Socket.init(machine_xml);

             // Begin unmarshalling of the Simulation XML \\
             JAXBContext jc = JAXBContext.newInstance(SimulationModel.class);
             Unmarshaller unmarshaller = jc.createUnmarshaller();

             File file = new File(simulation_model_xml);

             // Convert the XML file to the representing SimulationModel object
             SimulationModel model = (SimulationModel) unmarshaller.unmarshal(file);
             logger.log("Loading %s Devices", model.deviceModels.size());

             // A cache used to quickly access loaded devices \\
             Map<String, Device> deviceMap = new HashMap<String, Device>();

             // Iterate through the Device Models \\
             for (DeviceModel deviceModel : model.deviceModels) {
                 // If the DeviceModel ID has already been used, throw an exception \\
                 if (deviceMap.containsKey(deviceModel.id))
                     throw new IllegalArgumentException(String.format("Device [ID: %s] already in use!", deviceModel.id));

                 // Create the appropriate Device Type based on the DeviceModel type \\
                 Device device = deviceModel.generateDevice();

                 // Add the device to the system so that it can be connected \\
                 devices.add(device);
                 deviceMap.put(device.getId(), device);
             }

             // Connect the devices after they're initialised \\
             for (Device device : devices) {
                 // Get the Device created from the DeviceModel \\
                 for (String connectedID : device.getRepository().getLatencies().keySet()) {
                     Device connectedDevice = deviceMap.get(connectedID);
                     logger.log("Connecting %s to %s", device.getId(), connectedID);

                     if (connectedDevice != null)
                         device.connectDevice(connectedDevice);
                     else {
                         logger.err("Couldn't connect devices [Source: %s] [Target: %s]",
                                 device.getId(), connectedID);
                     }
                 }
             }

             isInitialised = true;
         } catch (JAXBException e) {
             e.printStackTrace();
         } catch (ParserConfigurationException e) {
             e.printStackTrace();
         } catch (IOException e) {
             e.printStackTrace();
         } catch (SAXException e) {
             e.printStackTrace();
         }
     }
    
    public static void packetTransaction(boolean successful) {
        if(successful)
            successfulPackets++;
        else
            failedPackets++;

        totalPackets++;
    }
    
    
    /**
     * Start the simulation
     */
    public static void startSim() {
        initCheck();
        // Subscribe all the built devices \\
        for (Device device : devices)
            device.start();

        // Begin the simulation until all devices are unsubscribed
        Timed.simulateUntilLastEvent();

        // Print the total number of packets that were sent \\
        logger.log("Simulation Packet Stats [Total: %s] [Successful: %s] [Failed: %s]",
                totalPackets, successfulPackets, failedPackets);
    }

    public static void initCheck() {
        if(!isInitialised)
            throw new IllegalStateException("SocketApplication is not initialised! Load the Simulation Model first!");
    }
   
	public static SocketApplication getInstance() {
		 if (instance == null)
	            instance = new SocketApplication();

	        logger.log("Starting SocketApplication");
	        return instance;
	}
}
