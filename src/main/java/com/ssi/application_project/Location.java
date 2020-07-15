package com.ssi.application_project;

public class Location {
  private final int x;
  private final Side side;
  
  public Location(int x, Side side) {
    this.x = x;
    this.side = side;
  }
  
  public int getX() {
    return x;
  }
  
  public Side getSide() {
    return side;
  }

  @Override
  public String toString() {
    return "Location x=" + x + ", side=" + side;
  }
  
}
