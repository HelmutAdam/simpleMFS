package com.ssi;


public class Config {
  private final Class<?> implClass;
  private final String deviceName;
  
  public Config(Class<?> implClass, String id) {
    this.implClass = implClass;
    this.deviceName = id;
  }

  public Class<?> getImplClass() {
    return implClass;
  }

  public String getId() {
    return deviceName;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((deviceName == null) ? 0 : deviceName.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Config other = (Config) obj;
    if (deviceName == null) {
      if (other.deviceName != null)
        return false;
    } else if (!deviceName.equals(other.deviceName))
      return false;
    return true;
  }
  
}
