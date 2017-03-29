package com.ljmu.andre.FitbitSim;

import com.ljmu.andre.FitbitSim.Utils.Logger;

/**
 * Created by Andre on 24/01/2017.
 */
public class main {
    private static final Logger logger = new Logger(main.class);
    public static Scenario scenario;

    public static void main(String[] args) {
        try {
            scenario = new Scenario();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
