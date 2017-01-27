package com.ljmu.andre.FitbitSim;

/**
 * Created by Andre on 24/01/2017.
 */
public class main {
    public static Scenario scenario;

    public static void main(String[] args) {
        try {
            scenario = new Scenario();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
