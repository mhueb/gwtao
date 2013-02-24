package com.gwtao.ui.task.client;

import javax.validation.Validator;

import com.gwtao.ui.data.client.source.IDataSource;
import com.gwtao.ui.task.client.AbstractTaskController.State;
import com.gwtao.ui.util.client.ParameterList;

public interface ITaskController<M> extends IDataSource<M> {

  public Validator getValidator();

  public State getState();

  public boolean isDirty();

  public void start(ParameterList param);

  public void execute();

  public void refresh();
}