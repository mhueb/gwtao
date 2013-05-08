package com.gwtao.ui.cellview.client;

import com.google.gwt.user.cellview.client.AbstractHasData;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.RangeChangeEvent;
import com.google.gwt.view.client.RangeChangeEvent.Handler;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SelectionModel;
import com.google.gwt.view.client.SetSelectionModel;
import com.google.gwt.view.client.SingleSelectionModel;
import com.gwtao.ui.data.client.selection.AbstractDataSelection;

public class CellDataSelection extends AbstractDataSelection {

  private Object[] data = new Object[0];

  public CellDataSelection(AbstractHasData<?> hasData) {
    if (!(hasData.getSelectionModel() instanceof SetSelectionModel))
      throw new IllegalArgumentException("Unhandled SelectionModel " + String.valueOf(hasData.getSelectionModel()));

    final SetSelectionModel<?> model = (SetSelectionModel<?>) hasData.getSelectionModel();

    model.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
      @Override
      public void onSelectionChange(SelectionChangeEvent event) {
        data = model.getSelectedSet().toArray();
        notifyChange();
      }
    });

    hasData.addRangeChangeHandler(new Handler() {
      @Override
      public void onRangeChange(RangeChangeEvent event) {
        model.clear();
        data = model.getSelectedSet().toArray();
        notifyChange();
      }
    });
  }

  @Override
  public Object[] getData() {
    return data;
  }

}
