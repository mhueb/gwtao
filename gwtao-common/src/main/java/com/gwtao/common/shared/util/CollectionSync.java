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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Synchronize collections.<br>
 * This class makes it easy to synchronize two collections. Both collections can have same or different
 * element types.
 * 
 * @author matthuebner
 * 
 */
public class CollectionSync {

  /**
   * Interface to read a collection.
   * 
   * @param <ID> Type of element identifier
   * @param <E> Type of element
   */
  public static interface ICollectionReader<ID, E> {
    ID getId(E elem);

    Collection<? extends E> getElements();
  }

  /**
   * Interface to read and change a collection
   * 
   * @param <ID> Type of element identifier
   * @param <E> Type of element
   */
  public static interface ICollectionChanger<ID, E> extends ICollectionReader<ID, E> {
    void remove(E elem);

    void add(int pos, E elem);

    void finish();

    void start();
  }

  /**
   * Interface to convert elements from source to target type.
   * 
   * @param <S> Type of source element
   * @param <T> Type of target element
   */
  public static interface IElementConverter<S, T> {
    T create(S source, int pos);

    void update(T target, S source, int pos);
  }

  private static final class EA<E> {
    private final E e;
    private final int pos;

    public EA(E e, int pos) {
      this.e = e;
      this.pos = pos;
    }

    public E getElement() {
      return e;
    }

    public int getPos() {
      return pos;
    }
  }

  private static class CollectionReader<ID, E> {
    protected final Map<ID, EA<E>> existingMap = new HashMap<ID, EA<E>>();
    protected final List<EA<E>> newList = new ArrayList<EA<E>>();

    public CollectionReader(ICollectionReader<ID, E> acc) {
      Collection<? extends E> elements = acc.getElements();
      if (elements != null) {
        int pos = 0;
        for (E l : elements) {
          ID id = acc.getId(l);
          EA<E> value = new EA<E>(l, pos++);
          if (id != null)
            existingMap.put(id, value);
          else
            newList.add(value);
        }
      }
    }

    public Set<ID> getExisting() {
      Set<ID> keySet = existingMap.keySet();
      if (keySet == null)
        return Collections.emptySet();
      return keySet;
    }

    public Collection<EA<E>> getNew() {
      return newList;
    }

    public EA<E> getElement(ID id) {
      return existingMap.get(id);
    }
  }

  public static class CollectionChanger<ID, E> extends CollectionReader<ID, E> {
    private final ICollectionChanger<ID, E> acc;

    public CollectionChanger(ICollectionChanger<ID, E> acc) {
      super(acc);
      this.acc = acc;
    }

    public void removeDeleted(Set<ID> existing) {
      for (ID id : new HashSet<ID>(getExisting())) {
        if (!existing.contains(id))
          acc.remove(existingMap.remove(id).getElement());
      }
    }

    public void add(E elem, int pos) {
      acc.add(pos, elem);
    }
  }

  /**
   * Perform collection synchronization.
   * 
   * @param <ID> Type of element identifier. This must be the same for source and target elements.
   * @param <S> Type of source elements
   * @param <T> Type of target elements
   * @param source Reader for source elements
   * @param target Changer for target elements
   * @param converter Converter for elements
   */
  public static <ID, S, T> void sync(ICollectionReader<ID, S> source, ICollectionChanger<ID, T> target, IElementConverter<S, T> converter) {
    target.start();

    CollectionReader<ID, S> sourceAdp = new CollectionReader<ID, S>(source);
    CollectionChanger<ID, T> targetAdp = new CollectionChanger<ID, T>(target);

    targetAdp.removeDeleted(sourceAdp.getExisting());

    for (ID id : sourceAdp.getExisting()) {
      EA<S> ea = sourceAdp.getElement(id);
      EA<T> element = targetAdp.getElement(id);
      if (element == null) {
        T t = converter.create(ea.getElement(), ea.getPos());
        targetAdp.add(t, ea.getPos());
      }
      else
        converter.update(element.getElement(), ea.getElement(), ea.getPos());
    }

    for (EA<S> ea : sourceAdp.getNew()) {
      T t = converter.create(ea.getElement(), ea.getPos());
      targetAdp.add(t, ea.getPos());
    }

    target.finish();
  }

}
