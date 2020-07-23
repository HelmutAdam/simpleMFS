package com.ssi;

public interface IOrderStateListener {
  void onDevice(Order order);
  void orderStarted(Order order);
  void orderFinished(Order order, AckType Ack);
}
