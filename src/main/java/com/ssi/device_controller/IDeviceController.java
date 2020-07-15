package com.ssi.device_controller;

import com.ssi.application_project.Order;
import com.ssi.device.IDevice;

public interface IDeviceController {
  void addOrder(Order order);
  void setDevice(IDevice device);
  void shutdownDevice();
}
