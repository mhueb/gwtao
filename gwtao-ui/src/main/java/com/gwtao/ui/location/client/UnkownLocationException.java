package com.gwtao.ui.location.client;

public class UnkownLocationException extends RuntimeException {
  private static final long serialVersionUID = 1L;

  public UnkownLocationException(Token token) {
    super("Unknown location '" + token.getName() + "'");
  }
}
