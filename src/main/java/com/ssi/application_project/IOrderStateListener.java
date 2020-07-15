package com.ssi.application_project;

public interface IOrderStateListener {
  void onDevice(Order order);
  void orderStarted(Order order);
  void orderFinished(Order order, AckType Ack);
}
