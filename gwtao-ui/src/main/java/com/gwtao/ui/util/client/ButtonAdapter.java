package com.gwtao.ui.util.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.AttachEvent;
import com.google.gwt.user.client.ui.Button;
import com.gwtao.ui.data.client.source.IDataSource;
import com.gwtao.ui.util.client.action.ActionFocusWidgetAdapter;
import com.gwtao.ui.util.client.action.ActionUtil;
import com.gwtao.ui.util.client.action.IAction;

public class ButtonAdapter {
  private ActionFocusWidgetAdapter adapter;

  public ButtonAdapter(Button button, final IAction action, final IDataSource<?> source) {
    this.adapter = new ActionFocusWidgetAdapter(action, button, source);

    if (button.isAttached())
      action.getWidgetHandler().addAdapter(adapter);

    button.addClickHandler(new ClickHandler() {

      @Override
      public void onClick(ClickEvent event) {
        ActionUtil.saveExecute(GWTUtils.createEventInfo(event.getNativeEvent()), action, source.getData());
      }
    });

    button.addAttachHandler(new AttachEvent.Handler() {

      @Override
      public void onAttachOrDetach(AttachEvent event) {
        if (event.isAttached())
          action.getWidgetHandler().addAdapter(adapter);
        else
          action.getWidgetHandler().removeAdapter(adapter);
      }
    });
  }
}
