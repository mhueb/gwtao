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
package com.gwtao.ui.data.client.source;

import org.shu4j.utils.permission.Permission;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;
import com.gwtao.ui.data.client.source.events.AbstractDataSourceEvent;
import com.gwtao.ui.data.client.source.events.DataChangedEvent;

public abstract class AbstractDataSource<M> implements IDataSource<M> {
  private final HandlerManager handlerManager;

  public AbstractDataSource() {
    this.handlerManager = new HandlerManager(this);
  }

  public final <H extends AbstractDataSourceEvent.Handler> HandlerRegistration addHandler(AbstractDataSourceEvent.Type<H> type, final H handler) {
    assert handler != null : "handler must not be null";
    assert type != null : "type must not be null";
    return handlerManager.addHandler(type, handler);
  }

  public void fireDataChanged() {
    fireEvent(new DataChangedEvent());
  }

  protected final <H extends AbstractDataSourceEvent.Handler, E extends AbstractDataSourceEvent<H>> void fireEvent(E event) {
    handlerManager.fireEvent(event);
  }

  @Override
  public boolean isNull() {
    return getData() == null;
  }

  @Override
  public Permission getPermission() {
    return Permission.ALLOWED;
  }

}
