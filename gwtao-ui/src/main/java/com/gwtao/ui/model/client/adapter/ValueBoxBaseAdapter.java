package com.gwtao.ui.model.client.adapter;

import org.shu4j.utils.permission.Permission;

import com.google.gwt.editor.ui.client.adapters.ValueBoxEditor;
import com.google.gwt.user.client.ui.DoubleBox;
import com.google.gwt.user.client.ui.IntegerBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.ValueBoxBase;
import com.gwtao.ui.model.client.adapter.IFieldAdapter.WidgetType;

@WidgetType({
    TextBox.class,
    DoubleBox.class,
    IntegerBox.class })
public abstract class ValueBoxBaseAdapter<M, T, F extends ValueBoxBase<T>> extends AbstractFieldAdapter<M, T, F> {

  private F widget;

  public void init(F widget) {
    this.widget = widget;
  }

  public F getWidget() {
    return widget;
  }

  public T getValue() {
    return getEditor().getValue();
  }

  public void setValue(T value) {
    getEditor().setValue(value);
  }

  @Override
  public void setPermission(Permission perm) {
    switch (perm) {
    case ALLOWED:
      widget.setReadOnly(false);
      widget.setEnabled(true);
      widget.setVisible(true);
      break;
    case HIDDEN:
      widget.setReadOnly(true);
      widget.setEnabled(false);
      widget.setVisible(false);
      break;
    case READONLY:
      widget.setReadOnly(true);
      widget.setEnabled(true);
      widget.setVisible(true);
      break;
    }
  }

  public ValueBoxEditor<T> getEditor() {
    return widget.asEditor();
  }

}
