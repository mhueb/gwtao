/* 
 * GWTAF - GWT Application Framework
 * 
 * Copyright (C) 2008-2010 Matthias Huebner.
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
 * $Id: $
 */
package com.gwtao.ui.tree.client;

public interface IDataFormator {
  public static final IDataFormator DUMMY = new IDataFormator() {
    @Override
	public String getIcon(Object obj) {
      return obj.toString();
    }

    @Override
	public String getText(Object obj) {
      return null;
    }

    @Override
	public String getToolTip(Object obj) {
      return null;
    }
  };

  String getText(Object obj);

  String getIcon(Object obj);

  String getToolTip(Object obj);
}
