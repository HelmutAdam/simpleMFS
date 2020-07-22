package com.ssi.application_project;

import java.util.HashSet;
import java.util.Set;

import com.google.inject.AbstractModule;
import com.google.inject.Key;
import com.google.inject.PrivateBinder;
import com.google.inject.Singleton;
import com.google.inject.name.Names;
import com.ssi.device.Device;
import com.ssi.device.IDevice;
import com.ssi.device_controller.IDeviceController;
import com.ssi.device_controller.IDeviceListener;
public class DeviceModule extends AbstractModule{
  
  Set<Config> configSet = new HashSet<>();

  public DeviceModule(Set<Config> configSet) {
    this.configSet = configSet;
  }

  @Override
  protected void configure() {
    
    for (Config config : configSet) {
      PrivateBinder binder = binder().newPrivateBinder();
      binder.bind(String.class).annotatedWith(Names.named("id")).toInstance(config.getId());
      binder.bind(IDevice.class).to(Device.class);
      binder.bind(IDeviceController.class).to((Class<? extends IDeviceController>) config.getImplClass());
      binder.bind(IDeviceListener.class).to((Class<? extends IDeviceListener>) config.getImplClass());
      binder.bind(Key.get(IDeviceController.class, Names.named(config.getId()))).to(IDeviceController.class);
      binder.bind(Key.get(IDevice.class, Names.named(config.getId()))).to(IDevice.class);
      binder.bind(config.getImplClass()).in(Singleton.class);
      binder.expose(Key.get(IDeviceController.class, Names.named(config.getId())));
      binder.expose(Key.get(IDevice.class, Names.named(config.getId())));
    }
  }
  
}
