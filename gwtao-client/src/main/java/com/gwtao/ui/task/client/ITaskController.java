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
package com.gwtao.ui.task.client;

import javax.validation.Validator;

import com.gwtao.ui.data.client.source.IDataSource;
import com.gwtao.ui.task.client.AbstractTaskController.State;

public interface ITaskController<P, M> extends IDataSource<M> {
  Validator getValidator();

  State getState();

  boolean isDirty();

  void start(P param);

  void perform();

  void perform(IAsyncTaskPerformer<M> performer);

  void refresh();

  void edit(M model);

  boolean validateAndFlush();
}