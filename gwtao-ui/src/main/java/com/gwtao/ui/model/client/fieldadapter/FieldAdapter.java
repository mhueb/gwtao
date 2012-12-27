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
package com.gwtao.ui.model.client.fieldadapter;

import org.shu4j.utils.permission.IPermissionDelegate;
import org.shu4j.utils.permission.Permission;

import com.gwtao.ui.context.client.datacontext.IDataChangeListener;
import com.gwtao.ui.context.client.datacontext.IDataContext;

public abstract class FieldAdapter<T> implements IFieldAdapter<T>, IDataChangeListener {

  private IDataContext<? extends T> ctx;
  private boolean loopLock = true;
  private boolean forceReadOnly = false;
  private IPermissionDelegate delegate;

  public void setContext(IDataContext<? extends T> ctx) {
    if (this.ctx != null)
      this.ctx.removeChangeListener(this);
    this.ctx = ctx;
    if (ctx != null)
      ctx.addChangeListener(this);
    if (getWidget() != null && getWidget().isAttached())
      updateField();
  }

  public void onDataChange() {
    updateField();
    updatePermission();
  }

  public final void updateField() {
    if (loopLock) {
      loopLock = false;
      try {
        doUpdateField();
      }
      finally {
        loopLock = true;
      }
    }
  }

  protected abstract void doUpdateField();

  protected void onFieldChange() {
    if (loopLock && !forceReadOnly) {
      loopLock = false;
      try {
        updateModel();
        ctx.notifyChange();
      }
      finally {
        loopLock = true;
      }
    }
  }

  protected T getModel() {
    return ctx.getData();
  }

  protected Permission getPermission() {
    Permission perm = forceReadOnly ? Permission.READONLY : Permission.ALLOWED;
    perm = perm.add(ctx.getPermission());
    if (delegate != null)
      perm = perm.add(delegate.getPermission(getCtx().getData()));
    return perm;
  }

  public void forceReadOnly(boolean readonly) {
    this.forceReadOnly = readonly;
  }

  public void setPermissionDelegate(IPermissionDelegate delegate) {
    this.delegate = delegate;
  }

  public IDataContext<? extends T> getCtx() {
    return ctx;
  }
}
