package com.gwtao.ui.model.client.source.events;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

public abstract class AbstractModelSourceEvent<H extends AbstractModelSourceEvent.Handler> extends GwtEvent<H> {
  public static class Type<H> extends com.google.gwt.event.shared.GwtEvent.Type<H> {
  }
  
  public interface Handler extends EventHandler {
  }
}
