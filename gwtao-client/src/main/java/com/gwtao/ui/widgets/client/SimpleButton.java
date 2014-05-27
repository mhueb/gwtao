package com.gwtao.ui.widgets.client;

import org.apache.commons.lang.Validate;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.gwtao.ui.data.client.source.IDataSource;
import com.gwtao.ui.util.client.GWTUtils;
import com.gwtao.ui.util.client.action.ActionFocusWidgetAdapter;
import com.gwtao.ui.util.client.action.ActionUtil;
import com.gwtao.ui.util.client.action.IAction;

public class SimpleButton extends Button {
  private ActionFocusWidgetAdapter adapter;

  public SimpleButton() {
  }

  public SimpleButton(IAction action, final IDataSource<?> source) {
    setAction(action, source);
  }

  public void setAction(final IAction action, final IDataSource<?> source) {
    Validate.isTrue(this.adapter == null);
    this.adapter = new ActionFocusWidgetAdapter(action, this, source) {
      @Override
      public void update() {
        super.update();
        setText(action.getDisplayText());
        setTitle(action.getDisplayTooltip());
      }
    };
    addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent event) {
        if (action != null)
          ActionUtil.saveExecute(GWTUtils.createEventInfo(event.getNativeEvent()), action, source);
      }
    });
    if (isAttached())
      adapter.addAdapter();
  }

  @Override
  protected void onLoad() {
    super.onLoad();
    if (adapter != null)
      adapter.addAdapter();
  }

  @Override
  protected void onUnload() {
    if (adapter != null)
      adapter.removeAdapter();
    super.onUnload();
  }
}
