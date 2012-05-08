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

public enum Permission {
  ALLOWED(1),
  UNALLOWED(2),
  READONLY(2),
  INVISIBLE(3);

  private int value;

  private Permission(int value) {
    this.value = value;
  }

  public boolean isAllowed() {
    return value == 1;
  }

  public boolean isVisible() {
    return value < 3;
  }

  public boolean isMoreRestrictiveThan(Permission permission) {
    return value > permission.value;
  }

  public Permission add(Permission o) {
    if (isMoreRestrictiveThan(o))
      return this;
    else
      return o;
  }

  public static Permission valueOf(Integer val) {
    if (val != null) {
      if (ALLOWED.ordinal() == val)
        return ALLOWED;
      else if (UNALLOWED.ordinal() == val)
        return UNALLOWED;
      else if (READONLY.ordinal() == val)
        return READONLY;
      else if (INVISIBLE.ordinal() == val)
        return INVISIBLE;
      throw new IllegalArgumentException("Value does not represent a permission.");
    }
    return null;
  }
}
