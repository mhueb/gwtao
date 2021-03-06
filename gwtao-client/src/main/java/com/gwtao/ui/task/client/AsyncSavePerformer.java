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
package com.gwtao.ui.task.client;

import com.google.gwt.core.shared.GWT;
import com.gwtao.ui.task.client.i18n.DataConstants;

public abstract class AsyncSavePerformer<M> implements IAsyncTaskPerformer<M> {
  private static final DataConstants CONSTANTS = GWT.create(DataConstants.class);

  @Override
  public String getDisplayText() {
    return CONSTANTS.save();
  }

  @Override
  public String getWaitMessage() {
    return CONSTANTS.saving();
  }

  @Override
  public String getDisplayIcon() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String getDisplayTooltip() {
    // TODO Auto-generated method stub
    return null;
  }
}
