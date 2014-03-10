package com.example.arrayadapteritem;

//another class to handle item's id and name
public class ObjectItem {

  public int major_number;
  public int minor_number;
  public int rssi;
  public String uuid;

  // constructor
  public ObjectItem(String uuid, int major_number, int minor_number, int rssi) {
      this.uuid = new String(uuid);
      this.major_number = major_number;
      this.minor_number = minor_number;
      this.rssi = rssi;
  }

}