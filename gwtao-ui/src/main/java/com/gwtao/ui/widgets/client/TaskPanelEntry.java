package com.gwtao.ui.widgets.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.AnchorElement;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.gwtao.ui.util.client.card.CardItem;
import com.gwtao.ui.util.client.card.ICard;

public class TaskPanelEntry extends Composite {

  private static TaskPanelEntryUiBinder uiBinder = GWT.create(TaskPanelEntryUiBinder.class);

  interface TaskPanelEntryUiBinder extends UiBinder<Widget, TaskPanelEntry> {
  }

  @UiField
  DivElement headLine;

  @UiField
  DivElement tasks;

  public TaskPanelEntry(ICard set) {
    initWidget(uiBinder.createAndBindUi(this));
    headLine.setInnerText(set.getDisplayTitle());

    String className = tasks.getFirstChildElement().getClassName();
    tasks.removeChild(tasks.getFirstChildElement());
    for (CardItem item : set) {
      if (item.isToken()) {
        AnchorElement span = AnchorElement.as(DOM.createAnchor());
        span.setClassName(className);
        span.setHref("#" + item.asToken().getToken());
        span.setInnerText(item.getDisplayTitle());
        tasks.appendChild(span);
      }
    }
  }

}
