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

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

public class Privilege implements IPrivilege, IPermissionDelegate, Serializable {
  private static final long serialVersionUID = 1L;

  private String id;

  protected Privilege() {
  }

  public Privilege(String id) {
    if (StringUtils.isEmpty(id))
      throw new IllegalArgumentException("Illegal id");
    this.id = id;
  }

  public String getId() {
    return id;
  }

  public Permission getPermission(Object... data) {
    return GlobalPrivilegeProvider.get().getPermission(this.id, data);
  }

  public void addListener(IPermissionListener listener) {
    GlobalPrivilegeProvider.addListener(listener);
  }

  public void removeListener(IPermissionListener listener) {
    GlobalPrivilegeProvider.removeListener(listener);
  }

}
