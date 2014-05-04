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
 * $Id: AbstractTreeAdapter.java 584 2010-08-24 17:01:26Z matthuebner $
 */
package com.gwtao.ui.tree.client;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwtao.ui.tree.client.IDataSourceAsync.AsyncReply;

public abstract class AbstractTreeAdapter<T, N> {

  private static final AsyncCallback<Boolean> NOP = new AsyncCallback<Boolean>() {
    @Override
    public void onSuccess(Boolean result) {
    }

    @Override
    public void onFailure(Throwable caught) {
    }
  };

  private final T treePanel;
  private final Set<Object> loadSigns = new HashSet<Object>();
  private IDataSource source = IDataSource.DUMMY;
  private int selectCylcleId;

//  private IWaitMask mask = WaitMaskDummy.get();

  public AbstractTreeAdapter(T treePanel) {
    this.treePanel = treePanel;
  }

  public T getTree() {
    return treePanel;
  }

  public void setDataSource(IDataSource source) {
    this.source = source;
  }

//  public void setLoadMask(IWaitMask mask) {
//    if (mask == null)
//      this.mask = WaitMaskDummy.get();
//    this.mask = mask;
//  }

  public void update(Object data) {
    setUserObject(getRootNode(), data);
    update();
  }

  public void update(boolean deep) {
    invalidateLoadFlags(getRootNode());
//    mask.show(GWTAFConstants.c.loading());
    update(getRootNode(), deep, new AsyncCallback<Boolean>() {
      @Override
      public void onSuccess(Boolean result) {
//        mask.hide();
      }

      @Override
      public void onFailure(Throwable caught) {
//        mask.hide();
      }
    });
  }

  /**
   * Performs a deep update of the tree
   */
  public void update() {
    update(true);
  }

  public void refresh() {
    doRefresh(getRootNode());
  }

  private void doRefresh(N parent) {
    for (N node : getChildNodes(parent)) {
      refreshNode(node);
      doRefresh(node);
    }
  }

  private void refreshNode(N node) {
    Object data = getUserObject(node);
    refreshNode(node, data);
    refreshExpandButton(node, data);
  }

  private void refreshExpandButton(N node, Object data) {
    boolean expandable = source.hasItems(data) || needToLoad(source, data);
    // node.setExpandable(expandable);
    updateExpandIcon(node, expandable);
  }

  private void invalidateLoadFlags(N parent) {
    loadSigns.clear();
  }

  private void syncChildren(N parent, boolean deep) {
    loadSigns.add(parent);

    Object[] childrenData = source.getItems(getUserObject(parent));
    if (childrenData != null && childrenData.length > 0) {
      doSyncChildren(parent, childrenData);
      if (deep) {
        for (N node : getChildNodes(parent))
          update(node, deep);
      }
    }
    else
      removeChildren(parent);
  }

  private void removeChildren(N parent) {
    collapse(parent);
    for (N node : getChildNodes(parent))
      removeChild(parent, node);
  }

  private void doSyncChildren(N parent, Object[] datas) {
    Set<N> oldNodes = new HashSet<N>();
    for (N node : getChildNodes(parent))
      oldNodes.add(node);

    for (int idx = 0; idx < datas.length; ++idx) {
      Object data = datas[idx];

      N node = findNode(oldNodes, data);
      if (node != null) {
        setUserObject(node, data);
        refreshNode(node);
        oldNodes.remove(node);
      }
      else {
        node = createNode(data);
        setUserObject(node, data);
        refreshNode(node);

        appendChild(parent, node);
      }
    }

    for (N node : oldNodes)
      removeChild(parent, node);

    if (this.source instanceof IDataSourceComparator) {
      final IDataSourceComparator comp = (IDataSourceComparator) this.source;
      sort(parent, new Comparator<N>() {
        @Override
        public int compare(N o1, N o2) {
          return comp.compare(getUserObject(o1), getUserObject(o2));
        }
      });
    }
    else {
      final List<Object> index = Arrays.asList(datas);
      sort(parent, new Comparator<N>() {
        @Override
        public int compare(N o1, N o2) {
          Object d1 = getUserObject(o1);
          Object d2 = getUserObject(o2);
          int pos1 = index.indexOf(d1);
          int pos2 = index.indexOf(d2);
          return pos1 - pos2;
        }
      });
    }
  }

  private N findNode(Set<N> oldNodes, Object data) {
    for (N node : oldNodes) {
      if (iseq(getUserObject(node), data))
        return node;
    }
    return null;
  }

  private boolean iseq(Object obj1, Object obj2) {
	return obj1 == obj2 || obj1 != null && obj2 != null && obj1.equals(obj2);
  }

private boolean needToLoad(IDataSource source2, Object data) {
    if (source instanceof IDataSourceAsync)
      return ((IDataSourceAsync) source).needLoad(data);
    return false;
  }

  protected void onNodeCollapse(N node) {
  }

  protected boolean onNodeBeforeExpand(N node) {
    if (loadSigns.contains(node))
      return true;

    return update(node, false);
  }

  private boolean update(final N treeNode, final boolean deep) {
    return update(treeNode, deep, NOP);
  }

  private boolean update(final N treeNode, final boolean deep, final AsyncCallback<Boolean> reply) {
    try {
      if (deep && (!getRootNode().equals(treeNode) && !isExpanded(treeNode))) {
        removeChildren(treeNode);
        reply.onSuccess(true);
        return true;
      }

      if (source instanceof IDataSourceAsync) {
        IDataSourceAsync asyncSource = (IDataSourceAsync) source;
        Object userObject = getUserObject(treeNode);
        if (asyncSource.needLoad(userObject)) {
          showLoadSign(treeNode);
          asyncSource.loadAsync(userObject, new AsyncReply() {
            @Override
			public void completed(boolean success) {
              refreshNode(treeNode);
              removeLoadSign(treeNode);
              if (success) {
                syncChildren(treeNode, deep);
                expand(treeNode);
              }
              reply.onSuccess(success);
            }
          });
          return false;
        }
      }

      syncChildren(treeNode, deep);
      reply.onSuccess(true);
      return true;
    }
    catch (RuntimeException e) {
      refreshNode(treeNode);
      reply.onFailure(e);
      return false;
    }
  }

  public void select(Iterator<?> path) {
    find(getRootNode(), path, new HashSet<Object>(), ++selectCylcleId, new AsyncCallback<N>() {

      @Override
      public void onFailure(Throwable caught) {
      }

      @Override
      public void onSuccess(N result) {
        if (result != null)
          select(result);
      }
    });
  }

  private void find(final N parent, final Iterator<?> path, final Set<Object> trail, final int cylcleRunId, final AsyncCallback<N> asyncCallback) {
    if (selectCylcleId == cylcleRunId)
      update(parent, false, new AsyncCallback<Boolean>() {
        @Override
        public void onSuccess(Boolean result) {
          if (selectCylcleId != cylcleRunId)
            return;

          if (path.hasNext()) {
            final Object probe = path.next();
            if (probe == null) {
              asyncCallback.onSuccess(parent);
              return;
            }

            if (trail.contains(probe)) {
              asyncCallback.onSuccess(null);
              return; // recursion detected!
            }

            if (trail.size() > 200) {
              asyncCallback.onSuccess(null);
              return; // how many more?
            }

            expand(parent);

            for (N node : getChildNodes(parent)) {
              if (probe.equals(getUserObject(node))) {
                trail.add(probe);
                find(node, path, trail, cylcleRunId, asyncCallback);
                return;
              }
            }

            if (source instanceof IDataSourceInvalidator) {
              ((IDataSourceInvalidator) source).invalidateChildren(getUserObject(parent));
              update(parent, false, new AsyncCallback<Boolean>() {

                @Override
                public void onFailure(Throwable caught) {
                }

                @Override
                public void onSuccess(Boolean result) {
                  for (N node : getChildNodes(parent)) {
                    if (probe.equals(getUserObject(node))) {
                      trail.add(probe);
                      find(node, path, trail, cylcleRunId, asyncCallback);
                      return;
                    }
                  }
                  if (trail.isEmpty())
                    find(parent, path, trail, cylcleRunId, asyncCallback);
                }
              });
            }
            else if (trail.isEmpty())
              find(parent, path, trail, cylcleRunId, asyncCallback);
            else
              select(parent);
          }
          else
            select(parent);
        }

        @Override
        public void onFailure(Throwable caught) {
          if (selectCylcleId == cylcleRunId)
            asyncCallback.onFailure(caught);
        }
      });
  }

  public void refresh(Iterator<?> nodePath) {
    N parent = getRootNode();

    while (parent != null && nodePath.hasNext()) {
      Object probe = nodePath.next();
      for (N node : getChildNodes(parent)) {
        if (probe.equals(getUserObject(node))) {
          setUserObject(node, probe);
          refreshNode(node);
          parent = node;
          break;
        }
      }
    }
  }

  protected abstract N getRootNode();

  protected abstract List<N> getChildNodes(N parent);

  protected abstract Object getUserObject(N node);

  protected abstract void setUserObject(N node, Object data);

  protected abstract void refreshNode(N node, Object data);

  protected abstract void removeChild(N parent, N node);

  protected abstract void appendChild(N parent, N node);

  protected abstract void showLoadSign(N treeNode);

  protected abstract void removeLoadSign(N treeNode);

  protected abstract void updateExpandIcon(N node, boolean expandable);

  protected abstract void select(N parent);

  protected abstract boolean isExpanded(N treeNode);

  protected abstract void collapse(N parent);

  protected abstract void expand(N parent);

  protected abstract N createNode(Object data);

  protected abstract void sort(N parent, Comparator<N> comparator);

}
