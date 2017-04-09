package com.ljmu.andre.FitbitSimulation;

import com.ljmu.andre.SimulationHelpers.Application;

/**
 * Created by Andre on 09/04/2017.
 */
public class main {
    public static void main(String[] args) {
        Application application = Application.getInstance();

        application.loadSimData(args[0], args[1]);
        application.startSim();
    }
}
