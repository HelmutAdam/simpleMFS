package com.ssi.application_project;

import com.google.inject.AbstractModule;
import com.google.inject.Key;
import com.google.inject.PrivateModule;
import com.google.inject.Singleton;
import com.google.inject.name.Names;
import com.ssi.device.Device;
import com.ssi.device.IDevice;
import com.ssi.device_controller.IDeviceController;
import com.ssi.device_controller.IDeviceListener;
import com.ssi.device_controller.SimpleDc;
public class DeviceModule extends AbstractModule{
//private int simpleDcCount = 0;
  
  @Override
  protected void configure() {
    install(new PrivateModule() {
      
      @Override
      protected void configure() {
        bind(String.class).annotatedWith(Names.named("DcName")).toInstance("Controller Simple");
        bind(String.class).annotatedWith(Names.named("DeviceName")).toInstance("Device Simple");
        bind(IDevice.class).to(Device.class);
        bind(IDeviceController.class).to(SimpleDc.class);
        bind(IDeviceListener.class).to(SimpleDc.class);
        bind(Key.get(IDeviceController.class, Names.named("Simple"))).to(IDeviceController.class);
        bind(Key.get(IDevice.class, Names.named("Simple"))).to(IDevice.class);
        bind(SimpleDc.class).in(Singleton.class);
        expose(Key.get(IDeviceController.class, Names.named("Simple")));
        expose(Key.get(IDevice.class, Names.named("Simple")));
      }
    });
  }
  
  /*@Override
  protected void configure() {
    bind(String.class).annotatedWith(Names.named("DcName")).toInstance("Device Controller");
    bind(String.class).annotatedWith(Names.named("DeviceName")).toInstance("Device Simple");
    bind(IDevice.class).to(Device.class);
    bind(IDeviceController.class).to(SimpleDc.class);
    bind(IDeviceListener.class).to(SimpleDc.class);
  }
  
  @Provides
  SimpleDc getSimpleDc(String deviceName) {
    SimpleDc simpleDc = new SimpleDc(deviceName + simpleDcCount);
    Device device = new Device(deviceName + simpleDcCount, simpleDc);
    simpleDc.setDevice(device);
    simpleDcCount++;
    return simpleDc;
  }*/
  
}
