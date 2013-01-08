package com.gwtao.ui.model.client.editor.events;

import com.gwtao.ui.model.client.source.events.AbstractModelSourceEvent;

public class EditorRemoveEvent extends AbstractModelEditorEvent<EditorRemoveEvent.Handler> {
  public static final Type<Handler> TYPE = new Type<Handler>();

  public interface Handler extends AbstractModelSourceEvent.Handler {
    void onEditorStart(boolean pre);
  }

  public EditorRemoveEvent(boolean pre) {
    super(pre);
  }

  @Override
  public com.google.gwt.event.shared.GwtEvent.Type<Handler> getAssociatedType() {
    return TYPE;
  }

  @Override
  protected void dispatch(Handler handler) {
    handler.onEditorStart(isPre());
  }
}
