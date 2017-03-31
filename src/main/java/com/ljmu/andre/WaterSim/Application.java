package com.ljmu.andre.WaterSim;

import com.ljmu.andre.FitbitSim.Scenario;
import com.ljmu.andre.WaterSim.Devices.Device;
import com.ljmu.andre.WaterSim.Utils.Logger;
import com.ljmu.andre.WaterSim.XMLModels.DeviceModel;
import com.ljmu.andre.WaterSim.XMLModels.DeviceModel.Connection;
import com.ljmu.andre.WaterSim.XMLModels.GatewayModel;
import com.ljmu.andre.WaterSim.XMLModels.SensorModel;
import com.ljmu.andre.WaterSim.XMLModels.ServerModel;
import com.ljmu.andre.WaterSim.XMLModels.SimulationModel;

import org.xml.sax.SAXException;

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

import hu.mta.sztaki.lpds.cloud.simulator.Timed;

/**
 * Created by Andre on 30/03/2017.
 */
public class Application {
    public static final String USER_DIR = System.getProperty("user.dir");
    private static final Logger logger = new Logger(Application.class);
    private static final String SIMULATION_MODEL_XML = USER_DIR + "/" + "Simulation_Model.xml";
    public static int totalPackets = 0;
    private static Application instance;
    private List<Device> devices = new ArrayList<Device>();

    private Application() {
    }

    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
        getInstance().loadSimData();
        getInstance().startSim();
    }

    /**
     * Load the Simulation Data such as Machine Data and Device Models
     */
    private void loadSimData() {
        try {
            // Initialise the Machine Handler \\
            MachineHandler.init(Scenario.MACHINE_XML_PATH);

            // Begin unmarshalling of the Simulation XML \\
            JAXBContext jc = JAXBContext.newInstance(SimulationModel.class);
            Unmarshaller unmarshaller = jc.createUnmarshaller();

            File file = new File(SIMULATION_MODEL_XML);

            // Convert the XML file to the representing SimulationModel object
            SimulationModel model = (SimulationModel) unmarshaller.unmarshal(file);

            // A cache used to quickly access loaded devices \\
            Map<String, Device> deviceMap = new HashMap<String, Device>();

            // Iterate through the Device Models \\
            for (DeviceModel deviceModel : model.deviceModels) {
                // If the DeviceModel ID has already been used, throw an exception \\
                if (deviceMap.containsKey(deviceModel.id))
                    throw new IllegalArgumentException(String.format("Device [ID: %s] already in use!", deviceModel.id));

                // Create the appropriate Device Type based on the DeviceModel type \\
                Device device = null;

                if (deviceModel instanceof SensorModel)
                    device = ((SensorModel) deviceModel).createSensor();
                else if (deviceModel instanceof GatewayModel)
                    device = ((GatewayModel) deviceModel).createGateway();
                else if (deviceModel instanceof ServerModel)
                    device = ((ServerModel) deviceModel).createServer();
                else
                    throw new IllegalArgumentException("Unassigned Device for Model Type: " + deviceModel.getClass().getName());

                // Add the device to the system so that it can be connected \\
                devices.add(device);
                deviceMap.put(device.getId(), device);
            }

            // Connect the devices after they're initialised \\
            for (DeviceModel deviceModel : model.deviceModels) {
                // Get the Device created from the DeviceModel \\
                Device device = deviceMap.get(deviceModel.id);
                if (device == null)
                    throw new IllegalArgumentException(String.format("Device [ID: %s] Not Found!", deviceModel.id));

                // Connect the device to all the Connections described in the model \\
                for (Connection connection : deviceModel.connections) {
                    Device connectedDevice = deviceMap.get(connection.deviceId);

                    // Throw an exception if there is no Device with the connection ID \\
                    if (connectedDevice == null)
                        throw new IllegalArgumentException(String.format("TargetDevice [ID: %s] Not Found!", connection.deviceId));

                    logger.log("Connection [Target: %s][Latency: %s]", connection.deviceId, connection.latency);
                    device.connectDevice(connectedDevice, connection.latency);
                }
            }
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

    public static Application getInstance() {
        if (instance == null)
            instance = new Application();

        logger.log("Starting Application");
        return instance;
    }

    /**
     * Start the simulation
     */
    private void startSim() {
        // Subscribe all the built devices \\
        for (Device device : devices)
            device.start();

        // Begin the simulation until all devices are unsubscribed
        Timed.simulateUntilLastEvent();

        // Print the total number of packets that were sent \\
        logger.log("Total packets sent: " + totalPackets);
    }
}
