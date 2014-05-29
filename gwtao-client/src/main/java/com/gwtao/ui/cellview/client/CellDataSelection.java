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
package com.gwtao.ui.cellview.client;

import com.google.gwt.user.cellview.client.AbstractHasData;
import com.google.gwt.view.client.RangeChangeEvent;
import com.google.gwt.view.client.RangeChangeEvent.Handler;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SetSelectionModel;
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
        fireDataChanged();
      }
    });

    hasData.addRangeChangeHandler(new Handler() {
      @Override
      public void onRangeChange(RangeChangeEvent event) {
        model.clear();
        data = model.getSelectedSet().toArray();
        fireDataChanged();
      }
    });
  }

  @Override
  public Object[] getData() {
    return data;
  }

}
