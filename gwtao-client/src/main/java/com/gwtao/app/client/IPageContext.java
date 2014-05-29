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
package com.gwtao.app.client;

import org.shu4j.utils.permission.IPermissionProvider;

import com.google.gwt.event.shared.EventBus;
import com.gwtao.ui.util.client.mask.IWaitMask;

public interface IPageContext {
  IPage getPage();
  
  IPageHandler getHandler();

  void switchToken(String parameter);

  String getParameter();

  String canClose();

  void show();

  void close();

  void updateTitle();

  EventBus getEventBus();

  IPermissionProvider getPermissionProvider();

  IWaitMask getWaitMask();
  
  void addHandler(IPageHandler handler);
}
