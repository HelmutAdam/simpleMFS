package com.ssi.device_controller;

import com.ssi.application_project.AckType;
import com.ssi.application_project.Location;
import com.ssi.application_project.Order;

public interface IDeviceListener {
  Order onIdle(Location location);
  void onError(Location location, Order order, AckType error);
}