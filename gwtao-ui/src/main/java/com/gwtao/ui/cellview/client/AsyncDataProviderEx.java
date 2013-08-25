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

  protected IWaitMask getWaitMask() {
    return new WaitMaskDummy();
  }

  @Override
  protected final void onRangeChanged(final HasData<T> display) {
    if (readLock)
      return;

    List<SortField> sortList = null;
    if (display instanceof AbstractCellTable) {
      AbstractCellTable<?> ct = (AbstractCellTable<?>) display;
      sortList = CellUtil.makeSortFields(ct.getColumnSortList());
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

    PagingInfo pi = null;
    if (pageStart != null)
      pi = new PagingInfo(pageStart, pageSize, sortList);

    readLock = true;
    onLoadStart();
    load(display, pi, new AsyncCallback<R>() {

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
          setResult(display, result);
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
  
  protected void setDisplayData(HasData<T> display, QueryResult<T> result) {
    display.setRowCount(result.getTotalCount(), false);
    display.setRowData(result.getStart(), result.getResult());
  }


  protected abstract void load(HasData<T> display, PagingInfo pi, AsyncCallback<R> callback);

  protected abstract void setResult(HasData<T> display, R result);
}