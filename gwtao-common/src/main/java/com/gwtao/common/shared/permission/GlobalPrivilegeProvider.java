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
package com.gwtao.common.shared.permission;

import java.util.ArrayList;
import java.util.List;

public class GlobalPrivilegeProvider {

  private static IPrivilegeProvider provider = IPrivilegeProvider.UNALLOWED;

  private static final List<IPermissionListener> listeners = new ArrayList<IPermissionListener>();

  public static void set(IPrivilegeProvider prov) {
    if (prov == null)
      provider = IPrivilegeProvider.UNALLOWED;
    else
      provider = prov;

    for (IPermissionListener l : listeners)
      l.onPermissionChanged();
  }

  public static IPrivilegeProvider get() {
    return provider;
  }

  public static void addListener(IPermissionListener listener) {
    listeners.add(listener);
  }

  public static void removeListener(IPermissionListener listener) {
    listeners.remove(listener);
  }
}
