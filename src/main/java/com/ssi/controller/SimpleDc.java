package com.ssi.controller;

import java.util.LinkedList;
import java.util.Queue;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.ssi.AckType;
import com.ssi.Location;
import com.ssi.Order;
import com.ssi.device.IDevice;

public class SimpleDc implements IDeviceController, IDeviceListener {

  private final String id;
  private final Queue<Order> orderQueue = new LinkedList<>();
  
  private IDevice device;
  private static final Logger LOG = LogManager.getLogger(SimpleDc.class);
  
  @Inject
  public SimpleDc(@Named("id") String id) {
    this.id = id;
  }

  @Override
  public Order onIdle(Location location) {
    Order nextOrder = orderQueue.poll();
    return nextOrder;
  }

  @Override
  public void onError(Location location, Order order, AckType error) {
    LOG.error(error.name() + " for Order " + order.getId() + " on location " + location);
  }

  @Override
  public void addOrder(Order order) {
    orderQueue.add(order);
    if (device != null) {
      device.trigger();
    }
  }
  
  public void setDevice(IDevice device) {
    this.device = device;
  }

  @Override
  public String toString() {
    return "SimpleDc " + id;
  }

  @Override
  public void shutdownDevice() {
    if (device != null) {
      device.shutdown();
    }
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    SimpleDc other = (SimpleDc) obj;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    return true;
  }

}
