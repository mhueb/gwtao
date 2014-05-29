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
package com.gwtao.portalapp.client.deprecated.client;

import org.shu4j.utils.permission.Permission;

import com.gwtao.ui.data.client.source.IDataSource;
import com.gwtao.ui.data.client.source.events.DataChangedEvent;
import com.gwtao.ui.util.client.action.ActionAdapter;
import com.gwtao.ui.util.client.action.IAction;

public class ContextActionAdapter extends ActionAdapter implements DataChangedEvent.Handler {
  private final IDataSource<?> ctx;
  private boolean respectContextPermission = true;

  public ContextActionAdapter(IAction action, IDataSource<?> ctx) {
    super(action);
    this.ctx = ctx;
    if (this.ctx == null)
      throw new IllegalArgumentException("ContextActionAdapter: ctx is null.");
    ctx.addHandler(DataChangedEvent.TYPE, this);
  }

  public ContextActionAdapter(IAction action, IDataSource<?> ctx, boolean respectContextPermission) {
    this(action, ctx);
    this.respectContextPermission = respectContextPermission;
  }

  @Override
  public void onDataChanged() {
    getWidgetHandler().update();
  }

  @Override
  public void execute(Object... data) {
    if (data != null && data.length != 0)
      throw new RuntimeException("unexpected input data");
    Object obj = ctx.getData();
    if (obj instanceof Object[])
      super.execute((Object[]) obj);
    else
      super.execute(obj);
  }

  @Override
  public Permission getPermission(Object... data) {
    if (data != null && data.length != 0)
      throw new RuntimeException("unexpected input data");

    Permission perm;
    Object obj = ctx.getData();
    if (obj instanceof Object[])
      perm = super.getPermission((Object[]) obj);
    else if (obj != null)
      perm = super.getPermission(obj);
    else
      perm = super.getPermission();

    if (respectContextPermission)
      perm = perm.add(ctx.getPermission());

    return perm;
  }

  @Override
  public boolean equals(Object obj) {
    return super.equals(obj) || obj instanceof ContextActionAdapter && isEqual((ContextActionAdapter) obj);
  }

  protected boolean isEqual(ContextActionAdapter obj) {
    return this.ctx.equals(obj.ctx) && super.isEqual(obj);
  }
}
