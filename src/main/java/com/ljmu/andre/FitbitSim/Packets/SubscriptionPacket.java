package com.ljmu.andre.FitbitSim.Packets;

/**
 * Created by Andre on 07/02/2017.
 */
public class SubscriptionPacket extends BasicPacket {
    private Object subscriber;
    private boolean subState;

    public SubscriptionPacket(boolean subState) {
        super("Unsubscribe", 1, false);
        this.setShouldDeregister(true);
        this.subState = subState;
    }

    public boolean getSubState() {
        return subState;
    }

    public SubscriptionPacket setSubState(boolean subState) {
        this.subState = subState;
        return this;
    }

    public Object getSubscriber() {
        return subscriber;
    }

    public SubscriptionPacket setSubscriber(Object subscriber) {
        this.subscriber = subscriber;
        return this;
    }
}
