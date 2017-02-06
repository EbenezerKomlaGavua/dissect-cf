package com.ljmu.andre.FitbitSim;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ljmu.andre.FitbitSim.DataStores.SimulationData;
import com.ljmu.andre.FitbitSim.DataStores.SmartphoneData;
import com.ljmu.andre.FitbitSim.DataStores.WatchData;
import com.sun.istack.internal.Nullable;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import hu.mta.sztaki.lpds.cloud.simulator.iaas.PhysicalMachine;

/**
 * Created by Andre on 06/02/2017.
 */
public class LoaderUtils {
    private static Gson gson;

    private static Gson getGson() {
        if (gson == null) {
            gson = new GsonBuilder()
                    .setPrettyPrinting()
                    .create();
        }

        return gson;
    }

    public static SimulationData getSimDataFromJson(String jsonPath) throws FileNotFoundException {
        File simJsonFile = new File(jsonPath);

        if (!simJsonFile.exists())
            throw new FileNotFoundException("Could not find json file: " + jsonPath);

        FileReader reader = new FileReader(simJsonFile);

        try {
            return getGson().fromJson(reader, SimulationData.class);
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static List<Watch> getWatchListFromJson(String jsonPath, @Nullable Smartphone bindableSmartphone) throws FileNotFoundException {
        File watchJsonFile = new File(jsonPath);

        if (!watchJsonFile.exists())
            throw new FileNotFoundException("Could not find json file: " + jsonPath);

        FileReader reader = new FileReader(watchJsonFile);
        WatchData[] watchDataList = getGson().fromJson(reader, WatchData[].class);
        try {
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<Watch> watchList = new ArrayList<Watch>();

        for (WatchData watchData : watchDataList) {
            if (watchData.getSimDuration() == -1)
                watchData.setSimDuration(Integer.MAX_VALUE);

            PhysicalMachine watchMachine = Cloud.findPM(watchData.getId());

            if (watchMachine == null) {
                NullPointerException npe = new NullPointerException("WatchMachine not assigned to data: " + watchData.getId());
                npe.printStackTrace();
                continue;
            }

            Watch watch = new Watch(watchMachine, watchData);

            if (bindableSmartphone != null) {
                bindableSmartphone.addWatch(watch);
                watch.bindSmartphone(bindableSmartphone);
            }

            watchList.add(watch);
        }

        return watchList;
    }

    public static Smartphone getPhoneFromJson(String jsonPath) throws FileNotFoundException {
        File phoneJsonFile = new File(jsonPath);
        System.out.println("Smartphone file: " + jsonPath);

        if (!phoneJsonFile.exists())
            throw new FileNotFoundException("Could not find json file: " + jsonPath);

        FileReader reader = new FileReader(phoneJsonFile);
        SmartphoneData smartphoneData = getGson().fromJson(reader, SmartphoneData.class);
        try {
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        PhysicalMachine phoneMachine = Cloud.findPM(smartphoneData.getId());
        if (phoneMachine == null)
            throw new NullPointerException("SmartphoneMachine not assigned to data: " + smartphoneData.getId());

        return new Smartphone(phoneMachine, smartphoneData);
    }
}
