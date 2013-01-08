/* 
 * Copyright 2012 Matthias Huebner
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.gwtao.ui.model.client.source;

import org.shu4j.utils.permission.Permission;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;
import com.gwtao.ui.model.client.source.events.AbstractModelSourceEvent;
import com.gwtao.ui.model.client.source.events.ModelChangedEvent;

public abstract class AbstractModelSource<M> implements IModelSource<M> {
  private final HandlerManager handlerManager;

  public AbstractModelSource() {
    this.handlerManager = new HandlerManager(this);
  }

  public final <H extends AbstractModelSourceEvent.Handler> HandlerRegistration addHandler(final H handler, AbstractModelSourceEvent.Type<H> type) {
    assert handler != null : "handler must not be null";
    assert type != null : "type must not be null";
    return handlerManager.addHandler(type, handler);
  }

  protected final void notifyChange() {
    fireEvent(new ModelChangedEvent());
  }

  protected final <H extends AbstractModelSourceEvent.Handler, E extends AbstractModelSourceEvent<H>> void fireEvent(E event) {
    handlerManager.fireEvent(event);
  }

  @Override
  public boolean isNull() {
    return getModel() == null;
  }

  @Override
  public Permission getPermission() {
    return Permission.ALLOWED;
  }

}
