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
package com.gwtao.ui.context.client.editcontext;

import org.shu4j.utils.permission.Permission;

import com.gwtao.ui.context.client.editcontext.IEditContext.State;

public interface IEditContextPermission {
  IEditContextPermission ALL = new IEditContextPermission() {
    @Override
    public Permission getPermission(IEditContext<?> ctx) {
      return Permission.ALLOWED;
    }
  };

  /**
   * if the context is handling a new model, permission will be denied, else allowed
   */
  IEditContextPermission NOTNEW = new IEditContextPermission() {
    @Override
    public Permission getPermission(IEditContext<?> ctx) {
      return ctx.isDataNull() || ctx.isNew() ? Permission.READONLY : Permission.ALLOWED;
    }
  };

  IEditContextPermission EDIT = new IEditContextPermission() {
    @Override
    public Permission getPermission(IEditContext<?> ctx) {
      return !ctx.isDataNull() && ctx.getState() == State.EDIT ? Permission.ALLOWED : Permission.READONLY;
    }
  };

  IEditContextPermission EDITNEW = new IEditContextPermission() {
    @Override
    public Permission getPermission(IEditContext<?> ctx) {
      return !ctx.isDataNull() && ctx.getState() == State.EDIT && ctx.isNew() ? Permission.ALLOWED : Permission.READONLY;
    }
  };

  IEditContextPermission NOEDIT = new IEditContextPermission() {
    @Override
    public Permission getPermission(IEditContext<?> ctx) {
      return !ctx.isDataNull() && ctx.getState() == State.EDIT ? Permission.READONLY : Permission.ALLOWED;
    }
  };

  IEditContextPermission ASCTX = new IEditContextPermission() {
    @Override
    public Permission getPermission(IEditContext<?> ctx) {
      return ctx.getPermission();
    }
  };

  Permission getPermission(IEditContext<?> ctx);
}