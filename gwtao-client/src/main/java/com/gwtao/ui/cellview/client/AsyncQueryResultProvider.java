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

import java.io.Serializable;

import org.shu4j.utils.query.QueryResult;

/**
 * Result provider for kind of service that returns a {@link QueryResult}.
 * 
 * @author mhueb
 * 
 * @param <T>
 */
public abstract class AsyncQueryResultProvider<T extends Serializable> extends AsyncDataProviderEx<QueryResult<T>, T> {

  public AsyncQueryResultProvider() {
  }

  public AsyncQueryResultProvider(int pageSize) {
    super(pageSize);
  }

  @Override
  protected void onDataLoaded(QueryResult<T> result) {
    setDisplayData(result);
  }
}
