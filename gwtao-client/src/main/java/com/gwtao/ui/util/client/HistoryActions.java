package com.gwtao.ui.util.client;

import com.google.gwt.user.client.History;
import com.gwtao.ui.util.client.action.Action;
import com.gwtao.ui.util.client.action.IAction;
import com.gwtao.ui.util.i18n.UIConstants;

public class HistoryActions {
  public static final IAction backAction = new Action(UIConstants.c.back()) {

    @Override
    public void execute(Object... data) {
      History.back();
    }
  };

  public static final IAction forwardAction = new Action(UIConstants.c.forward()) {

    @Override
    public void execute(Object... data) {
      History.forward();
    }
  };
}
