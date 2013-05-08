package com.gwtao.ui.viewdriver.client;

import java.util.ArrayList;
import java.util.List;

import org.shu4j.utils.permission.Permission;

import com.google.gwt.user.client.ui.Widget;
import com.gwtao.ui.viewdriver.client.adapter.IFieldAdapter;

public class ViewDriver<M> implements IViewDriver<M> {

  private final List<IFieldAdapter<M, ? extends Widget>> fields = new ArrayList<IFieldAdapter<M, ? extends Widget>>();

  public ViewDriver() {
  }

  public <T extends Widget> void add(IFieldAdapter<M, T> adp, T field) {
    adp.init(field);
    fields.add(adp);
  }

  @Override
  public void updateView(M model, Permission perm) {
    for (IFieldAdapter<M, ?> field : fields) {
      if (model != null)
        field.updateView(model);
      field.setPermission(perm);
    }
  }

  @Override
  public void updateErrors() {
    // TODO Auto-generated method stub

  }

  @Override
  public void addPermissionHandler() {
    // TODO Auto-generated method stub

  }

  @Override
  public boolean isDirty() {
    for (IFieldAdapter<M, ?> field : fields) {
      if (field.isDirty())
        return true;
    }
    return false;
  }

  @Override
  public void clearDirty() {
    for (IFieldAdapter<M, ?> field : fields) {
      field.clearDirty();
    }
  }

  @Override
  public void updateModel(M data) {
    for (IFieldAdapter<M, ?> field : fields) {
      field.updateModel(data);
    }
  }

}
