package com.ssi.controller;

import com.ssi.AckType;
import com.ssi.Location;
import com.ssi.Order;

public interface IDeviceListener {
  Order onIdle(Location location);
  void onError(Location location, Order order, AckType error);
}