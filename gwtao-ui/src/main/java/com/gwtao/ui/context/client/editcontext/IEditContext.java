/* 
 * GWTAO
 * 
 * Copyright (C) 2012 Matthias Huebner
 * 
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 3 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 *
 */
package com.gwtao.ui.context.client.editcontext;

import com.gwtao.common.shared.message.IMessageSource;
import com.gwtao.ui.context.client.datacontext.IDataContext;

public interface IEditContext<T> extends IDataContext<T>, IControllerContext {
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
