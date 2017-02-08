package com.ljmu.andre.FitbitSim.Packets;

import com.ljmu.andre.FitbitSim.Interfaces.ConnectionEvent;
import com.ljmu.andre.FitbitSim.Interfaces.ConnectionEvent.State;

import java.util.ArrayList;

import hu.mta.sztaki.lpds.cloud.simulator.iaas.resourcemodel.ResourceConsumption;
import hu.mta.sztaki.lpds.cloud.simulator.iaas.resourcemodel.ResourceConsumption.ConsumptionEvent;
import hu.mta.sztaki.lpds.cloud.simulator.io.NetworkNode;
import hu.mta.sztaki.lpds.cloud.simulator.io.NetworkNode.NetworkException;

/**
 * Created by Andre on 08/02/2017.
 */
public class PacketHandler {

    public static ArrayList<BasicPacket> sendPackets(final ConnectionEvent source, final ConnectionEvent target,
                                                     final ArrayList<BasicPacket> packetList) {
        System.out.println("Attempting to send: " + packetList.size() + " packets");
        ArrayList<BasicPacket> failedPackets = new ArrayList<BasicPacket>();

        for (BasicPacket packet : packetList) {
            System.out.println("Sending packet: " + packet.id);
            if (!sendPacket(source, target, packet)) {
                failedPackets.add(packet);
                target.getRepository().registerObject(packet);
            }
        }

        if(failedPackets.size() > 0)
            System.out.println("Failed packets: " + failedPackets.size());

        return failedPackets;
    }

    public static boolean sendPacket(final ConnectionEvent source, final ConnectionEvent target,
                                     final BasicPacket packet) {
        createPacketIfNotExist(source, packet);

        try {
            if (NetworkNode.checkConnectivity(source.getRepository(), target.getRepository()) <= 0)
                return false;

            target.connectionStarted(source);
            return source.getRepository().requestContentDelivery(packet.id, target.getRepository(), new ConsumptionEvent() {
                @Override public void conComplete() {
                    System.out.println("Packet[" + packet.id + "] successfully sent");

                    source.connectionFinished(source, State.SUCCESS, packet);
                    target.connectionFinished(source, State.SUCCESS, packet);

                    if(packet.getShouldDeregister()) {
                        source.getRepository().deregisterObject(packet);
                        target.getRepository().deregisterObject(packet);

                        System.out.println("Deregistered packet");
                    }
                }

                @Override public void conCancelled(ResourceConsumption problematic) {
                    source.connectionFinished(source, State.FAILED, packet);
                    System.out.println("Cancelled: " + problematic.toString());
                }
            });
        } catch (NetworkException e) {
            System.out.println("Failed to send packet: " + packet.id + "\nReason: " + e.getMessage());
            source.connectionFinished(source, State.FAILED, packet);
        }

        return false;
    }

    private static void createPacketIfNotExist(ConnectionEvent source, BasicPacket packet) {
        if(source.getRepository().lookup(packet.id) == null) {
            System.out.println("Registering packet: " + packet);
            source.getRepository().registerObject(packet);
        }
    }
}
