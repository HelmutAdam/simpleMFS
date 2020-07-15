package ssi.com.device_controller;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.atLeastOnce;

import com.ssi.application_project.Order;
import com.ssi.device.IDevice;
import com.ssi.device_controller.SimpleDc;

public class SimpleDcTest {

  private SimpleDc simpleDc = null;
  private IDevice device = null;
  
  @Before
  public void setup() {
    simpleDc = new SimpleDc("Simple Test Controller");
    device = mock(IDevice.class);
    simpleDc.setDevice(device);
  }
  
  @Test
  public void getNewOrderOnIdle() {
    simpleDc.addOrder(mock(Order.class));
    assertNotNull(simpleDc.onIdle(null));
  }
  
  @Test
  public void notfifyDeviceOnNewOrder() {
    simpleDc.addOrder(mock(Order.class));
    verify(device, atLeastOnce()).trigger();
  }
  
  @Test
  public void processOrdersFifo() {
    Order order1 = mock(Order.class);
    Order order2 = mock(Order.class);
    simpleDc.addOrder(order1);
    simpleDc.addOrder(order2);
    assertEquals(order1, simpleDc.onIdle(null));
    assertEquals(order2, simpleDc.onIdle(null));
  }
}
