package com.ssi.device_controller;

import java.util.LinkedList;
import java.util.Queue;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ssi.application_project.AckType;
import com.ssi.application_project.Location;
import com.ssi.application_project.Order;
import com.ssi.device.IDevice;

public class SimpleDc implements IDeviceController, IDeviceListener {

  private final String controllerName;
  private final Queue<Order> orderQueue = new LinkedList<>();
  
  private IDevice device;
  private static final Logger LOG = LogManager.getLogger(SimpleDc.class);
  
  @Inject
  public SimpleDc(@Named("DcName") String controllerName) {
    this.controllerName = controllerName;
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
    return "SimpleDc " + controllerName;
  }

  @Override
  public void shutdownDevice() {
    if (device != null) {
      device.shutdown();
    }
  }

}
