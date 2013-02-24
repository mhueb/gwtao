package com.gwtao.ui.viewdriver.client.adapter;

import org.shu4j.utils.permission.Permission;
import org.shu4j.utils.util.HasId;

import com.google.gwt.editor.client.adapters.TakesValueEditor;
import com.gwtao.ui.viewdriver.client.adapter.IFieldAdapter.WidgetType;
import com.gwtao.ui.widgets.client.IDPicker;

@WidgetType(IDPicker.class)
public abstract class IDPickerAdapter<M, T, F extends IDPicker<? extends HasId<T>, T>> extends AbstractFieldAdapter<M, T, F> {

  private F widget;

  public void init(F widget) {
    this.widget = widget;
  }

  public F getWidget() {
    return widget;
  }

  public T getValue() {
    HasId<T> value = getEditor().getValue();
    return value == null ? null : value.getId();
  }

  public void setValue(T value) {
    // getEditor().setValue(value);
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

  public TakesValueEditor<? extends HasId<T>> getEditor() {
    return widget.asEditor();
  }

}
