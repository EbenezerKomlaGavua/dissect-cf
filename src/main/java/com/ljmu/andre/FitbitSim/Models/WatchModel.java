package com.ljmu.andre.FitbitSim.Models;

import com.sun.istack.internal.NotNull;

import java.io.Serializable;

/**
 * Created by Andre on 26/01/2017.
 */
public class WatchModel extends GenericModel implements Serializable {
    public WatchModel(
            @NotNull String id,
            @NotNull long simDuration,
            @NotNull long startTime,
            @NotNull long stopTime,
            @NotNull long frequency,
            @NotNull int fileSize) {
        super(id, simDuration, startTime, stopTime, frequency, fileSize);
    }
}
