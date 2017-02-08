package com.ljmu.andre.FitbitSim.Packets;

/**
 * Created by Andre on 07/02/2017.
 */
public class ActivityPacket extends BasicPacket {
    private int stepCount;
    private int caloriesBurnt;
    private int distanceWalked;
    private int minutesActive;

    public ActivityPacket(String myid, long mysize) {
        super(myid, mysize, false);
    }

    public int getStepCount() {
        return stepCount;
    }

    public ActivityPacket setStepCount(int stepCount) {
        this.stepCount = stepCount;
        return this;
    }

    public int getCaloriesBurnt() {
        return caloriesBurnt;
    }

    public ActivityPacket setCaloriesBurnt(int caloriesBurnt) {
        this.caloriesBurnt = caloriesBurnt;
        return this;
    }

    public int getDistanceWalked() {
        return distanceWalked;
    }

    public ActivityPacket setDistanceWalked(int distanceWalked) {
        this.distanceWalked = distanceWalked;
        return this;
    }

    public int getMinutesActive() {
        return minutesActive;
    }

    public ActivityPacket setMinutesActive(int minutesActive) {
        this.minutesActive = minutesActive;
        return this;
    }
}
