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
package com.gwtao.ui.portal.client.view;

import com.google.gwt.user.client.ui.Widget;
import com.gwtao.ui.util.client.IDisplayableItem;

public interface IPortalView extends IDisplayableItem {
  void setViewContext(IPortalViewContext context);

  IPortalViewContext getViewContext();

  void init();

  Widget getWidget();

  String canClose();

  boolean isClosable();

  void onOpen();

  void onActivate();

  void onDeactivate();

  void onClose();
}
