package com.ljmu.andre.SimulationHelpers.XMLModels;

import com.ljmu.andre.SimulationHelpers.SimulationTraceProducer;

import javax.xml.bind.annotation.XmlElement;

import hu.mta.sztaki.lpds.cloud.simulator.helpers.trace.GenericTraceProducer;

/**
 * Created by Andre on 09/04/2017.
 */
public class TraceProducerModel {
    @XmlElement(name="TargetDevice")
    String deviceID;

    @XmlElement(name="Distributions")
    Distributions distributions;

    @XmlElement(name="MaxPacketSize")
    public int maxPacketSize;

    @XmlElement(name="MaxJobDistance")
    public int maxJobDistance;

    @XmlElement(name="JobCount")
    public int jobCount = -1;

    @XmlElement(name="StorePackets")
    public boolean shouldSave = false;

    public GenericTraceProducer generateProducer(int simFrom, int simTo, String source) {
        try {
            return new SimulationTraceProducer(source, deviceID,
                    simFrom, simTo, jobCount, shouldSave,
                    distributions.sizeModel.generateDistribution(), maxPacketSize,
                    distributions.gapModel.generateDistribution(), maxJobDistance);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        return null;
    }


    public static class Distributions {
        @XmlElement(name="Size")
        public DistributionModel sizeModel;

        @XmlElement(name="Gap")
        public DistributionModel gapModel;
    }
}
