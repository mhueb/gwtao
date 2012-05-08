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
package com.gwtao.ui.context.client.datacontext;

import java.util.ArrayList;
import java.util.List;

import com.gwtao.common.shared.permission.Permission;

public abstract class DataContext<T> implements IDataContext<T> {
  private List<IDataChangeListener> listeners;
  boolean loopLock = true;

  @Override
  public void addChangeListener(IDataChangeListener listener) {
    if (listeners == null)
      listeners = new ArrayList<IDataChangeListener>();
    listeners.remove(listener);
    listeners.add(listener);
  }

  @Override
  public void removeChangeListener(IDataChangeListener listener) {
    if (listeners != null)
      listeners.remove(listener);
  }

  @Override
  public void notifyChange() {
    if (loopLock) {
      try {
        loopLock = false;
        if (listeners != null) {
          IDataChangeListener[] current = listeners.toArray(new IDataChangeListener[listeners.size()]);
          for (IDataChangeListener listener : current)
            listener.onDataChange();
        }
      }
      finally {
        loopLock = true;
      }
    }
  }

  @Override
  public boolean isDataNull() {
    return getData() == null;
  }

  @Override
  public Permission getPermission() {
    return Permission.ALLOWED;
  }

}
