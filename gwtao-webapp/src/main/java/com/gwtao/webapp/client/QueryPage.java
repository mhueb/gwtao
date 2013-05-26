package com.gwtao.webapp.client;

import com.gwtao.ui.util.client.action.Action;
import com.gwtao.ui.util.client.action.IAction;

public abstract class QueryPage<T> extends AbstractPage {

  public QueryPage() {
  }

  IAction action = new Action() {

    @Override
    public void execute(Object... data) {
      // TODO Auto-generated method stub

    }
  };

  public IAction getQueryAction() {
    return action;
  }
}
