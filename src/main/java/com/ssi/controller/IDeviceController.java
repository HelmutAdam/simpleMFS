package com.ssi.controller;

import com.ssi.Order;
import com.ssi.device.IDevice;

public interface IDeviceController {
  void addOrder(Order order);
  void setDevice(IDevice device);
  void shutdownDevice();
}
