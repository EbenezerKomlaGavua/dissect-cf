package com.ljmu.andre.FitbitSim;

import com.ljmu.andre.FitbitSim.Utils.Logger;
import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import hu.mta.sztaki.lpds.cloud.simulator.iaas.IaaSService;
import hu.mta.sztaki.lpds.cloud.simulator.iaas.IaaSService.IaaSHandlingException;
import hu.mta.sztaki.lpds.cloud.simulator.iaas.PhysicalMachine;
import hu.mta.sztaki.lpds.cloud.simulator.iaas.PhysicalMachine.State;
import hu.mta.sztaki.lpds.cloud.simulator.iaas.PhysicalMachine.StateChangeListener;
import hu.mta.sztaki.lpds.cloud.simulator.io.VirtualAppliance;
import hu.mta.sztaki.lpds.cloud.simulator.util.CloudLoader;

/**
 * Created by Andre on 02/02/2017.
 */
class Cloud {
    private static final Logger logger = new Logger(Cloud.class);
    private static IaaSService iaaSService;
    private static boolean isInitialized = false;
    private static boolean hasClearedNonCloudPMs = false;
    private static VirtualAppliance virtualAppliance;

    private Cloud() {
    }

    static void init(@NotNull String CLOUD_XML_PATH) throws ParserConfigurationException, SAXException, IOException {
        iaaSService = CloudLoader.loadNodes(CLOUD_XML_PATH);
        isInitialized = true;
    }

    static void clearNonCloudMachines(List<PhysicalMachine> machines) {
        bulkHostDeregister(machines);
        hasClearedNonCloudPMs = true;
    }

    private static void bulkHostDeregister(List<PhysicalMachine> machines) {
        for (PhysicalMachine machine : machines)
            deregisterHost(machine);
    }

    private static boolean deregisterHost(PhysicalMachine pMachine) {
        initCheck();

        try {
            iaaSService.deregisterHost(pMachine);
            iaaSService.deregisterRepository(pMachine.localDisk);

            logger.log("Cloud Machines: " + iaaSService.machines.size());
            return true;
        } catch (IaaSHandlingException e) {
            e.printStackTrace();
        }

        return false;
    }

    private static void initCheck() {
        if (!isInitialized)
            throw new IllegalStateException("Cloud is not initialized!");
    }

    static void initVMs() {
        PhysicalMachine pm = iaaSService.machines.get(0);
        logger.log("PM: " + pm.localDisk.getName() + " State: " + pm.getState());

        pm.subscribeStateChangeEvents(new StateChangeListener() {
            @Override public void stateChanged(PhysicalMachine pm, State oldState, State newState) {
                logger.log("State Change: " + oldState + " to " + newState);
            }
        });
    }

    /*@Nullable public static VirtualMachine getVM() {
        VirtualAppliance virtualAppliance = new VirtualAppliance("VA", 1000, 0, false, pm.localDisk.getFreeStorageCapacity() / 2);
        pm.localDisk.registerObject(virtualAppliance);
        try {
            VirtualMachine vm = iaaSService.requestVM(virtualAppliance, pm.getCapacities(), pm.localDisk, 1, null)[0];
            System.out.println("VM: " + vm.toString());
            System.out.println("RA: " + vm.getResourceAllocation());
            vm.switchOn(null, pm.localDisk);
            return vm;
        } catch (VMManagementException e) {
            e.printStackTrace();
        } catch (NetworkException e) {
            e.printStackTrace();
        }

        return null;
    }*/

    /**
     * @param pmName - The name of the machine - Case Insensitive
     * @return PhysicalMachine if found / Null if not found
     */
    @Nullable static PhysicalMachine findPM(String pmName) {
        initCheck();
        pmName = pmName.toLowerCase();

        for (PhysicalMachine physicalMachine : iaaSService.machines) {
            logger.log("Checking PM: " + physicalMachine.localDisk.getName());
            if (physicalMachine.localDisk.getName().toLowerCase().equals(pmName))
                return physicalMachine;
        }

        return null;
    }

    private static void clearedUnrelatedCheck() {
        if (!hasClearedNonCloudPMs)
            throw new IllegalStateException("Non Cloud machines not cleared!");
    }
}
