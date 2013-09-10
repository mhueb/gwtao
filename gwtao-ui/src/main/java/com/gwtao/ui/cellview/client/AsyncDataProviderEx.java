/* 
 * Copyright 2012 Matthias Huebner
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

import java.io.Serializable;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.shu4j.utils.query.PagingInfo;
import org.shu4j.utils.query.QueryResult;
import org.shu4j.utils.query.SortField;

import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.cellview.client.AbstractCellTable;
import com.google.gwt.user.cellview.client.AbstractHasData;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.view.client.AsyncDataProvider;
import com.google.gwt.view.client.HasData;
import com.google.gwt.view.client.Range;
import com.gwtao.ui.cellview.client.i18n.CellViewConstants;
import com.gwtao.ui.util.client.GlobalExceptionHandler;
import com.gwtao.ui.util.client.mask.IWaitMask;
import com.gwtao.ui.util.client.mask.WaitMaskDummy;

/**
 * 
 * Grid laden:<br/>
 * <code>
 *     resultGrid.setVisibleRangeAndClearData(new Range(0, 25), true);
 * </code>
 * 
 * @author mhueb
 * 
 * @see AsyncQueryResultProvider
 * 
 * @param <R> Type of service result, e.g. List<T>
 * @param <T> Type of data
 */
public abstract class AsyncDataProviderEx<R, T extends Serializable> extends AsyncDataProvider<T> {

  private boolean readLock;
  private List<T> cached;

  protected IWaitMask getWaitMask() {
    return new WaitMaskDummy();
  }

  public void refresh() {
    cached = null;
    Iterator<HasData<T>> it = getDataDisplays().iterator();
    if (it.hasNext()) {
      HasData<T> display = it.next();
      display.setVisibleRangeAndClearData(new Range(0, 25), true);
    }
  }

  @Override
  protected final void onRangeChanged(final HasData<T> display) {
    if (readLock)
      return;

    if (cached != null) {
      updateRowData(0, cached);
      return;
    }

    Integer pageStart = null;
    Integer pageSize = null;
    if (display instanceof AbstractHasData) {
      AbstractHasData<?> hd = (AbstractHasData<?>) display;
      pageStart = hd.getPageStart();
      pageSize = hd.getPageSize();
      if (pageSize == 0)
        pageSize = 25;
    }

    List<SortField> sortList = null;
    if (display instanceof AbstractCellTable) {
      AbstractCellTable<?> ct = (AbstractCellTable<?>) display;
      sortList = CellUtil.makeSortFields(ct.getColumnSortList());
    }

    PagingInfo pi = null;
    if (pageStart != null)
      pi = new PagingInfo(pageStart, pageSize, sortList);

    readLock = true;

    onLoadStart();
    load(pi, new AsyncCallback<R>() {

      @Override
      public void onFailure(Throwable caught) {
        try {
          List<T> empty = Collections.emptyList();
          display.setRowCount(0);
          display.setRowData(0, empty);
          onLoadEnd();
          GlobalExceptionHandler.get().onUncaughtException(caught);
        }
        finally {
          readLock = false;
        }
      }

      @Override
      public void onSuccess(R result) {
        try {
          onDataLoaded(result);
          onLoadEnd();
        }
        finally {
          readLock = false;
        }
      }
    });
  }

  @Override
  public final void addDataDisplay(HasData<T> display) {
    readLock = true;
    super.addDataDisplay(display);
    List<T> empty = Collections.emptyList();
    display.setRowCount(0);
    display.setRowData(0, empty);
    readLock = false;
  }

  protected void onLoadStart() {
    getWaitMask().setMessage(SafeHtmlUtils.fromString(CellViewConstants.consts.loadingPleaseWait()));
    getWaitMask().show();
  }

  protected void onLoadEnd() {
    getWaitMask().hide();
  }

  protected abstract void load(PagingInfo pi, AsyncCallback<R> callback);

  protected abstract void onDataLoaded(R result);

  public void setDisplayData(List<T> result, boolean exact) {
    if (result == null)
      result = Collections.emptyList();
    this.cached = result;
    for (HasData<T> display : getDataDisplays())
      display.setRowCount(result.size(), exact);
    updateRowData(0, result);
  }

  public void setDisplayData(QueryResult<T> result) {
    if (result == null)
      result = QueryResult.emptyResult();
    this.cached = null;
    for (HasData<T> display : getDataDisplays()) {
      display.setRowCount(result.getTotalCount());
      display.setVisibleRange(result.getStart(), result.getResult().size());
      display.setRowData(result.getStart(), result.getResult());
    }
  }
}