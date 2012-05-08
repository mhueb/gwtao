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
package com.gwtao.ui.util.client.action;

import java.util.ArrayList;
import java.util.List;


public class ActionWidgetHandler implements IActionWidgetHandler {
  private final List<IActionWidgetAdapter> adapter = new ArrayList<IActionWidgetAdapter>();

  @Override
  public void addAdapter(IActionWidgetAdapter adapter) {
    this.adapter.add(adapter);
  }

  public void removeAdapter(IActionWidgetAdapter adapter) {
    this.adapter.remove(adapter);
  }

  @Override
  public void update() {
    for (IActionWidgetAdapter adp : adapter)
      adp.update();
  }
}
