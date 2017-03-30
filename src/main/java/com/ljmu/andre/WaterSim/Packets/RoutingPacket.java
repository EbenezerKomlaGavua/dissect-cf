package com.ljmu.andre.WaterSim.Packets;

import com.ljmu.andre.WaterSim.Interfaces.ConnectionEvent;

import java.util.List;

/**
 * Created by Andre on 30/03/2017.
 */
public class RoutingPacket extends BasePacket {
    private BasePacket payload;
    private List<ConnectionEvent> route;
    private ConnectionEvent source;

    public RoutingPacket(String myid, BasePacket payload, ConnectionEvent source, List<ConnectionEvent> route) {
        super(myid, payload.size, false);
        this.payload = payload;
        this.source = source;
        this.route = route;
        setShouldStore(false);
    }

    public BasePacket getPayload() {
        return payload;
    }

    public ConnectionEvent getSource() {
        return source;
    }

    public List<ConnectionEvent> getRoute() {
        return route;
    }

    public ConnectionEvent getNextTarget() {
        if(route.isEmpty())
            return null;

        ConnectionEvent target = route.get(0);

        if(target == null)
            return null;

        route.remove(0);
        return target;
    }
}
