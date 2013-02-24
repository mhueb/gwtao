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
package com.gwtao.ui.context.client.editcontext;

import org.shu4j.utils.message.IMessageSource;

import com.gwtao.ui.data.client.source.IDataSource;

public interface IEditContext<T> extends IDataSource<T>, IControllerContext {
  public static enum State {
    INIT,
    VIEW,
    EDIT,
    FAILURE
  };

  boolean supportsEditState();

  boolean wantExplicitCheckOut();

  boolean wantCheckInBeforeLeave();

  boolean wantValidateOnChange();

  State getState();

  boolean isEditable();

  boolean isDirty();

  boolean isValid();

  boolean isNew();

  /**
   * Starts the edit context.<br>
   * 
   * @param parameter This is the object to start with. May also be <code>null</code>.
   */
  void start(Object parameter);

  void checkOut();

  void revert();

  void checkIn();

  void refresh();

  IMessageSource validate(boolean checkin);

  IMessageSource getServerMessages();

  /**
   * Use this function if you modified values in your model manually.
   * <p>
   * Also sends a change event to all the listening components ({@link #notifyChange})
   * <p>
   * This dirty flag is set back to <code>false</code> internally once the {@link State} changes.
   */
  void setDirty();

  boolean wantValidToCheckIn();
}
