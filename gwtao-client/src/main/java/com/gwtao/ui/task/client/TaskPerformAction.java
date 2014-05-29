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

import com.gwtao.ui.util.client.action.Action;

public abstract class TaskPerformAction<M> extends Action implements IAsyncTaskPerformer<M> {
  private ITaskController<?, M> taskctrl;
  private String message;

  /**
   * @param title Title of the action
   */
  public TaskPerformAction(ITaskController<?, M> taskctrl, String title, String message) {
    super(title);
    this.taskctrl = taskctrl;
    this.message = message;
  }

  @Override
  public void execute(Object... data) {
    if (taskctrl.validateAndFlush()) {
      taskctrl.perform(this);
    }
  }

  @Override
  public String getWaitMessage() {
    return message;
  }
}
