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
package com.gwtao.common.server.util;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Thread safe weak reference set.
 * <p>
 * The entries of this set are ordered by time they where added.
 * 
 * @author Matthias Huebner
 * 
 * @param <E> Type of elements
 */
public final class WeakReferenceSet<E> implements Set<E> {

  private static final class WeakIterator<T> implements Iterator<T> {
    private final WeakReferenceSet<T> set;
    private final List<WeakReference<T>> copy;
    private T next;
    private T last;
    private int pos = 0;

    public WeakIterator(WeakReferenceSet<T> set) {
      this.set = set;
      this.copy = set.set;
      adjust();
      last = next;
    }

    @Override
    public boolean hasNext() {
      return next != null;
    }

    @Override
    public T next() {
      last = next;
      adjust();
      return last;
    }

    private void adjust() {
      next = null;
      while (next == null && pos < set.size()) {
        next = copy.get(pos++).get();
      }
    }

    @Override
    public void remove() {
      if (last == null)
        throw new IllegalStateException("Unexpected remove");
      set.remove(last);
    }
  }

  private List<WeakReference<E>> set = new ArrayList<WeakReference<E>>();

  @Override
  public boolean add(E e) {
    if (e == null)
      return false;

    synchronized (set) {
      if (exists(e, set))
        return false;

      List<WeakReference<E>> copy = new ArrayList<WeakReference<E>>(set);
      copy.add(new WeakReference<E>(e));
      collectGarbage(copy);
      set = copy;
      return true;
    }
  }

  @Override
  public boolean addAll(Collection<? extends E> c) {
    if (c == null)
      return false;

    synchronized (set) {
      List<WeakReference<E>> copy = new ArrayList<WeakReference<E>>(set);

      boolean changed = false;
      for (E e : c) {
        if (!exists(e, copy)) {
          copy.add(new WeakReference<E>(e));
          changed = true;
        }
      }

      if (changed) {
        collectGarbage(copy);
        set = copy;
        return true;
      }
      return false;
    }
  }

  @Override
  public void clear() {
    synchronized (set) {
      set = new ArrayList<WeakReference<E>>();
    }
  }

  @Override
  public boolean contains(Object o) {
    return exists(o, set);
  }

  @Override
  public boolean containsAll(Collection<?> c) {
    List<WeakReference<E>> copy = set;
    for (Object o : c) {
      if (!exists(o, copy))
        return false;
    }
    return true;
  }

  @Override
  public boolean isEmpty() {
    return set.isEmpty() || size() == 0;
  }

  @Override
  public Iterator<E> iterator() {
    return new WeakIterator<E>(this);
  }

  @Override
  public boolean remove(Object o) {
    if (o == null)
      return false;

    synchronized (set) {
      List<WeakReference<E>> copy = new ArrayList<WeakReference<E>>(set);

      boolean changed = false;
      Iterator<WeakReference<E>> it = copy.iterator();
      while (it.hasNext()) {
        E i = it.next().get();
        if (i != null && o.equals(i)) {
          it.remove();
          changed = true;
          break;
        }
      }

      if (changed) {
        collectGarbage(copy);
        set = copy;
        return true;
      }

      return false;
    }
  }

  @Override
  public boolean removeAll(Collection<?> c) {
    if (c == null)
      return false;

    synchronized (set) {
      List<WeakReference<E>> copy = new ArrayList<WeakReference<E>>(set);

      boolean changed = false;
      Iterator<WeakReference<E>> it = copy.iterator();
      while (it.hasNext()) {
        E i = it.next().get();
        if (i != null && c.contains(i)) {
          it.remove();
          changed = true;
        }
      }

      if (changed) {
        collectGarbage(copy);
        set = copy;
        return true;
      }

      return false;
    }
  }

  @Override
  public boolean retainAll(Collection<?> c) {
    if (c == null)
      return false;

    synchronized (set) {
      List<WeakReference<E>> copy = new ArrayList<WeakReference<E>>(set);

      boolean changed = false;
      Iterator<WeakReference<E>> it = copy.iterator();
      while (it.hasNext()) {
        E i = it.next().get();
        if (i != null && !c.contains(i)) {
          it.remove();
          changed = true;
        }
      }

      if (changed) {
        collectGarbage(copy);
        set = copy;
        return true;
      }

      return false;
    }
  }

  @Override
  public int size() {
    int size = 0;
    for (WeakReference<E> s : set) {
      if (s.get() != null)
        size++;
    }
    return size;
  }

  @Override
  public Object[] toArray() {
    return makeList().toArray();
  }

  @Override
  public <T> T[] toArray(T[] a) {
    return makeList().toArray(a);
  }

  private boolean exists(Object e, List<WeakReference<E>> copy) {
    for (WeakReference<E> we : copy) {
      E i = we.get();
      if (i != null && i.equals(e))
        return true;
    }
    return false;
  }

  private List<E> makeList() {
    List<E> result = new ArrayList<E>();
    for (WeakReference<E> s : set) {
      E e = s.get();
      if (e != null)
        result.add(e);
    }
    return result;
  }

  private static <T> void collectGarbage(Collection<WeakReference<T>> weakColl) {
    Iterator<WeakReference<T>> it = weakColl.iterator();
    while (it.hasNext()) {
      if (it.next().get() == null)
        it.remove();
    }
  }
}
