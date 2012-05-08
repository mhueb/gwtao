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
package com.gwtao.ui.portal.client.view;

import java.util.List;

import com.gwtao.ui.portal.client.part.IPortalPart;

/**
 * A portlet stack contains one ore more views.
 * 
 * @author mah
 * 
 */
public interface IPortalViewStack<T extends IPortalView> extends IPortalPart {
  void add(T view);

  void remove(T view);

  void activate(T view);

  boolean contains(T view);

  T getActiveView();

  List<T> getViews();

  void addListener(IPortalViewStackListener documentStackListener);
}
