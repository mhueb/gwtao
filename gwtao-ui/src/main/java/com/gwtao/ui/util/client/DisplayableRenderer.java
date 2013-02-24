package com.gwtao.ui.util.client;

import com.google.gwt.text.shared.AbstractRenderer;

public class DisplayableRenderer<T extends IDisplayableItem> extends AbstractRenderer<T> {
  
  public DisplayableRenderer() {
    // TODO Auto-generated constructor stub
  }

  @Override
  public String render(T object) {
    return object == null ? "" : object.getTitle();
  }

}
