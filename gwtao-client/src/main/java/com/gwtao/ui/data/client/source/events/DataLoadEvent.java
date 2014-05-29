/* 
 * Copyright 2012 GWTAO
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
package com.gwtao.ui.data.client.source.events;

public class DataLoadEvent extends AbstractDataSourceEvent<DataLoadEvent.Handler> {
  public static final Type<Handler> TYPE = new Type<Handler>();

  public interface Handler extends AbstractDataSourceEvent.Handler {
    void onDataLoading();

    void onDataLoaded(boolean success);
  }

  private final boolean loading;
  private final Throwable error;

  public DataLoadEvent() {
    this.loading = true;
    this.error = null;
  }

  public DataLoadEvent(Throwable error) {
    this.loading = false;
    this.error = error;
  }

  @Override
  public com.google.gwt.event.shared.GwtEvent.Type<Handler> getAssociatedType() {
    return TYPE;
  }

  @Override
  protected void dispatch(Handler handler) {
    if (loading)
      handler.onDataLoading();
    else
      handler.onDataLoaded(error == null);
  }
}
