package com.gwtao.ui.model.client.mgr;

import java.util.ArrayList;
import java.util.List;

import org.shu4j.utils.permission.Permission;

import com.google.gwt.user.client.ui.Widget;
import com.gwtao.ui.model.client.adapter.IFieldAdapter;

public class ViewManager<M> implements IViewManager<M> {

  private final List<IFieldAdapter<M, ? extends Widget>> fields = new ArrayList<IFieldAdapter<M, ? extends Widget>>();

  public ViewManager() {
  }

  public <T extends Widget> void add(IFieldAdapter<M, T> adp, T field) {
    adp.init(field);
    fields.add(adp);
  }

  @Override
  public void updateView(M model, Permission perm) {
    for (IFieldAdapter<M, ?> field : fields) {
      field.updateView(model);
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

}
