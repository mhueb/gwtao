package com.gwtao.ui.model.client.editor.events;

import com.gwtao.ui.model.client.source.events.AbstractModelSourceEvent;

public abstract class AbstractModelEditorEvent<H extends AbstractModelSourceEvent.Handler> extends AbstractModelSourceEvent<H> {
  private boolean pre;

  protected AbstractModelEditorEvent(boolean pre) {
    this.pre = pre;
  }

  public boolean isPre() {
    return pre;
  }
}
