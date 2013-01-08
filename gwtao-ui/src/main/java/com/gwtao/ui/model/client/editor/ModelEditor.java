package com.gwtao.ui.model.client.editor;

import java.util.Set;

import javax.validation.ConstraintViolation;

import com.gwtao.ui.model.client.mgr.IViewManager;

public final class ModelEditor<M> extends AbstractModelEditor<M> {

  private IViewManager<M> viewMgr;

  public ModelEditor(IViewManager<M> viewMgr, IServiceAdapter<M> serviceAdapter) {
    super(serviceAdapter);
    this.viewMgr = viewMgr;
  }

  public boolean isDirty() {
    return this.viewMgr.isDirty();
  }

  protected void onEdit() {
    viewMgr.updateView(getModel(), getPermission());
    viewMgr.clearDirty();
  }

  protected void handleDriverErrors() {
    // TODO
  }

  @Override
  protected M flush() {
    // TODO Auto-generated method stub
    return getModel();
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
