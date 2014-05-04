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

import org.apache.commons.lang.NotImplementedException;

import com.google.gwt.editor.client.EditorDriver;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.gwtao.ui.location.client.IParameterConverter;

public final class TaskEditorDriver<P, M> extends AbstractTaskController<P, M> {

  private EditorDriver<M> driver;

  public TaskEditorDriver(EditorDriver<M> driver, IParameterConverter<P, M> converter, IAsyncDataReader<P, M> reader, IAsyncTaskPerformer<M> performer) {
    super(converter, reader, performer);
    this.driver = driver;
  }

  public final EditorDriver<M> getDriver() {
    return driver;
  }

  public boolean isDirty() {
    return driver.isDirty();
  }

  @SuppressWarnings("unchecked")
  @Override
  protected void onEdit() {
    if (driver instanceof SimpleBeanEditorDriver)
      ((SimpleBeanEditorDriver<M, ?>) driver).edit(getData());
    else
      throw new NotImplementedException();
  }

  protected void handleDriverErrors() {
    // TODO
  }

  @Override
  protected M flush() {
    return driver.flush();
  }

  @Override
  protected boolean hasErrors() {
    return driver.hasErrors();
  }

  @SuppressWarnings({
      "rawtypes",
      "unchecked" })
  @Override
  protected void setConstraintViolations(Set<ConstraintViolation<?>> constraintViolations) {
    Set raw = constraintViolations;
    driver.setConstraintViolations(raw);
  }

}
