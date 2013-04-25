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
package com.gwtao.portalapp.client.deprecated.client.editcontext;

import org.shu4j.utils.permission.Permission;

import com.gwtao.ui.util.client.action.ActionAdapter;
import com.gwtao.ui.util.client.action.IAction;

public class EditContextActionAdapter extends ActionAdapter {
  private final IEditContext<?> ctx;
  private final IEditContextPermission filter;

  public EditContextActionAdapter(IAction action, IEditContext<?> ctx) {
    this(action, ctx, IEditContextPermission.ALL);
  }

  public EditContextActionAdapter(IAction action, IEditContext<?> ctx, IEditContextPermission filter) {
    super(action);
    this.ctx = ctx;
    this.filter = filter;
    ctx.addStateListener(new EditContextListenerAdapter() {
      @Override
      public void onStateChange() {
        enforcePermission();
      }

      @Override
      public void onDataStateChange() {
        enforcePermission();
      }

      @Override
      public void onSetData() {
        enforcePermission();
      }
    });
  }

  @Override
  public void execute(Object... data) {
    if (data != null && data.length != 0)
      throw new RuntimeException("unexpected input data");
    super.execute(ctx.getData());
  }

  @Override
  public Permission getPermission(Object... data) {
    if (data != null && data.length != 0)
      throw new RuntimeException("unexpected input data");
    Permission perm = super.getPermission(ctx.getData());
    return perm.add(filter.getPermission(ctx));
  }

  @Override
  public boolean equals(Object obj) {
    return super.equals(obj) || obj instanceof EditContextActionAdapter && isEqual((EditContextActionAdapter) obj);
  }

  private boolean isEqual(EditContextActionAdapter obj) {
    return this.ctx.equals(obj.ctx) && super.isEqual(obj);
  }

  protected IEditContext<?> getContext() {
    return this.ctx;
  }
}
