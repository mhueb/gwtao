package com.gwtao.ui.widgets.client;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ObjectUtils;
import org.shu4j.utils.util.HasId;

import com.google.gwt.editor.client.IsEditor;
import com.google.gwt.editor.client.adapters.TakesValueEditor;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.text.shared.AbstractRenderer;
import com.google.gwt.text.shared.Renderer;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasConstrainedValue;
import com.google.gwt.user.client.ui.ListBox;
import com.gwtao.ui.data.client.source.IDataSource;
import com.gwtao.ui.data.client.source.NullDataSource;
import com.gwtao.ui.data.client.source.events.DataChangedEvent;
import com.gwtao.ui.data.client.source.events.DataLoadEvent;

public class IDPicker<T extends HasId<ID>, ID> extends Composite implements HasConstrainedValue<T>, IsEditor<TakesValueEditor<T>> {

  private final List<T> values = new ArrayList<T>();
  private final Map<ID, Integer> valueKeyToIndex = new HashMap<ID, Integer>();

  private TakesValueEditor<T> editor;
  private T value;

  private IDataSource<List<T>> source;

  private HandlerRegistration handler;

  private Renderer<T> renderer;

  private boolean loaded;

  private boolean isLoading;
  private ID id;
  private String initial;

  public IDPicker() {
    source = new NullDataSource<List<T>>();
    renderer = new AbstractRenderer<T>() {
      @Override
      public String render(T object) {
        return object == null ? "" : object.toString();
      }
    };

    initWidget(new ListBox());
    getListBox().addChangeHandler(new ChangeHandler() {
      public void onChange(ChangeEvent event) {
        if (isLoading)
          return;

        int selectedIndex = getListBox().getSelectedIndex();

        if (selectedIndex < 0) {
          return; // Not sure why this happens during addValue
        }
        T newValue = values.get(selectedIndex);
        setValue(newValue, true);
      }
    });
  }

  public HandlerRegistration addValueChangeHandler(ValueChangeHandler<T> handler) {
    return addHandler(handler, ValueChangeEvent.getType());
  }

  /**
   * Returns a {@link TakesValueEditor} backed by the ValueListBox.
   */
  public TakesValueEditor<T> asEditor() {
    if (editor == null) {
      editor = TakesValueEditor.of(this);
    }
    return editor;
  }

  public T getValue() {
    return value;
  }

  public ID getValueId() {
    return id;
  }

  public void setAcceptableValues(Collection<T> newValues) {
    if (isLoading)
      return;

    clear();

    for (T nextNewValue : newValues) {
      addValue(nextNewValue);
    }

    updateListBox();
  }

  private void clear() {
    values.clear();
    valueKeyToIndex.clear();
    ListBox listBox = getListBox();
    listBox.clear();
  }

  /**
   * Set the value and display it in the select element. Add the value to the acceptable set if it is not
   * already there.
   */
  public void setValue(T value) {
    setValue(value, false);
  }

  public void setValueById(ID id) {
    if (ObjectUtils.equals(id, this.id))
      return;

    this.id = id;
    this.value = null;

    if (!isLoading) {
      if (this.id != null) {
        if (this.value == null) {
          Integer idx = valueKeyToIndex.get(id);
          if (idx != null) {
            T t = values.get(idx);
            setValue(t);
          }
        }
      }
      else
        setValue(null);
    }
  }

  public void setValue(T value, boolean fireEvents) {
    if (isLoading || value == this.value || (this.value != null && this.value.equals(value))) {
      return;
    }

    T before = this.value;
    this.value = value;
    this.id = value == null ? null : value.getId();
    updateListBox();

    if (fireEvents) {
      ValueChangeEvent.fireIfNotEqual(this, before, value);
    }
  }

  private void addValue(T value) {
    ID key = value == null ? null : value.getId();
    if (valueKeyToIndex.containsKey(key)) {
      throw new IllegalArgumentException("Duplicate value: " + value);
    }

    valueKeyToIndex.put(key, values.size());
    values.add(value);
    getListBox().addItem(renderer.render(value));
    assert values.size() == getListBox().getItemCount();
  }

  private ListBox getListBox() {
    return (ListBox) getWidget();
  }

  private void updateListBox() {
    ID key = value == null ? null : value.getId();
    if (key == null && id != null)
      key = id;

    Integer index = null;

    if (key == null && this.initial != null) {
      for (int i = 0; i < getListBox().getItemCount(); ++i) {
        String itemText = getListBox().getItemText(i);
        if (ObjectUtils.equals(itemText, initial)) {
          index = i;
          value = values.get(index);
          key = value.getId();
          break;
        }
      }
    }
    else
      index = valueKeyToIndex.get(key);

    if (index == null) {
      addValue(value);
    }

    index = valueKeyToIndex.get(key);
    getListBox().setSelectedIndex(index);
  }

  public void setDataSource(IDataSource<List<T>> source) {
    this.source = source;
    if (this.handler != null)
      this.handler.removeHandler();
    this.handler = this.source.addHandler(new DataChangedEvent.Handler() {

      @Override
      public void onDataChanged() {
        updateList();
      }
    }, DataChangedEvent.TYPE);

    this.handler = this.source.addHandler(new DataLoadEvent.Handler() {

      @Override
      public void onDataLoading() {
        clear();
        isLoading = true;
        getListBox().addItem("Loading");
        getListBox().setSelectedIndex(0);
        getListBox().setEnabled(false);
      }

      @Override
      public void onDataLoaded(boolean success) {
        loaded = success;
        if (success) {
          isLoading = false;
          updateList();
          getListBox().setEnabled(true);
        }
        else {
          clear();
          getListBox().addItem("Load failed!");
          getListBox().setSelectedIndex(0);
        }
      }
    }, DataLoadEvent.TYPE);
    updateList();
  }

  public void initRenderer(Renderer<T> renderer) {
    this.renderer = renderer;
  }

  protected void updateList() {
    List<T> data = source.getData();
    if (data == null)
      data = Collections.emptyList();
    this.setAcceptableValues(data);
  }

  public void setEnabled(boolean b) {
    getListBox().setEnabled(!isLoading && b);
  }

  public void setInitial(String string) {
    this.initial = string;
  }
}
