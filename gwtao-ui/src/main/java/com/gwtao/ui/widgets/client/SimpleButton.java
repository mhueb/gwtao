package com.gwtao.ui.widgets.client;

import org.shu4j.utils.permission.Permission;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.gwtao.ui.data.client.source.IDataSource;
import com.gwtao.ui.util.client.GWTUtil;
import com.gwtao.ui.util.client.action.ActionFocusWidgetAdapter;
import com.gwtao.ui.util.client.action.ActionUtil;
import com.gwtao.ui.util.client.action.IAction;

public class SimpleButton extends Button {
  public SimpleButton() {
  }

  public SimpleButton(IAction action, final IDataSource<?> source) {
    setAction(action, source);
  }

  public void setAction(final IAction action, final IDataSource<?> source) {
    addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent event) {
        ActionUtil.saveExecute(GWTUtil.createEventInfo(event), action, source);
      }
    });

    action.getWidgetHandler().addAdapter(new ActionFocusWidgetAdapter(action, this, source) {
      @Override
      public void update() {
        super.update();
        updateText(action);
      }
    });

    action.getWidgetHandler().update();
  }

  private void updateText(final IAction action) {
    setText(action.getTitle());
    setTitle(action.getTooltip());
  }

}
