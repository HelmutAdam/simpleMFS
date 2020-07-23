package com.ssi;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ssi.device.IDevice;

public class Order{
  private final Location source;
  private final Location destination;
  private final String id;
  private final int priority;
  private final Sequence sequence;
  private final List<IOrderStateListener> stateListenerList = new ArrayList<>(); 
  private static final Logger LOG = LogManager.getLogger(Order.class);
  
  public Order(Location source, Location destination, String id, int priority, Sequence sequence) {
    this.source = source;
    this.destination = destination;
    this.id = id;
    this.priority = priority;
    this.sequence = sequence;
  }

  public Location getSource() {
    return source;
  }

  public Location getDestination() {
    return destination;
  }

  public String getId() {
    return id;
  }

  public int getPriority() {
    return priority;
  }

  public Sequence getSequence() {
    return sequence;
  }
  
  public void onDevice(IDevice device) {
    LOG.info("{}: onDevice - {}", device, this);
    for (IOrderStateListener stateListener : stateListenerList) {
      stateListener.onDevice(this);
    }
  }
  
  public void orderStarted(IDevice device) {
    LOG.info("{}: orderStarted - {}", device, this);
    for (IOrderStateListener stateListener : stateListenerList) {
      stateListener.orderStarted(this);
    }
  }
  
  public void orderFinished(IDevice device, AckType Ack) {
    LOG.info("{}: orderFinished - {}", device, this);
    for (IOrderStateListener stateListener : stateListenerList) {
      stateListener.orderFinished(this, Ack);
    }
  }

  public void registerStateListener(IOrderStateListener stateListener) {
    stateListenerList.add(stateListener);
  }

  @Override
  public String toString() {
    return "Order: " + id + " from: " + source + " to: " + destination + " with priority: " + priority + " and " + sequence;
  }
  
}
