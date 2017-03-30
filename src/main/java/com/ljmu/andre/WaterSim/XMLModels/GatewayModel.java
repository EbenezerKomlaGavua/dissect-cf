package com.ljmu.andre.WaterSim.XMLModels;

import com.ljmu.andre.WaterSim.Application;
import com.ljmu.andre.WaterSim.Devices.Gateway;
import com.ljmu.andre.WaterSim.Devices.Sensor;
import com.ljmu.andre.WaterSim.SimulationFileReader;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

/**
 * Created by Andre on 30/03/2017.
 */

@XmlAccessorType(XmlAccessType.FIELD)
public class GatewayModel extends DeviceModel {
    public Gateway createGateway() {
        if(simFilePath == null && simFileName != null)
            simFilePath = Application.USER_DIR + "/" + simFileName;
        SimulationFileReader fileReader = null;

        if(simFilePath != null) {
            if(simTo == -1)
                simTo = Integer.MAX_VALUE;

            try {
                fileReader =
                        new SimulationFileReader("None", simFilePath, simFrom, simTo, false);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }

        return new Gateway(id, machineId, fileReader);
    }
}
