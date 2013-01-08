package com.gwtao.ui.location.client;

public class UnkownLocation extends RuntimeException {
  private static final long serialVersionUID = 1L;

  public UnkownLocation(Location location) {
    super("Unknown location '" + location.getId() + "'");
  }
}
