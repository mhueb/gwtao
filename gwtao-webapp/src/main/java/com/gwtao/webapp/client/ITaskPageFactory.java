package com.gwtao.webapp.client;

import com.gwtao.ui.location.client.HasTokenConverter;

public interface ITaskPageFactory<P, M> extends IPageFactory, HasTokenConverter<P, M> {
  TaskPage<P, M> createPage();
}
