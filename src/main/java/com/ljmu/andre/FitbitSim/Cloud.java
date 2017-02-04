package com.ljmu.andre.FitbitSim;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;

import org.xml.sax.SAXException;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import hu.mta.sztaki.lpds.cloud.simulator.iaas.IaaSService;
import hu.mta.sztaki.lpds.cloud.simulator.iaas.PhysicalMachine;
import hu.mta.sztaki.lpds.cloud.simulator.util.CloudLoader;

/**
 * Created by Andre on 02/02/2017.
 */
public class Cloud {
    private static String CLOUD_XML_PATH;
    private static IaaSService iaaSService;
    private static boolean isInitialized = false;

    private Cloud() {}

    public static void init(@NotNull String CLOUD_XML_PATH) throws ParserConfigurationException, SAXException, IOException {
        Cloud.CLOUD_XML_PATH = CLOUD_XML_PATH;
        iaaSService = CloudLoader.loadNodes(CLOUD_XML_PATH);
        isInitialized = true;
    }


    /**
     *
     * @param pmName - The name of the machine, will append "_Repo"
     *               as repositories names are the only available identifiers
     * @return PhysicalMachine if found / Null if not found
     */
    @Nullable public static PhysicalMachine findPM(String pmName) {
        initCheck();
        String appendedName = pmName + "_Repo";

        for(PhysicalMachine physicalMachine : iaaSService.machines) {
            System.out.println("PhysicalMachine: "+ physicalMachine.localDisk.getName());
            if(physicalMachine.localDisk.getName().equals(appendedName))
                return physicalMachine;
        }

        return null;
    }


    private static void initCheck() {
        if(!isInitialized)
            throw new IllegalStateException("Cloud is not initialized!");
    }
}
