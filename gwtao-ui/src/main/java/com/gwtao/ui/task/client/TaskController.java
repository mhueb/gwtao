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
