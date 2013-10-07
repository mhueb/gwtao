package com.gwtao.webapp.client;

import com.google.gwt.user.client.History;
import com.gwtao.ui.util.client.action.Action;
import com.gwtao.ui.util.client.action.IAction;
import com.gwtao.webapp.client.i18n.WebAppConstants;

public class AppActions {
  public static final IAction backAction = new Action(WebAppConstants.CONST.back()) {

    @Override
    public void execute(Object... data) {
      History.back();
    }
  };

  public static final IAction forwardAction = new Action(WebAppConstants.CONST.forward()) {

    @Override
    public void execute(Object... data) {
      History.forward();
    }
  };
}
