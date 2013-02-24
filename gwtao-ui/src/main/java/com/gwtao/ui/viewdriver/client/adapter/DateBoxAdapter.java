package com.gwtao.ui.viewdriver.client.adapter;

import java.util.Date;

import org.shu4j.utils.permission.Permission;

import com.google.gwt.editor.client.LeafValueEditor;
import com.google.gwt.user.datepicker.client.DateBox;
import com.google.gwt.user.datepicker.client.DatePicker;
import com.gwtao.ui.viewdriver.client.adapter.IFieldAdapter.WidgetType;

@WidgetType(DateBox.class)
public abstract class DateBoxAdapter<M, T extends Date, F extends DateBox> extends AbstractFieldAdapter<M, Date, F> {

  private F widget;

  public void init(F widget) {
    this.widget = widget;
  }

  public F getWidget() {
    return widget;
  }

  public Date getValue() {
    return getEditor().getValue();
  }

  public void setValue(Date value) {
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

  public LeafValueEditor<Date> getEditor() {
    return widget.asEditor();
  }

}
