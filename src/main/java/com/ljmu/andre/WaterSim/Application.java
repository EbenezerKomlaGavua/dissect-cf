package com.ljmu.andre.WaterSim;

import com.ljmu.andre.FitbitSim.Scenario;
import com.ljmu.andre.WaterSim.Devices.Device;
import com.ljmu.andre.WaterSim.Devices.Sensor;
import com.ljmu.andre.WaterSim.Packets.SubscriptionPacket;
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
import hu.mta.sztaki.lpds.cloud.simulator.io.Repository;

/**
 * Created by Andre on 30/03/2017.
 */
public class Application {
    public static final String USER_DIR = System.getProperty("user.dir");
    private static final Logger logger = new Logger(Application.class);
    private static final String SIMULATION_MODEL_XML = USER_DIR + "/" + "Simulation_Model.xml";
    private static Application instance;

    private List<Device> devices = new ArrayList<Device>();
    private List<DeviceModel> deviceModels;

    public static int totalPackets = 0;

    private Application() {

    }

    private void startSim() throws IOException, SAXException, ParserConfigurationException {
        for(Device device : devices)
            device.start();

        Timed.simulateUntilLastEvent();
        logger.log("Total packets sent: " + totalPackets);
    }

    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
        getInstance().loadSimData();
        getInstance().startSim();
    }

    private void loadSimData() {
        try {
            MachineHandler.init(Scenario.MACHINE_XML_PATH);

            JAXBContext jc = JAXBContext.newInstance(SimulationModel.class);

            Unmarshaller unmarshaller = jc.createUnmarshaller();

            File file = new File(SIMULATION_MODEL_XML);
            SimulationModel model = (SimulationModel) unmarshaller.unmarshal(file);
            Map<String, Device> deviceMap = new HashMap<String, Device>();

            for (DeviceModel deviceModel : model.deviceModels) {
                Device device = null;

                if (deviceModel instanceof SensorModel)
                    device = ((SensorModel) deviceModel).createSensor();
                else if(deviceModel instanceof GatewayModel)
                    device = ((GatewayModel) deviceModel).createGateway();
                else if(deviceModel instanceof ServerModel)
                    device = ((ServerModel) deviceModel).createServer();

                if(device != null) {
                    for(Connection connection : deviceModel.connections) {
                        logger.log("Connection [Target: %s][Latency: %s]", connection.connectionId, connection.latency);
                        device.getRepository().addLatency(connection.connectionId, connection.latency);
                    }

                    devices.add(device);
                    deviceMap.put(device.getId(), device);
                }
            }

            // Connect the devices after they're initialised \\
            for(DeviceModel deviceModel : model.deviceModels) {
                Device device = deviceMap.get(deviceModel.id);
                logger.log("IsDeviceNull? " + (device == null));

                for(Connection connection : deviceModel.connections) {
                    Device connectedDevice = deviceMap.get(connection.connectionId);
                    device.connectDevice(connectedDevice);
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
}
