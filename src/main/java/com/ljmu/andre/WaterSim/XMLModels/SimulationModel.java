package com.ljmu.andre.WaterSim.XMLModels;

import com.ljmu.andre.WaterSim.Devices.Device;
import com.ljmu.andre.WaterSim.Devices.Sensor;
import com.ljmu.andre.WaterSim.Devices.Server;

import java.util.List;

import javax.xml.bind.annotation.*;

/**
 * Created by Andre on 30/03/2017.
 */
@XmlRootElement(name="Simulation")
@XmlAccessorType(XmlAccessType.FIELD)
public class SimulationModel {

    @XmlElements({
            @XmlElement(name="Sensor", type=SensorModel.class),
            @XmlElement(name="Gateway", type=GatewayModel.class),
            @XmlElement(name="Server", type=ServerModel.class)
    })
    @XmlElementWrapper(name="Devices")
    public List<DeviceModel> deviceModels;

    /*@XmlElementWrapper(name="Gateways")
    @XmlElement(name="Gateway")
    public List<GatewayModel> gatewayModels;

    @XmlElementWrapper(name="Servers")
    @XmlElement(name="Server")
    public List<ServerModel> serverModels;*/
}
