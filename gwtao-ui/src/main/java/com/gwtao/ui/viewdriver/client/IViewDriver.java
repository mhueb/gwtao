package com.gwtao.ui.viewdriver.client;

import org.shu4j.utils.permission.Permission;

public interface IViewDriver<M> {
  void updateView(M model, Permission perm);

  boolean isDirty();

  void clearDirty();

  void updateModel(M data);
}
