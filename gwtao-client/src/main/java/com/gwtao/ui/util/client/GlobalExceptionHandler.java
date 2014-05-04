package com.gwtao.ui.util.client;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.GWT.UncaughtExceptionHandler;

public class GlobalExceptionHandler implements UncaughtExceptionHandler {

  private static final GlobalExceptionHandler INSTANCE = new GlobalExceptionHandler();

  private Logger logger = Logger.getLogger(GlobalExceptionHandler.class.getName());

  public interface ExceptionHandler {
    boolean handleException(Throwable e);
  }

  private List<ExceptionHandler> handlers = new ArrayList<ExceptionHandler>();

  public GlobalExceptionHandler() {
    GWT.setUncaughtExceptionHandler(this);
  }

  public static GlobalExceptionHandler get() {
    return INSTANCE;
  }

  public void addExceptionHandler(ExceptionHandler handler) {
    this.handlers.add(0, handler);
  }

  @Override
  public void onUncaughtException(Throwable e) {
    for (ExceptionHandler handler : handlers) {
      try {
        if (handler.handleException(e)) {
          logger.log(Level.INFO, "Uncaught Exception", e);
          return;
        }
      }
      catch (Throwable throwable) {
        logger.log(Level.SEVERE, "exception handler failed", throwable);
      }
    }
    logger.log(Level.SEVERE, "Uncaught exception", e);
  }
}
