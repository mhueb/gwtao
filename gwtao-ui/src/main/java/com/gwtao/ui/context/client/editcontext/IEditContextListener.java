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


public interface IEditContextListener {
  void onStateChange();

  void onDataStateChange();

  void onSetData();

  void onStartPre();

  void onRefreshPre();

  void onCheckInPre();

  void onCheckOutPre();

  void onRevertPre();

  void onStartPost();

  void onRefreshPost();

  void onCheckInPost();

  void onCheckOutPost();

  void onRevertPost();

  void onServiceFailed();

  void onServicePost();

  void onValidatePost(IMessageSource msgs);
}
