/* 
 * Copyright 2012 Matthias Huebner
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.gwtao.ui.location.client;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.UnhandledException;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.GWT.UncaughtExceptionHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.Window.ClosingEvent;
import com.google.gwt.user.client.Window.ClosingHandler;

/**
 * 
 * 
 * @author Matthias Hübner
 * 
 * @param <T> Type of the location presenter.
 */
public final class LocationManager<T> {
  private final IPresenterManager<T> presenterManager;

  private boolean started;

  private final Map<Token, T> presenters = new HashMap<Token, T>();
  private Token currentToken;
  private T currentPresenter;

  private HandlerRegistration addValueChangeHandler;

  private HandlerRegistration addWindowClosingHandler;

  private ILocationChangeInterceptor locationChangeInterceptor;

  public LocationManager(IPresenterManager<T> presenterManager) {
    this.presenterManager = presenterManager;
  }

  public void startup() {
    if (started)
      throw new IllegalStateException("unexpected startup");

    this.started = true;

    addValueChangeHandler = History.addValueChangeHandler(new ValueChangeHandler<String>() {
      @Override
      public void onValueChange(ValueChangeEvent<String> event) {
        onHistoryChange(event.getValue());
      }
    });

    addWindowClosingHandler = Window.addWindowClosingHandler(new ClosingHandler() {
      @Override
      public void onWindowClosing(ClosingEvent event) {
        event.setMessage(onClose());
      }
    });

    onHistoryChange(History.getToken());
  }

  public void reset() {
    if (started) {
      started = false;
      addValueChangeHandler.removeHandler();
      addValueChangeHandler = null;
      addWindowClosingHandler.removeHandler();
      addWindowClosingHandler = null;
      this.currentPresenter = null;
      this.currentToken = null;
      this.presenters.clear();
      History.newItem("", false);
    }
  }

  private String onClose() {
    for (T presenter : presenters.values()) {
      String message = presenterManager.canClose(presenter);
      if (StringUtils.isNotBlank(message))
        return message;
    }
    return null;
  }

  private void onHistoryChange(String tokenString) {
    Token token = Token.create(tokenString);
    try {
      if (token == null)
        return;

      if (locationChangeInterceptor != null && locationChangeInterceptor.before(token))
        return;

      if (currentToken != null) {
        if (currentToken.equals(token)) {
          return;
        }

        if (!presenterManager.deactivate(currentPresenter)) {
          History.back();
          return;
        }
      }

      T presenter = presenters.get(token);
      if (presenter == null) {
        presenter = presenterManager.createLocation(token);
        if (presenter == null)
          throw new IllegalStateException("Unhandled location='" + token + "'");
        presenters.put(token, presenter);
      }

      currentToken = token;
      currentPresenter = presenter;
      presenterManager.activate(currentPresenter);
    }
    catch (Exception e) {
      currentToken = token;
      currentPresenter = null;

      String error = "Failed to open presenter for location = " + token + ": " + e.toString();

      try {
        currentPresenter = presenterManager.createErrorLocation(token, error);
        presenterManager.activate(currentPresenter);
      }
      catch (Exception e1) {
        UnhandledException fatal = new UnhandledException(error, e1);
        UncaughtExceptionHandler uncaughtExceptionHandler = GWT.getUncaughtExceptionHandler();
        if (uncaughtExceptionHandler != null)
          uncaughtExceptionHandler.onUncaughtException(fatal);
        else
          throw fatal;
      }
    }
  }

  public void notifyActivation(T presenter) {
    if (presenter == null || this.currentPresenter == presenter)
      return;

    Token token = findToken(presenter);
    if (token != null) {
      this.currentPresenter = presenter;
      this.currentToken = token;
      History.newItem(token.getValue(), false);
    }
  }

  public void notifyRemove(T presenter) {
    if (presenter == null)
      return;
    Token token = findToken(presenter);
    if (token != null) {
      presenters.remove(token);
    }
    if (currentPresenter == presenter) {
      currentPresenter = null;
      currentToken = null;
    }
  }

  public void notifyLocationChange(T presenter, Token token) {
    Token oldToken = findToken(presenter);
    if (oldToken != null) {
      presenters.remove(oldToken);
      presenters.remove(token);
      presenters.put(token, presenter);
      if (currentPresenter == presenter) {
        currentToken = token;
        History.newItem(token.getValue(), false);
      }
    }
  }

  protected Token findToken(T presenter) {
    for (Entry<Token, T> probe : presenters.entrySet()) {
      if (probe.getValue() == presenter) {
        return probe.getKey();
      }
    }
    return null;
  }

  public void setLocationInterceptor(ILocationChangeInterceptor interceptor) {
    locationChangeInterceptor = interceptor;
  }
}