package com.ljmu.andre.WaterSim.XMLModels;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by Andre on 30/03/2017.
 */
public class DeviceModel {
    @XmlElement(name="ID")
    public String id;

    @XmlElement(name="MachineID")
    public String machineId;

    @XmlElement(name="SimulationFilePath")
    public String simFilePath;

    @XmlElement(name="SimulationFileName")
    public String simFileName;

    @XmlElement(name="SimulateFrom")
    public int simFrom;

    @XmlElement(name="SimulateTo")
    public int simTo = -1;

    @XmlElement(name="Connection")
    public List<Connection> connections = new ArrayList<Connection>();


    public static class Connection {
        @XmlElement(name="TargetDevice")
        public String deviceId;
        @XmlElement(name="Latency")
        public Integer latency;
    }
}
