package com.gwtao.ui.viewdriver.client;

import org.shu4j.utils.permission.Permission;

public interface IViewDriver<M> {
  void updateView(M model, Permission perm);

  void updateErrors();

  void addPermissionHandler();

  boolean isDirty();

  void clearDirty();

  void updateModel(M data);
}
