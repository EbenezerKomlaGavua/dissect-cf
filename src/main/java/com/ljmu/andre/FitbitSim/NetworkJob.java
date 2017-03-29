package com.ljmu.andre.FitbitSim;

import hu.mta.sztaki.lpds.cloud.simulator.helpers.job.Job;

/**
 * Created by Andre on 29/03/2017.
 */
public class RealJob extends Job {
    /**
     * The generic constructor to be used by most of the trace generators and in
     * most of the use cases.
     * <p>
     * Please note the id of the job is automatically generated with this
     * constructor.
     *
     * @param id         the id of the job, if it is null, then the id will be
     *                   internally assigned.
     * @param submit     the time instance (in secs) the job is submitted
     * @param queue      the duration (in secs) it took for the job to get to
     *                   execution.
     * @param exec       the execution duration of the job on its original resource.
     * @param nprocs     number of processors used by the particular job during its
     *                   execution
     * @param ppCpu
     * @param ppMem
     * @param user       the user who submitted the job
     * @param group
     * @param executable
     * @param preceding
     * @param delayAfter
     */
    public RealJob(String id, long submit, long queue, long exec, int nprocs, double ppCpu, long ppMem, String user, String group, String executable, Job preceding, long delayAfter) {
        super(id, submit, queue, exec, nprocs, ppCpu, ppMem, user, group, executable, preceding, delayAfter);
    }

    @Override public void started() {

    }

    @Override public void completed() {

    }
}
