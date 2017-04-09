package com.ljmu.andre.SimulationHelpers.XMLModels;

import com.ljmu.andre.SimulationHelpers.Application;
import com.ljmu.andre.SimulationHelpers.Device;
import com.ljmu.andre.SimulationHelpers.SimulationFileReader;

import javax.xml.bind.annotation.XmlElement;

import hu.mta.sztaki.lpds.cloud.simulator.helpers.trace.GenericTraceProducer;

/**
 * Created by Andre on 30/03/2017.
 */
public class DeviceModel {
    @XmlElement(name="ID")
    public String id;

    @XmlElement(name="TraceFileReader")
    public TraceFileReaderModel fileReaderModel;

    @XmlElement(name="TraceProducer")
    public TraceProducerModel traceProducerModel;

    public Device generateDevice() {
        GenericTraceProducer traceProducer = null;

        if(fileReaderModel != null)
            traceProducer = fileReaderModel.generateFileReader();

        if(traceProducer == null && traceProducerModel != null)
            traceProducer = traceProducerModel.generateProducer(id);

        return new Device(id, traceProducer);
    }
}
