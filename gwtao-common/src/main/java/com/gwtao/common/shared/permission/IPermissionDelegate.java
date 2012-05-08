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

public interface IPermissionDelegate {
  public static final IPermissionDelegate ALLOWED = new IPermissionDelegate() {

    @Override
    public void removeListener(IPermissionListener listener) {
    }

    @Override
    public Permission getPermission(Object... data) {
      return Permission.ALLOWED;
    }

    @Override
    public void addListener(IPermissionListener listener) {
    }
  };

  Permission getPermission(Object... data);

  void addListener(IPermissionListener listener);

  void removeListener(IPermissionListener listener);
}
