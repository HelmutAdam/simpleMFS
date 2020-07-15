package com.ssi.device;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Named;

import com.google.inject.Inject;
import com.ssi.application_project.AckType;
import com.ssi.application_project.Location;
import com.ssi.application_project.Order;
import com.ssi.application_project.Side;
import com.ssi.device_controller.IDeviceListener;

public class Device implements IDevice {

  private static final  int TIME_TO_MOVE_ONE_PLACE = 100;
  private static final int TIME_TO_LOAD = 1000;
  
  private final ExecutorService executorService = Executors.newSingleThreadExecutor();
  private final IDeviceListener deviceListener;
  private final String deviceName;
  
  private Location location;
  private Order currentOrder = null;
  
  @Inject
  public Device(@Named("DeviceName")String deviceName, IDeviceListener deviceListener) {
    this.deviceName = deviceName;
    this.deviceListener = deviceListener;
    this.location = new Location(0, Side.ONE);
  }

  @Override
  public void trigger() {
     if (currentOrder != null) {
       return;
     }
     currentOrder = deviceListener.onIdle(location);
     executeCurrentOrder();
  }
  
  private void executeCurrentOrder() {
    if (currentOrder == null) {
      return;
    }
    executorService.execute(new Runnable() {
      @Override
      public void run() {
        simulateOrderExecution();
      }
    });
  }
  
  private void simulateOrderExecution() {
    try {
      currentOrder.orderStarted(this);
      travelTo(currentOrder.getSource());
      location = currentOrder.getSource();
      Thread.sleep(TIME_TO_LOAD);
      currentOrder.onDevice(this);
      travelTo(currentOrder.getDestination());
      location = currentOrder.getDestination();
      Thread.sleep(TIME_TO_LOAD);
      currentOrder.orderFinished(this, AckType.Success);
      currentOrder = null;
      trigger();
    } catch (InterruptedException e) {
      deviceListener.onError(location, currentOrder, AckType.DeviceError);
      currentOrder = null;
      e.printStackTrace();
    }
  }
  
  private void travelTo(Location destination) throws InterruptedException {
    int distanceToDestination = Math.abs(location.getX() - destination.getX());
    Thread.sleep(TIME_TO_MOVE_ONE_PLACE * distanceToDestination);
  }

  @Override
  public String toString() {
    return deviceName;
  }

  @Override
  public void shutdown() {
    if (!executorService.isShutdown()) {
      executorService.shutdown();
    }
  }
  
}
