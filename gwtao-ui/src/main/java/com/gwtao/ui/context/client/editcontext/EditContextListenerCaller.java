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

import java.util.HashSet;
import java.util.Set;

import com.gwtao.common.shared.message.IMessageSource;
import com.gwtao.common.shared.util.SafeIterator;

public class EditContextListenerCaller {
  private Set<IEditContextListener> listeners;

  public EditContextListenerCaller() {
  }

  public void add(IEditContextListener listener) {
    if (listeners == null)
      listeners = new HashSet<IEditContextListener>();
    listeners.add(listener);
  }

  public void remove(IEditContextListener listener) {
    if (listeners != null)
      listeners.remove(listener);
  }

  public void callCheckInPost() {
    SafeIterator<IEditContextListener> it = new SafeIterator<IEditContextListener>(listeners);
    while (it.hasNext()) {
      IEditContextListener next = it.next();
      next.onCheckInPost();
      next.onServicePost();
    }
  }

  public void callCheckInPre() {
    SafeIterator<IEditContextListener> it = new SafeIterator<IEditContextListener>(listeners);
    while (it.hasNext())
      it.next().onCheckInPre();
  }

  public void callCheckOutPost() {
    SafeIterator<IEditContextListener> it = new SafeIterator<IEditContextListener>(listeners);
    while (it.hasNext()) {
      IEditContextListener next = it.next();
      next.onCheckOutPost();
      next.onServicePost();
    }
  }

  public void callCheckOutPre() {
    SafeIterator<IEditContextListener> it = new SafeIterator<IEditContextListener>(listeners);
    while (it.hasNext())
      it.next().onCheckOutPre();
  }

  public void callDataStateChange() {
    SafeIterator<IEditContextListener> it = new SafeIterator<IEditContextListener>(listeners);
    while (it.hasNext())
      it.next().onDataStateChange();
  }

  public void callRefreshPost() {
    SafeIterator<IEditContextListener> it = new SafeIterator<IEditContextListener>(listeners);
    while (it.hasNext()) {
      IEditContextListener next = it.next();
      next.onRefreshPost();
      next.onServicePost();
    }
  }

  public void callRefreshPre() {
    SafeIterator<IEditContextListener> it = new SafeIterator<IEditContextListener>(listeners);
    while (it.hasNext())
      it.next().onRefreshPre();
  }

  public void callRevertPost() {
    SafeIterator<IEditContextListener> it = new SafeIterator<IEditContextListener>(listeners);
    while (it.hasNext()) {
      IEditContextListener next = it.next();
      next.onRevertPost();
      next.onServicePost();
    }
  }

  public void callRevertPre() {
    SafeIterator<IEditContextListener> it = new SafeIterator<IEditContextListener>(listeners);
    while (it.hasNext())
      it.next().onRevertPre();
  }

  public void callSetData() {
    SafeIterator<IEditContextListener> it = new SafeIterator<IEditContextListener>(listeners);
    while (it.hasNext())
      it.next().onSetData();
  }

  public void callOnStartPre() {
    SafeIterator<IEditContextListener> it = new SafeIterator<IEditContextListener>(listeners);
    while (it.hasNext())
      it.next().onStartPre();
  }

  public void callOnStartPost() {
    SafeIterator<IEditContextListener> it = new SafeIterator<IEditContextListener>(listeners);
    while (it.hasNext()) {
      IEditContextListener next = it.next();
      next.onStartPost();
      next.onServicePost();
    }
  }

  public void callStateChange() {
    SafeIterator<IEditContextListener> it = new SafeIterator<IEditContextListener>(listeners);
    while (it.hasNext())
      it.next().onStateChange();
  }

  public void callOnServiceFailed() {
    SafeIterator<IEditContextListener> it = new SafeIterator<IEditContextListener>(listeners);
    while (it.hasNext())
      it.next().onServiceFailed();
  }

  public void callOnValidatePost(IMessageSource msgs) {
    SafeIterator<IEditContextListener> it = new SafeIterator<IEditContextListener>(listeners);
    while (it.hasNext())
      it.next().onValidatePost(msgs);
  }
}
