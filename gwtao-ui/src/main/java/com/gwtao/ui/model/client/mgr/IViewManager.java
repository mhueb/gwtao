package com.gwtao.ui.model.client.mgr;

import org.shu4j.utils.permission.Permission;

public interface IViewManager<M> {
  void updateView(M model, Permission perm);

  void updateErrors();
  
  void addPermissionHandler();

  boolean isDirty();

  void clearDirty();
}
