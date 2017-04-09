package com.ljmu.andre.SimulationHelpers.XMLModels;

import com.ljmu.andre.SimulationHelpers.Application;
import com.ljmu.andre.SimulationHelpers.Device;
import com.ljmu.andre.SimulationHelpers.SimulationFileReader;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import javax.xml.bind.annotation.XmlElement;

import hu.mta.sztaki.lpds.cloud.simulator.helpers.trace.GenericTraceProducer;

/**
 * Created by Andre on 30/03/2017.
 */
public class DeviceModel {
    @XmlElement(name="CustomDevice")
    public String deviceClass;

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

        if(deviceClass == null)
            return new Device(id, traceProducer);

        try {
            Class<? extends Device> customDeviceClass = (Class<? extends Device>) Class.forName(deviceClass);
            Constructor<? extends Device> constructor = customDeviceClass.getConstructor(String.class, GenericTraceProducer.class);
            return constructor.newInstance(id, traceProducer);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}
