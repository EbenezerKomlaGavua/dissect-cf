package com.ljmu.andre.FitbitSim.Models;

import com.sun.istack.internal.NotNull;

import java.io.Serializable;

/**
 * Created by Andre on 26/01/2017.
 */
@Deprecated
public class GenericModel implements Serializable {
    @NotNull private String id;

    @NotNull private long simDuration;
    @NotNull private long startTime;
    @NotNull private long stopTime;
    @NotNull private long frequency;

    @NotNull private int fileSize;

    public GenericModel(
            @NotNull String id,
            @NotNull long simDuration,
            @NotNull long startTime,
            @NotNull long stopTime,
            @NotNull long frequency,
            @NotNull int fileSize) {
        this.id = id;
        this.simDuration = simDuration;
        this.startTime = startTime;
        this.stopTime = stopTime;
        this.frequency = frequency;
        this.fileSize = fileSize;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    /**
     * Initially I thought a Builder Pattern would be better
     * However I'll probably scrap this in place for getters/setters
     */
    public static class Builder {
        private String id;

        private long simDuration;
        private long startTime;
        private long stopTime;
        private long frequency;

        private int fileSize;

        public Builder() {

        }

        private Builder(GenericModel model) {
            this.id = model.id;
            this.simDuration = model.simDuration;
            this.startTime = model.startTime;
            this.stopTime = model.stopTime;
            this.frequency = model.frequency;
            this.fileSize = model.fileSize;
        }

        public Builder setId(String id) {
            this.id = id;
            return this;
        }

        public Builder setSimDuration(long simDuration) {
            this.simDuration = simDuration;
            return this;
        }

        public Builder setStartTime(long startTime) {
            this.startTime = startTime;
            return this;
        }

        public Builder setStopTime(long stopTime) {
            this.stopTime = stopTime;
            return this;
        }

        public Builder setFrequency(long frequency) {
            this.frequency = frequency;
            return this;
        }

        public Builder setFileSize(int fileSize) {
            this.fileSize = fileSize;
            return this;
        }

        public GenericModel build() {
            return new GenericModel(
                    id,
                    simDuration,
                    startTime,
                    stopTime,
                    frequency,
                    fileSize
            );
        }
    }
}
