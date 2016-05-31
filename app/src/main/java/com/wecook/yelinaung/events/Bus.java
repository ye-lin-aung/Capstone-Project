package com.wecook.yelinaung.events;

/**
 * Created by user on 5/31/16.
 */
public class Bus {
  private static com.squareup.otto.Bus bus;

  public static com.squareup.otto.Bus getInstance() {
    if (bus == null) {
      bus = new com.squareup.otto.Bus();
    }
    return bus;
  }
}
