package com.ljmu.andre.FitbitSim;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;

import org.xml.sax.SAXException;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import hu.mta.sztaki.lpds.cloud.simulator.iaas.IaaSService;
import hu.mta.sztaki.lpds.cloud.simulator.iaas.IaaSService.IaaSHandlingException;
import hu.mta.sztaki.lpds.cloud.simulator.iaas.PhysicalMachine;
import hu.mta.sztaki.lpds.cloud.simulator.iaas.constraints.ConstantConstraints;
import hu.mta.sztaki.lpds.cloud.simulator.io.VirtualAppliance;
import hu.mta.sztaki.lpds.cloud.simulator.util.CloudLoader;

/**
 * Created by Andre on 02/02/2017.
 */
class Cloud {
    private static IaaSService iaaSService;
    private static boolean isInitialized = false;
    private static VirtualAppliance virtualAppliance;

    private Cloud() {
    }

    static void init(@NotNull String CLOUD_XML_PATH) throws ParserConfigurationException, SAXException, IOException {
        iaaSService = CloudLoader.loadNodes(CLOUD_XML_PATH);
        isInitialized = true;
    }

    static void initVMs() {
        virtualAppliance = new VirtualAppliance("FitbitServ", 1000, 0);
    }

    static boolean deregisterHost(PhysicalMachine pMachine) {
        try {
            iaaSService.deregisterHost(pMachine);
            iaaSService.deregisterRepository(pMachine.localDisk);

            System.out.println("Cloud Machines: " + iaaSService.machines.size());
            return true;
        } catch (IaaSHandlingException e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * @param pmName - The name of the machine - Case Insensitive
     * @return PhysicalMachine if found / Null if not found
     */
    @Nullable static PhysicalMachine findPM(String pmName) {
        initCheck();
        pmName = pmName.toLowerCase();

        for (PhysicalMachine physicalMachine : iaaSService.machines) {
            System.out.println("Checking PM: " + physicalMachine.localDisk.getName());
            if (physicalMachine.localDisk.getName().toLowerCase().equals(pmName))
                return physicalMachine;
        }

        return null;
    }


    private static void initCheck() {
        if (!isInitialized)
            throw new IllegalStateException("Cloud is not initialized!");
    }
}
