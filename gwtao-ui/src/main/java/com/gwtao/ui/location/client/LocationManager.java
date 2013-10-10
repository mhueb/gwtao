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

  private boolean ignoreEvent;

  private boolean canPopState;

  public LocationManager(IPresenterManager<T> presenterManager) {
    this.presenterManager = presenterManager;
  }

  public void startup(Token token) {
    if (started)
      throw new IllegalStateException("unexpected startup");

    this.started = true;

    addValueChangeHandler = History.addValueChangeHandler(new ValueChangeHandler<String>() {
      @Override
      public void onValueChange(ValueChangeEvent<String> event) {
        canPopState = true;
        onHistoryChange(event.getValue());
      }
    });

    addWindowClosingHandler = Window.addWindowClosingHandler(new ClosingHandler() {
      @Override
      public void onWindowClosing(ClosingEvent event) {
        event.setMessage(onClose());
      }
    });

    if (token != null)
      onHistoryChange(token);
    else
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
    }
  }

  private String onClose() {
    for (T presenter : presenters.values()) {
      String message = presenterManager.canClose(presenter);
      if (StringUtils.isNotBlank(message))
        return message;
    }
    return presenterManager.canClose();
  }

  private void onHistoryChange(String token) {
    onHistoryChange(TokenUtils.buildToken(token));
  }

  private void onHistoryChange(Token token) {
    try {
      if (ignoreEvent)
        return;

      if (currentToken != null) {
        if (currentToken.equals(token))
          return;
        if (!presenterManager.hide(currentPresenter)) {
          popLocation();
          return;
        }
      }

      currentToken = token;

      rebuildLocation();
    }
    catch (Exception e) {
      currentPresenter = null;

      String error;
      if (StringUtils.isNotBlank(e.getMessage()))
        error = e.getMessage();
      else
        error = e.toString();

      try {
        currentPresenter = presenterManager.createErrorPresenter(token, error);
        presenterManager.show(currentPresenter);
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

  public void rebuildLocation() {
    if (presenterManager.locationChangeHook(currentToken)) {
      T presenter = presenters.get(currentToken);
      if (presenter == null) {
        presenter = presenterManager.createPresenter(currentToken);
        if (presenter == null)
          throw new IllegalStateException("Unhandled token='" + currentToken + "'");
        presenters.put(currentToken, presenter);
      }
      currentPresenter = presenter;
      presenterManager.show(currentPresenter);
    }
  }

  private void popLocation() {
    if (canPopState) {
      ignoreEvent = true;
      History.back();
      ignoreEvent = false;
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
        popLocation();
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

  public Token getCurrentToken() {
    return currentToken;
  }

  public void replacePresenter(T presenter) {
    currentPresenter = presenter;
    presenterManager.show(currentPresenter);
  }
}