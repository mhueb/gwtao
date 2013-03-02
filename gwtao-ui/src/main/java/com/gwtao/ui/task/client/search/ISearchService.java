package com.gwtao.ui.task.client.search;

import org.shu4j.utils.query.PaginationInfo;

public interface ISearchService<T> {
  void search(PaginationInfo paging, T params);
}
