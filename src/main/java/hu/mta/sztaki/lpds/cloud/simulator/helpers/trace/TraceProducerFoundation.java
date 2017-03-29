/*
 *  ========================================================================
 *  Helper classes to support simulations of large scale distributed systems
 *  ========================================================================
 *  
 *  This file is part of DistSysJavaHelpers.
 *  
 *    DistSysJavaHelpers is free software: you can redistribute it and/or modify
 *    it under the terms of the GNU General Public License as published by
 *    the Free Software Foundation, either version 3 of the License, or
 *    (at your option) any later version.
 *
 *   DistSysJavaHelpers is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 *  (C) Copyright 2016, Gabor Kecskemeti (g.kecskemeti@ljmu.ac.uk)
 *  (C) Copyright 2012-2015, Gabor Kecskemeti (kecskemeti.gabor@sztaki.mta.hu)
 */

package hu.mta.sztaki.lpds.cloud.simulator.helpers.trace;

import java.lang.reflect.Constructor;

import hu.mta.sztaki.lpds.cloud.simulator.helpers.job.Job;

/**
 * Generic foundation for job trace producers. Any extension of this abstract
 * class should be able to provide a set of jobs at request (either with fixed
 * length or some arbitrary length derived from the particular implementation's
 * context).
 * 
 * @author "Gabor Kecskemeti, Department of Computer Science, Liverpool John
 *         Moores University, (c) 2016"
 * @author "Gabor Kecskemeti, Laboratory of Parallel and Distributed Systems,
 *         MTA SZTAKI (c) 2015"
 * 
 */
public abstract class TraceProducerFoundation implements GenericTraceProducer {
	protected long maxProcCount = -1;
	/**
	 * The constructor of the Job implementation to be used during the
	 * generation process.
	 * 
	 * The class is implemented to use a constructor with the following
	 * signature: Job(String, long, long, long, int, double, long, String,
	 * String, String, Job, long). For details see the implementation of the Job
	 * class.
	 */
	protected final Constructor<? extends Job> jobCreator;

	/**
	 * Basic constructor of the class. This constructor only ensures that the
	 * jobType in question can be used with the kind of constructor required to
	 * instantiate the jobs of this producer.
	 * 
	 * @param jobType
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 */
	public TraceProducerFoundation(final Class<? extends Job> jobType) throws NoSuchMethodException, SecurityException {
		jobCreator = jobType.getConstructor(String.class, long.class, long.class, long.class, int.class, double.class,
				long.class, String.class, String.class, String.class, Job.class, long.class);
	}

	/**
	 * By default, this function tells the system that the proc count is
	 * unknown. Update the maxProcCount filed if you can determine the proc
	 * count somehow.
	 */
	@Override
	public long getMaxProcCount() {
		return maxProcCount;
	}
}