package com.gwtao.ui.cellview.client;

import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SelectionModel;
import com.google.gwt.view.client.SingleSelectionModel;
import com.gwtao.ui.data.client.selection.AbstractDataSelection;

public class CellDataSelection extends AbstractDataSelection {

  private Object[] data = new Object[0];

  public CellDataSelection(SelectionModel<?> model) {
    if (model instanceof MultiSelectionModel)
      init((MultiSelectionModel<?>) model);
    else if (model instanceof SingleSelectionModel)
      init((SingleSelectionModel<?>) model);
    else
      throw new IllegalArgumentException("Unhandled SelectionModel " + String.valueOf(model));
  }

  private void init(final MultiSelectionModel<?> model) {
    model.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {

      @Override
      public void onSelectionChange(SelectionChangeEvent event) {
        data = model.getSelectedSet().toArray();
        notifyChange();
      }
    });
  }

  private void init(final SingleSelectionModel<?> model) {
    model.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {

      @Override
      public void onSelectionChange(SelectionChangeEvent event) {
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
