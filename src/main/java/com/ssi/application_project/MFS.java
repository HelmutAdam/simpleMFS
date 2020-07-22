package com.ssi.application_project;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.name.Names;
import com.ssi.device.IDevice;
import com.ssi.device_controller.IDeviceController;
import com.ssi.device_controller.SimpleDc;

public class MFS {
  private static final Logger LOG = LogManager.getLogger(MFS.class);
  
  public static void main(String[] args) {

    Set<Config> configList = new HashSet<>();
    configList.add(new Config(SimpleDc.class, "First Device"));
    configList.add(new Config(SimpleDc.class, "Second Device"));
    
    Injector injector = Guice.createInjector(new DeviceModule(configList));
    
    Set<IDeviceController> controllers = new HashSet<>();
    for (Config config : configList) {
      IDeviceController deviceController = injector.getInstance(Key.get(IDeviceController.class, Names.named(config.getId())));
      controllers.add(deviceController);
      IDevice deviceSimple = injector.getInstance(Key.get(IDevice.class, Names.named(config.getId())));
      deviceController.setDevice(deviceSimple);
      
      Set<Order> orderSet = new LinkedHashSet<>();
      fillOrderSet(orderSet);
      startDeviceControllerWithOrders(deviceController, orderSet);
    }
  }
  
  private static void startDeviceControllerWithOrders(IDeviceController dc, Set<Order> orderSet) {
    IOrderStateListener orderStateListener = createStateListener(dc, orderSet.size());
    for (Order order : orderSet) {
      order.registerStateListener(orderStateListener);
      dc.addOrder(order);
    }
    LOG.info("Orders sent to {}", dc);
  }
  
  private static IOrderStateListener createStateListener(IDeviceController dc, int orderSize) {
    return new IOrderStateListener() {
      private int ordersFinished = 0;
      private long startTime = 0;
      @Override
      public void orderStarted(Order order) {
        if (startTime == 0) {
          startTime = System.currentTimeMillis();
        }
      }
      
      @Override
      public void orderFinished(Order order, AckType Ack) {
        ordersFinished++;
        if (ordersFinished == orderSize) {
          LOG.info("{} needed {}ms to execute {} orders", dc, System.currentTimeMillis() - startTime, ordersFinished);
          LOG.info("shutting down {}", dc);
          dc.shutdownDevice();
        }
      }
      
      @Override
      public void onDevice(Order order) {
      }
    };
  }
  
  private static void fillOrderSet(Set<Order> orderSet) {
    orderSet.add(new Order(new Location(0, Side.ONE), new Location(10, Side.ONE), "order01", 1, new Sequence(1, 1)));
    orderSet.add(new Order(new Location(5, Side.ONE), new Location(17, Side.TWO), "order02", 1, new Sequence(1, 1)));
    orderSet.add(new Order(new Location(18, Side.TWO), new Location(7, Side.TWO), "order03", 2, new Sequence(2, 1)));
    orderSet.add(new Order(new Location(19, Side.ONE), new Location(11, Side.ONE), "order04", 3, new Sequence(1, 2)));
    orderSet.add(new Order(new Location(10, Side.TWO), new Location(13, Side.ONE), "order05", 2, new Sequence(1, 3)));
    orderSet.add(new Order(new Location(19, Side.TWO), new Location(13, Side.ONE), "order06", 1, new Sequence(2, 1)));
    orderSet.add(new Order(new Location(17, Side.ONE), new Location(12, Side.TWO), "order07", 4, new Sequence(2, 2)));
    orderSet.add(new Order(new Location(8, Side.TWO), new Location(1, Side.TWO), "order08", 3, new Sequence(3, 1)));
    orderSet.add(new Order(new Location(18, Side.ONE), new Location(16, Side.TWO), "order09", 4, new Sequence(3, 1)));
    orderSet.add(new Order(new Location(9, Side.ONE), new Location(14, Side.ONE), "order10", 4, new Sequence(1, 2)));
  }
}
