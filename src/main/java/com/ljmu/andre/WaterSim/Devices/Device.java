package com.ljmu.andre.WaterSim.Devices;

import com.ljmu.andre.WaterSim.Packets.PacketHandler;
import com.ljmu.andre.WaterSim.Interfaces.ConnectionEvent;
import com.ljmu.andre.WaterSim.NetworkJob;
import com.ljmu.andre.WaterSim.MachineHandler;
import com.ljmu.andre.WaterSim.Packets.BasePacket;
import com.ljmu.andre.WaterSim.Packets.RoutingPacket;
import com.ljmu.andre.WaterSim.SimulationFileReader;
import com.ljmu.andre.WaterSim.Utils.Logger;
import com.sun.istack.internal.Nullable;
import com.sun.xml.internal.bind.v2.runtime.reflect.Lister.Pack;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

import hu.mta.sztaki.lpds.cloud.simulator.Timed;
import hu.mta.sztaki.lpds.cloud.simulator.helpers.job.Job;
import hu.mta.sztaki.lpds.cloud.simulator.iaas.PhysicalMachine;
import hu.mta.sztaki.lpds.cloud.simulator.io.Repository;

/**
 * Created by Andre on 30/03/2017.
 */
public abstract class Device extends Timed implements ConnectionEvent {
    private static final Logger logger = new Logger(Device.class);
    private List<ConnectionEvent> connectedDevices = new ArrayList<ConnectionEvent>();

    private String id;

    private String machineID;
    private PhysicalMachine physicalMachine;
    private Repository repository;

    @Nullable private SimulationFileReader simulationFileReader;
    @Nullable List<Job> networkJobs;
    int currentJobNum = 0;

    Device(String id, String machineID, @Nullable SimulationFileReader simulationFileReader) {
        this.id = id;
        this.machineID = machineID;
        this.physicalMachine = MachineHandler.claimPM(machineID);
        this.repository = physicalMachine.localDisk;
        this.simulationFileReader = simulationFileReader;

        if(simulationFileReader != null)
            networkJobs = simulationFileReader.getAllJobs();
    }

    Device(String id, String machineID) {
        this(id, machineID, null);
    }

    public boolean connectDevice(ConnectionEvent device) {
        boolean success = connectedDevices.add(device);
        if(!success)
            logger.log("Device was already connected: " + device.getId());

        return success;
    }

    public boolean disconnectDevice(ConnectionEvent device) {
        boolean success = connectedDevices.remove(device);
        if(!success)
            logger.log("Device was not connected: " + device.getId());

        return success;
    }

    public boolean sendPacket(String targetID, BasePacket packet) {
        logger.log("Sending to: " + targetID);

        for(ConnectionEvent device : connectedDevices) {
            logger.log("Checking Device: " + device.getId());

            if(device.getId().equals(targetID))
                return PacketHandler.sendPacket(this, device, packet);
        }

        logger.log("Device not direct link");
        return PacketHandler.sendRoutedPacket(this, targetID, packet);
    }

    public boolean forwardRoutingPacket(RoutingPacket routingPacket) {
        return PacketHandler.sendPacket(this, routingPacket.getNextTarget(), routingPacket);
    }

    public boolean forwardRoutingPacket(RoutingPacket routingPacket, ConnectionEvent target) {
        return PacketHandler.sendPacket(this, target, routingPacket);
    }

    public void start() {
        logger.log("Started [ID: %s] [Success: %s]", id, subscribe(100));
    }

    public void stop() {
        logger.log("Stopped [ID: %s] [Success: %s]", id, unsubscribe());
    }

    public String getId() {
        return id;
    }

    public PhysicalMachine getPhysicalMachine() {
        return physicalMachine;
    }

    public void setRepo(Repository repo) {
        this.repository = repo;
    }
    @Override public Repository getRepository() {
        return repository;
    }

    @Override public List<ConnectionEvent> getConnectedDevices() {
        return connectedDevices;
    }

    @Override public void connectionStarted(ConnectionEvent source) {
        logger.log("Starting connection: " + source.getId());
    }

    @Override public void connectionFinished(ConnectionEvent source, State connectionState, BasePacket packet) {
        if(packet instanceof RoutingPacket) {
            if(connectionState == State.FAILED) {
                return;
            }

            RoutingPacket routingPacket = (RoutingPacket) packet;
            ConnectionEvent target = routingPacket.getNextTarget();

            if(target == null || target.getId().equals(this.getId())) {
                logger.log("Found Destination [Null? %s]", target == null);
                this.connectionFinished(routingPacket.getSource(), connectionState, routingPacket.getPayload());
            } else {
                logger.log("Forwarding!");
                forwardRoutingPacket(routingPacket, target);
            }
        } else
            handleConnectionFinished(source, connectionState, packet);
    }

    @Override public String toString() {
        return "Device{" +
                "connectedDevices=" + connectedDevices +
                ", id='" + id + '\'' +
                ", machineID='" + machineID + '\'' +
                ", physicalMachine=" + physicalMachine +
                ", repository=" + repository +
                '}';
    }

    abstract void handleConnectionStarted(ConnectionEvent source);
    abstract void handleConnectionFinished(ConnectionEvent source, State connectionState, BasePacket packet);
}
