package com.gwtao.ui.widgets.client;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class FieldPanel extends Composite {

  private final class PrivateFieldPanel extends ComplexPanel {
    public PrivateFieldPanel() {
      setElement(DOM.createDiv());
    }

    public void add(Widget child) {
      insert(child, getElement(), getWidgetCount(), false);
    }
  }

  public static class FieldSet extends ComplexPanel {
    private double width;
    private Unit unit;
    private Element legend;

    public FieldSet() {
      setElement(DOM.createFieldSet());
      setStyleName("gwtao-fieldSet");
      width = 120;
      unit = Unit.PX;
    }

    public void addField(String labelText, Widget field) {
      FieldRow child = new FieldRow();
      child.addLabel(labelText, width, unit);
      child.addWidget(field);
      insert(child, getElement(), getWidgetCount(), false);
    }

    public void setLegend(String string) {
      if (legend == null) {
        legend = DOM.createLegend();
        DOM.insertChild(getElement(), legend, 0);
      }
      legend.setInnerText(string);
    }
  }

  public static final class FieldRow extends ComplexPanel {
    public FieldRow() {
      setElement(DOM.createDiv());
      setStyleName("gwtao-fieldSetRow");
    }

    public void addLabel(String labelText, double width, Unit unit) {
      Label label = new Label(labelText == null ? "" : (labelText + ":"));
      label.addStyleName("gwtao-fieldSetLabel");
      label.getElement().getStyle().setWidth(width, unit);
      label.getElement().getStyle().setProperty("minWidth", label.getElement().getStyle().getWidth());
      insert(label, getElement(), getWidgetCount(), false);
    }

    public void addWidget(Widget field) {
      Element div = DOM.createDiv();
      div.setClassName("gwtao-fieldSetField");
      DOM.appendChild(getElement(), div);
      insert(field, div, 0, false);
    }
  }

  private ComplexPanel panel = new PrivateFieldPanel();
  private double width;
  private Unit unit;

  public FieldPanel() {
    this(120, Unit.PX);
  }

  public FieldPanel(int width, Unit unit) {
    panel.setStyleName("gwtao-fieldPanel");
    initWidget(panel);
    this.width = width;
    this.unit = unit;
  }

  public void addField(String labelText, Widget field) {
    FieldRow child = new FieldRow();
    child.addLabel(labelText, width, unit);
    child.addWidget(field);
    addRow(child);
  }

  public void addRow(FieldRow child) {
    panel.add(child);
  }

  public void addSet(FieldSet fieldset) {
    panel.add(fieldset);
  }
}
