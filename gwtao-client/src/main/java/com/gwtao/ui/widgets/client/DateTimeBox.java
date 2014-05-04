package com.gwtao.ui.widgets.client;

import java.util.Collection;
import java.util.Date;

import org.shu4j.utils.util.DateUtil;

import com.google.gwt.editor.client.IsEditor;
import com.google.gwt.editor.client.adapters.TakesValueEditor;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.datepicker.client.CalendarUtil;
import com.google.gwt.user.datepicker.client.DateBox;
import com.google.gwt.user.datepicker.client.DatePicker;

public class DateTimeBox extends ComplexPanel implements HasValue<Date>, IsEditor<TakesValueEditor<Date>> {
  /**
   * Creates a new value every time a date is accessed.
   */
  class DateTimeChangeEvent extends ValueChangeEvent<Date> {

    /**
     * Creates a new date value change event.
     * 
     * @param value the value
     */
    protected DateTimeChangeEvent(Date value) {
      // The date must be copied in case one handler causes it to change.
      super(CalendarUtil.copyDate(value));
    }

    @Override
    public Date getValue() {
      return CalendarUtil.copyDate(super.getValue());
    }
  }

  public static enum TYPE {
    DATE_TIME,
    DATE_ONLY
  }

  private static String[] TIMES = {
      "midnight",
      "12:30 AM",
      "01:00 AM",
      "01:30 AM",
      "02:00 AM",
      "02:30 AM",
      "03:00 AM",
      "03:30 AM",
      "04:00 AM",
      "04:30 AM",
      "05:00 AM",
      "05:30 AM",
      "06:00 AM",
      "06:30 AM",
      "07:00 AM",
      "07:30 AM",
      "08:00 AM",
      "08:30 AM",
      "09:00 AM",
      "09:30 AM",
      "10:00 AM",
      "10:30 AM",
      "11:00 AM",
      "11:30 AM",
      "noon",
      "12:30 PM",
      "01:00 PM",
      "01:30 PM",
      "02:00 PM",
      "02:30 PM",
      "03:00 PM",
      "03:30 PM",
      "04:00 PM",
      "04:30 PM",
      "05:00 PM",
      "05:30 PM",
      "06:00 PM",
      "06:30 PM",
      "07:00 PM",
      "07:30 PM",
      "08:00 PM",
      "08:30 PM",
      "09:00 PM",
      "09:30 PM",
      "10:00 PM",
      "10:30 PM",
      "11:00 PM",
      "11:30 PM" };

  private DateBox dateBox = new DateBox(new DatePicker(), null, new DateBox.DefaultFormat(DateTimeFormat.getFormat(PredefinedFormat.DATE_SHORT)));
  private ListBox timeBox = new ListBox();

  private TakesValueEditor<Date> editor;

  public DateTimeBox() {
    this(TYPE.DATE_TIME);
  }

  public DateTimeBox(TYPE type) {
    setElement(DOM.createDiv());

    dateBox.addStyleName("gwtao-datebox");
    dateBox.addValueChangeHandler(new ValueChangeHandler<Date>() {
      @Override
      public void onValueChange(ValueChangeEvent<Date> event) {
        dateBoxValueChanged(event.getValue());
      }
    });

    insert(dateBox, getElement(), 0, true);

    if (type == TYPE.DATE_TIME) {
      timeBox.addStyleName("gwtao-timebox");
      timeBox.addChangeHandler(new ChangeHandler() {
        @Override
        public void onChange(ChangeEvent event) {
          timeValueChanged();
        }
      });
      for (String time : TIMES) {
        this.timeBox.addItem(time);
      }
      insert(timeBox, getElement(), 1, false);
    }
  }

  protected void timeValueChanged() {
    fireValueChangedevent();
  }

  protected void dateBoxValueChanged(Date value) {
    fireValueChangedevent();
  }

  private void fireValueChangedevent() {
    ValueChangeEvent<Date> event = new DateTimeChangeEvent(this.getValue());
    fireEvent(event);
  }

  public HandlerRegistration addValueChangeHandler(ValueChangeHandler<Date> valueChangeHandler) {
    return addHandler(valueChangeHandler, ValueChangeEvent.getType());
  }

  /**
   * Returns a {@link TakesValueEditor} backed by the ValueListBox.
   */
  public TakesValueEditor<Date> asEditor() {
    if (editor == null) {
      editor = TakesValueEditor.of(this);
    }
    return editor;
  }

  @Override
  public Date getValue() {
    Date ret = dateBox.getValue();

    if (ret != null) {
      ret = DateUtil.clearTime(ret);
      int hour = 0;
      int min = 0;
      int index = this.timeBox.getSelectedIndex();

      if (index >= 0) {
        hour = index / 2;
        min = index % 2 * 30;
      }
      Date time = DateUtil.makeDateTime(0, 0, 0, hour, min, 0);

      ret = DateUtil.merge(ret, time);
    }
    return ret;
  }

  @Override
  public void setValue(Date value) {
    dateBox.setValue(value);
    if (value == null) {
      this.timeBox.setSelectedIndex(-1);
    }
    else {
      int hour = value.getHours();
      int minute = value.getMinutes();
      int index = hour * 2;

      if (minute >= 30) {
        index = index + 1;
      }
      this.timeBox.setSelectedIndex(index);
    }
  }

  @Override
  public void setValue(Date newValue, boolean fireEvents) {
    Date oldValue = getValue();

    if (newValue == oldValue || (oldValue != null && oldValue.equals(newValue))) {
      return;
    }

    this.setValue(newValue);

    if (fireEvents) {
      ValueChangeEvent.fireIfNotEqual(this, oldValue, newValue);
    }
  }
}
