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
package com.gwtao.common.shared.util;

import java.util.Collection;
import java.util.Iterator;

public class SafeIterator<E> implements Iterator<E> {
  private Collection<E> col;

  private E[] items;

  private int i;

  @SuppressWarnings("unchecked")
  public SafeIterator(Collection<E> coll) {
    this.col = coll;
    if (coll != null && coll.size() > 0)
      items = (E[]) coll.toArray(new Object[coll.size()]);
  }

  public boolean hasNext() {
    return items != null && i < items.length;
  }

  public E next() {
    return items[i++];
  }

  public void remove() {
    col.remove(items[i - 1]);
  }
}
