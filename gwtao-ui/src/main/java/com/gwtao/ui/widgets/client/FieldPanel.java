package com.gwtao.ui.widgets.client;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class FieldPanel extends ComplexPanel {

  public static class FieldSet extends ComplexPanel {
    private double width;
    private Unit unit;
    private Element legend;

    public FieldSet() {
      setElement(DOM.createFieldSet());
      getElement().setClassName("gwtao-fieldSet");
      width = 120;
      unit = Unit.PX;
    }

    public void addField(String labelText, Widget field) {
      insert(new FieldRow(labelText, field, width, unit), getElement(), getWidgetCount(), false);
    }

    public void setLegend(String string) {
      if (legend == null) {
        legend = DOM.createLegend();
        DOM.insertChild(getElement(), legend, 0);
      }
      legend.setInnerText(string);
    }
  }

  private double width;
  private Unit unit;

  private static final class FieldRow extends ComplexPanel {
    public FieldRow(String labelText, Widget field, double width, Unit unit) {
      setElement(DOM.createDiv());
      getElement().setClassName("gwtao-fieldSetRow");
      Label label = new Label(labelText + ":");
      label.getElement().setClassName("gwtao-fieldSetLabel");
      label.getElement().getStyle().setWidth(width, unit);
      field.getElement().setClassName("gwtao-fieldSetField");
      insert(label, getElement(), 0, false);
      insert(field, getElement(), 1, false);
    }
  }

  public FieldPanel() {
    setElement(DOM.createDiv());
    getElement().setClassName("gwtao-fieldPanel");
    width = 120;
    unit = Unit.PX;
  }

  public void addField(String labelText, Widget field) {
    insert(new FieldRow(labelText, field, width, unit), getElement(), getWidgetCount(), false);
  }

  public void addSet(FieldSet fieldset) {
    insert(fieldset, getElement(), getWidgetCount(), false);
  }
}
