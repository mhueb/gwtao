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