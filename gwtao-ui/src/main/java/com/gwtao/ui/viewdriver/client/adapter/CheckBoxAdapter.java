package com.gwtao.ui.viewdriver.client.adapter;

import org.shu4j.utils.permission.Permission;

import com.google.gwt.editor.client.LeafValueEditor;
import com.google.gwt.user.client.ui.CheckBox;
import com.gwtao.ui.viewdriver.client.adapter.IFieldAdapter.WidgetType;

@WidgetType(CheckBox.class)
public abstract class CheckBoxAdapter<M, T extends Boolean, F extends CheckBox> extends AbstractFieldAdapter<M, Boolean, F> {

  private F widget;

  public void init(F widget) {
    this.widget = widget;
  }

  public F getWidget() {
    return widget;
  }

  public Boolean getValue() {
    return getEditor().getValue();
  }

  public void setValue(T value) {
    getEditor().setValue(value);
  }

  @Override
  public void setPermission(Permission perm) {
    switch (perm) {
    case ALLOWED:
      widget.setVisible(true);
      break;
    case HIDDEN:
      widget.setVisible(false);
      break;
    case READONLY:
      widget.setVisible(true);
      break;
    }
  }

  public LeafValueEditor<Boolean> getEditor() {
    return widget.asEditor();
  }

}
