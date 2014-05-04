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
package com.gwtao.ui.task.client;

import java.util.Set;

import javax.validation.ConstraintViolation;

import com.gwtao.ui.location.client.IParameterConverter;
import com.gwtao.ui.viewdriver.client.IViewDriver;

public final class TaskController<P, M> extends AbstractTaskController<P, M> {

  private IViewDriver<M> viewMgr;

  public TaskController(IViewDriver<M> viewMgr, IParameterConverter<P, M> converter, IAsyncDataReader<P, M> reader, IAsyncTaskPerformer<M> performer) {
    super(converter, reader, performer);
    this.viewMgr = viewMgr;
  }

  public boolean isDirty() {
    return this.viewMgr.isDirty();
  }

  protected void onEdit() {
    viewMgr.updateView(getData(), getPermission());
    viewMgr.clearDirty();
  }

  protected void handleDriverErrors() {
    // TODO
  }

  @Override
  protected M flush() {
    viewMgr.updateModel(getData());

    return getData();
  }

  @Override
  protected boolean hasErrors() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  protected void setConstraintViolations(Set<ConstraintViolation<?>> constraintViolations) {
    // TODO Auto-generated method stub

  }
}
