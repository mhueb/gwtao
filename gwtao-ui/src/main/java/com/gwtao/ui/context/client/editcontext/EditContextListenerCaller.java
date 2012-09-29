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

import java.util.HashSet;
import java.util.Set;

import org.shu4j.utils.message.IMessageSource;
import org.shu4j.utils.util.SafeIterator;

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
