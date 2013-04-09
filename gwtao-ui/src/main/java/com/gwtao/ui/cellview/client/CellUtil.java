package com.gwtao.ui.cellview.client;

import java.util.ArrayList;
import java.util.List;

import org.shu4j.utils.query.SortField;

import com.google.gwt.user.cellview.client.ColumnSortList;
import com.google.gwt.user.cellview.client.ColumnSortList.ColumnSortInfo;

public class CellUtil {
  public static List<SortField> makeSortFields(ColumnSortList columnSortList) {
    List<SortField> result = new ArrayList<SortField>(columnSortList.size());
    for (int i = 0; i < columnSortList.size(); ++i) {
      ColumnSortInfo columnSortInfo = columnSortList.get(i);
      result.add(new SortField(columnSortInfo.getColumn().getDataStoreName(), columnSortInfo.isAscending()));
    }
    return result;
  }
}
