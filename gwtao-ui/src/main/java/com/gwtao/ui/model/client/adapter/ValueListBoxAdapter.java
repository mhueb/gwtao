package com.gwtao.ui.model.client.adapter;

import org.shu4j.utils.permission.Permission;

import com.google.gwt.editor.client.adapters.TakesValueEditor;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.ValueListBox;
import com.gwtao.ui.model.client.adapter.IFieldAdapter.WidgetType;

@WidgetType(ValueListBox.class)
public abstract class ValueListBoxAdapter<M, T, F extends ValueListBox<T>> extends AbstractFieldAdapter<M, T, F> {

  private F widget;

  @Override
  public void init(F widget) {
    this.widget = widget;

  }

  @Override
  public void setPermission(Permission perm) {
    switch (perm) {
    case ALLOWED:
      DOM.setElementPropertyBoolean(widget.getElement(), "disabled", false);
      DOM.setElementPropertyBoolean(widget.getElement(), "visible", true);
      break;
    case HIDDEN:
      DOM.setElementPropertyBoolean(widget.getElement(), "disabled", true);
      DOM.setElementPropertyBoolean(widget.getElement(), "visible", false);
      break;
    case READONLY:
      DOM.setElementPropertyBoolean(widget.getElement(), "disabled", true);
      DOM.setElementPropertyBoolean(widget.getElement(), "visible", true);
      break;
    }
  }

  @Override
  public F getWidget() {
    return widget;
  }

  public TakesValueEditor<T> getEditor() {
    return widget.asEditor();
  }

  @Override
  public T getValue() {
    return getEditor().getValue();
  }

  @Override
  public void setValue(T value) {
    getEditor().setValue(value);
  }

}
