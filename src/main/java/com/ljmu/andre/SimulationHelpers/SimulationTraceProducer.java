package com.ljmu.andre.SimulationHelpers;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import hu.mta.sztaki.lpds.cloud.simulator.helpers.Chartable;
import hu.mta.sztaki.lpds.cloud.simulator.helpers.job.Job;
import hu.mta.sztaki.lpds.cloud.simulator.helpers.trace.TraceManagementException;
import hu.mta.sztaki.lpds.cloud.simulator.helpers.trace.random.DistributionSpecifier;
import hu.mta.sztaki.lpds.cloud.simulator.helpers.trace.random.GenericRandomTraceGenerator;
import hu.mta.sztaki.lpds.cloud.simulator.helpers.trace.random.SimpleRandomTraceGenerator;

/**
 * Created by Andre on 09/04/2017.
 */
public class SimulationTraceProducer extends GenericRandomTraceGenerator implements Chartable {
    private final String source, target;

    /**
     * The generators that comply with the given distribution functions
     */
    private final DistributionSpecifier sizeDistribution, distanceDistribution;
    private final int maxPacketSize;
    /**
     * The maximum distance between the start time of two jobs
     */
    private final int maxJobDistance;

    /**
     * The submission time used for the currently generated job (this is always
     * updated once a job is generated
     */
    private long currentSubmitTime = 0;
    private long simTo;
    private int jobCount;
    private boolean shouldSave;

    public SimulationTraceProducer(String source, String target,
                                   long simFrom, long simTo, int jobCount, boolean shouldSave,
                                   DistributionSpecifier size, final int maxPacketSize,
                                   DistributionSpecifier gap, final int maxJobDistance) throws NoSuchMethodException, SecurityException {
        super(Job.class);

        this.source = source;
        this.target = target;
        this.currentSubmitTime = simFrom;
        this.simTo = simTo;
        this.jobCount = jobCount;
        this.shouldSave = shouldSave;
        this.maxPacketSize = maxPacketSize;
        this.maxJobDistance = maxJobDistance;
        if (!size.isFinalized()) {
            size.finalizeDistribution();
        }
        if (!gap.isFinalized()) {
            gap.finalizeDistribution();
        }
        sizeDistribution = size;
        distanceDistribution = gap;

        System.out.println("Distance: " + distanceDistribution.toCSV());
    }

    @Override public String toCSV() {
        return null;
    }

    /**
     * the main job generator function, ensures that no jobs overload the
     * infrastructure (adds an upper cap of resource utilization equalling to
     * getMaxTotalProcs())
     * <p>
     * Also follows the distribution function specified for the number of
     * processors.
     * <p>
     * The rest of the parameters for each job are chosen with a uniform random
     * number generator
     */
    @Override
    protected List<Job> generateJobs() throws TraceManagementException {
        try {
            setJobNum(0);
            System.err.println("Simulation Trace Generator parameters: jobnum - " + getJobNum() + " totprocs - "
                    + getMaxTotalProcs());
            final ArrayList<Job> generatedList = new ArrayList<Job>();

            while((simTo != -1 && currentSubmitTime <= simTo) &&
                    (jobCount != -1 && generatedList.size() < jobCount)) {
                long val = (long) (distanceDistribution.nextDouble() * (double) maxJobDistance);
                System.out.println("Val: " + val);

                currentSubmitTime += (long) (distanceDistribution.nextDouble() * (double) maxJobDistance);
                System.out.println("Submit: " + currentSubmitTime + " | " + distanceDistribution.nextDouble());
                int packetSize = (int) (sizeDistribution.nextDouble() * maxPacketSize);
                Job j = getJobInstance(source, target, packetSize);
                generatedList.add(j);
            }
            return generatedList;
        } catch (Exception e) {
            throw new TraceManagementException("Could not generate jobs", e);
        }
    }

    private Job getJobInstance(String source, String target, int packetSize)
            throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        return new NetworkJob(null, source, target, currentSubmitTime, packetSize, shouldSave, null, null, null);
    }
}
