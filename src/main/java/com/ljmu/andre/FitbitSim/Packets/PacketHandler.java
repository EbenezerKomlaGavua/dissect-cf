package com.ljmu.andre.FitbitSim.Packets;

import com.ljmu.andre.FitbitSim.Interfaces.ConnectionEvent;
import com.ljmu.andre.FitbitSim.Interfaces.ConnectionEvent.State;
import com.ljmu.andre.FitbitSim.Utils.Logger;

import java.util.ArrayList;

import hu.mta.sztaki.lpds.cloud.simulator.iaas.resourcemodel.ResourceConsumption;
import hu.mta.sztaki.lpds.cloud.simulator.iaas.resourcemodel.ResourceConsumption.ConsumptionEvent;
import hu.mta.sztaki.lpds.cloud.simulator.io.NetworkNode;
import hu.mta.sztaki.lpds.cloud.simulator.io.NetworkNode.NetworkException;

/**
 * Created by Andre on 08/02/2017.
 */
public class PacketHandler {
    private static final Logger logger = new Logger(PacketHandler.class);

    //TODO Change limit to something a little more elegant
    private static final int limit = 10;

    public static ArrayList<BasePacket> sendPackets(final ConnectionEvent source, final ConnectionEvent target,
                                                    final ArrayList<BasePacket> packetList) {
        logger.log("Attempting to send: " + packetList.size() + " packets");
        ArrayList<BasePacket> failedPackets = new ArrayList<BasePacket>();

        for (BasePacket packet : packetList) {
            logger.log("Sending packet: " + packet.id);

            sendPacket(source, target, packet);
        }

        if (failedPackets.size() > 0)
            logger.log("Failed packets: " + failedPackets.size());

        return failedPackets;
    }

    public static boolean sendPacket(final ConnectionEvent source, final ConnectionEvent target,
                                     final BasePacket packet) {

        try {
            if (NetworkNode.checkConnectivity(source.getRepository(), target.getRepository()) <= 0)
                return false;

            target.connectionStarted(source);
            ConsumptionEvent consumptionEvent = getConsumptionEvent(source, target, packet);

            if (packet.getShouldStore()) {
                registerPacketIfNotExist(source, packet);
                return source.getRepository()
                        .requestContentDelivery(
                                packet.id,
                                target.getRepository(),
                                consumptionEvent);
            } else {
                NetworkNode.initTransfer(
                        packet.size,
                        limit,
                        source.getRepository(),
                        target.getRepository(),
                        consumptionEvent);

                return true;
            }
        } catch (NetworkException e) {
            logger.log("Failed to send packet: " + packet.id + "\nReason: " + e.getMessage());

            // Check if the packet was registered before we tried to send it
            boolean packetIsRegistered = source.getRepository().lookup(packet.id) != null;

            // If it was not already registered, we can assume it was not intended to stay
            // Register the object so that we can grab it to send again later
            // Force the packet to deregister from the source once successfully transferred
            if (!packetIsRegistered &&
                    source.getRepository().registerObject(packet))
                packet.addDeregisterObject(source);

            source.connectionFinished(source, State.FAILED, packet);
        }

        return false;
    }

    private static ConsumptionEvent getConsumptionEvent(final ConnectionEvent source,
                                                        final ConnectionEvent target,
                                                        final BasePacket packet) {
        return new ConsumptionEvent() {
            @Override public void conComplete() {
                logger.log("Packet[" + packet.id + "] successfully sent");

                source.connectionFinished(source, State.SUCCESS, packet);
                target.connectionFinished(source, State.SUCCESS, packet);

                // Packets that are not intended to stay after transfer are deregistered here
                // This allows for failed packets to be wiped
                if (packet.shouldDeregister(source))
                    source.getRepository().deregisterObject(packet);
                if (packet.shouldDeregister(target))
                    target.getRepository().deregisterObject(packet);
            }

            @Override public void conCancelled(ResourceConsumption problematic) {
                source.connectionFinished(source, State.FAILED, packet);
                logger.log("Cancelled: " + problematic.toString());
            }
        };
    }

    private static void registerPacketIfNotExist(ConnectionEvent source, BasePacket packet) {
        if (source.getRepository().lookup(packet.id) == null) {
            logger.log("Registering packet: " + packet);
            source.getRepository().registerObject(packet);
        }
    }
}
