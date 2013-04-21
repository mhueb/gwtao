package com.gwtao.ui.task.client;

import java.util.Set;

import javax.validation.ConstraintViolation;

import com.gwtao.ui.viewdriver.client.IViewDriver;

public final class TaskController<M> extends AbstractTaskController<M> {

  private IViewDriver<M> viewMgr;

  public TaskController(IViewDriver<M> viewMgr, IServiceAdapter<M> serviceAdapter) {
    super(serviceAdapter);
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
