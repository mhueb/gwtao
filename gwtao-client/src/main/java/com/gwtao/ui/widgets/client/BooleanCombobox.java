/* 
 * Copyright 2012 GWTAO
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.gwtao.ui.widgets.client;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.shu4j.utils.util.Pair;

import com.google.gwt.editor.client.IsEditor;
import com.google.gwt.editor.client.adapters.TakesValueEditor;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasConstrainedValue;
import com.google.gwt.user.client.ui.ListBox;

public class BooleanCombobox extends Composite implements HasConstrainedValue<Boolean>, IsEditor<TakesValueEditor<Boolean>> {

  private TakesValueEditor<Boolean> editor;
  private List<Pair<String, Boolean>> values = new ArrayList<Pair<String, Boolean>>();
  private Boolean value;

  private Boolean initial;

  public BooleanCombobox() {
    initWidget(new ListBox());
    getListBox().addChangeHandler(new ChangeHandler() {
      public void onChange(ChangeEvent event) {
        int selectedIndex = getListBox().getSelectedIndex();

        if (selectedIndex < 0) {
          return; // Not sure why this happens during addValue
        }
        Boolean newValue = values.get(selectedIndex).getSecond();
        setValue(newValue, true);
      }
    });
  }

  public Boolean getValue() {
    return value;
  }

  /**
   * Set the value and display it in the select element. Add the value to the acceptable set if it is not
   * already there.
   */
  public void setValue(Boolean value) {
    setValue(value, false);
  }

  public void setValue(Boolean value, boolean fireEvents) {
    if (value == this.value || (this.value != null && this.value.equals(value))) {
      return;
    }

    Boolean before = this.value;
    this.value = value;

    if (fireEvents) {
      ValueChangeEvent.fireIfNotEqual(this, before, value);
    }
  }

  private ListBox getListBox() {
    return (ListBox) getWidget();
  }

  public void setEnabled(boolean b) {
    getListBox().setEnabled(b);
  }

  public void setInitial(Boolean b) {
    this.initial = b;
  }

  @Override
  public HandlerRegistration addValueChangeHandler(ValueChangeHandler<Boolean> handler) {
    return addHandler(handler, ValueChangeEvent.getType());
  }

  @Override
  /**
   * Returns a {@link TakesValueEditor} backed by the ValueListBox.
   */
  public TakesValueEditor<Boolean> asEditor() {
    if (editor == null) {
      editor = TakesValueEditor.of(this);
    }
    return editor;
  }

  @Override
  public void setAcceptableValues(Collection<Boolean> newValues) {
  }

}
