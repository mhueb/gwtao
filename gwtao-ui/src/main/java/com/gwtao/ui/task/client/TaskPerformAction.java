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
