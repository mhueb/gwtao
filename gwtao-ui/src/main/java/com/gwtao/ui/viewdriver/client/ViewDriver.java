package com.gwtao.ui.viewdriver.client;

import java.util.ArrayList;
import java.util.List;

import org.shu4j.utils.permission.Permission;

import com.google.gwt.user.client.ui.Widget;

public class ViewDriver<M> implements IViewDriver<M> {

  private final List<WidgetAdapter<M, ?>> widgets = new ArrayList<WidgetAdapter<M, ?>>();

  public ViewDriver() {
  }

  public <T extends Widget> void add(WidgetAdapter<M, ?> adp) {
    widgets.add(adp);
  }

  @Override
  public void updateView(M model, Permission perm) {
    for (WidgetAdapter<M, ?> widget : widgets) {
      if (model != null)
        widget.updateView(model);
      widget.setPermission(perm);
    }
  }

  @Override
  public boolean isDirty() {
    for (WidgetAdapter<M, ?> widget : widgets) {
      if (widget.isDirty())
        return true;
    }
    return false;
  }

  @Override
  public void clearDirty() {
    for (WidgetAdapter<M, ?> widget : widgets) {
      widget.clearDirty();
    }
  }

  @Override
  public void updateModel(M data) {
    for (WidgetAdapter<M, ?> widget : widgets) {
      widget.updateModel(data);
    }
  }

}
