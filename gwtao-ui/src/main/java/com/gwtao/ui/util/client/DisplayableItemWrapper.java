package com.gwtao.ui.util.client;

public class DisplayableItemWrapper implements IDisplayableItem {

  public static IDisplayableItem wrap(IDisplayableItem wrapped) {
    return new DisplayableItemWrapper(wrapped);
  }

  private IDisplayableItem wrapped;

  private DisplayableItemWrapper(IDisplayableItem wrapped) {
    if (wrapped == null)
      this.wrapped = DisplayableItem.EMPTY;
    else
      this.wrapped = wrapped;
  }

  public String getDisplayIcon() {
    return wrapped.getDisplayIcon();
  }

  public String getDisplayTitle() {
    return wrapped.getDisplayTitle();
  }

  public String getDisplayTooltip() {
    return wrapped.getDisplayTooltip();
  }
}
