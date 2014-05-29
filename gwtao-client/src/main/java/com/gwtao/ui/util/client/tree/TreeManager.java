/* 
 * Copyright 2012 GWTAO
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.gwtao.ui.util.client.tree;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwtao.ui.util.client.tree.ITreeDataSourceAsync.AsyncReply;

public final class TreeManager<T, N> {

  private static final AsyncCallback<Boolean> NOP = new AsyncCallback<Boolean>() {
    @Override
    public void onSuccess(Boolean result) {
    }

    @Override
    public void onFailure(Throwable caught) {
    }
  };

  private final ITreeAdapter<T, N> adapter;
  private final Set<Object> loadSigns = new HashSet<Object>();
  private ITreeDataSource source = ITreeDataSource.DUMMY;
  private int selectCylcleId;

  // private IWaitMask mask = WaitMaskDummy.get();

  public TreeManager(ITreeAdapter<T, N> adapter) {
    this.adapter = adapter;
  }

  public T getTree() {
    return adapter.getTree();
  }

  public void setDataSource(ITreeDataSource source) {
    this.source = source;
  }

  // public void setLoadMask(IWaitMask mask) {
  // if (mask == null)
  // this.mask = WaitMaskDummy.get();
  // this.mask = mask;
  // }

  public void update(Object data) {
    adapter.setUserObject(adapter.getRootNode(), data);
    update();
  }

  public void update(boolean deep) {
    invalidateLoadFlags(adapter.getRootNode());
    // mask.show(GWTAFConstants.c.loading());
    update(adapter.getRootNode(), deep, new AsyncCallback<Boolean>() {
      @Override
      public void onSuccess(Boolean result) {
        // mask.hide();
      }

      @Override
      public void onFailure(Throwable caught) {
        // mask.hide();
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
    doRefresh(adapter.getRootNode());
  }

  private void doRefresh(N parent) {
    for (N node : adapter.getChildNodes(parent)) {
      refreshNode(node);
      doRefresh(node);
    }
  }

  private void refreshNode(N node) {
    Object data = adapter.getUserObject(node);
    adapter.refreshNode(node, data);
    refreshExpandButton(node, data);
  }

  private void refreshExpandButton(N node, Object data) {
    boolean expandable = source.hasItems(data) || needToLoad(source, data);
    // node.setExpandable(expandable);
    adapter.updateExpandIcon(node, expandable);
  }

  private void invalidateLoadFlags(N parent) {
    loadSigns.clear();
  }

  private void syncChildren(N parent, boolean deep) {
    loadSigns.add(parent);

    Object[] childrenData = source.getItems(adapter.getUserObject(parent));
    if (childrenData != null && childrenData.length > 0) {
      doSyncChildren(parent, childrenData);
      if (deep) {
        for (N node : adapter.getChildNodes(parent))
          update(node, deep);
      }
    }
    else
      removeChildren(parent);
  }

  private void removeChildren(N parent) {
    adapter.collapse(parent);
    for (N node : adapter.getChildNodes(parent))
      adapter.removeChild(parent, node);
  }

  private void doSyncChildren(N parent, Object[] datas) {
    Set<N> oldNodes = new HashSet<N>();
    for (N node : adapter.getChildNodes(parent))
      oldNodes.add(node);

    for (int idx = 0; idx < datas.length; ++idx) {
      Object data = datas[idx];

      N node = findNode(oldNodes, data);
      if (node != null) {
        adapter.setUserObject(node, data);
        refreshNode(node);
        oldNodes.remove(node);
      }
      else {
        node = adapter.createNode(data);
        adapter.setUserObject(node, data);
        refreshNode(node);
        adapter.appendChild(parent, node);
      }
    }

    for (N node : oldNodes)
      adapter.removeChild(parent, node);

    if (this.source instanceof ITreeDataSourceComparator) {
      final ITreeDataSourceComparator comp = (ITreeDataSourceComparator) this.source;
      adapter.sort(parent, new Comparator<N>() {
        @Override
        public int compare(N o1, N o2) {
          return comp.compare(adapter.getUserObject(o1), adapter.getUserObject(o2));
        }
      });
    }
    else {
      final List<Object> index = Arrays.asList(datas);
      adapter.sort(parent, new Comparator<N>() {
        @Override
        public int compare(N o1, N o2) {
          Object d1 = adapter.getUserObject(o1);
          Object d2 = adapter.getUserObject(o2);
          int pos1 = index.indexOf(d1);
          int pos2 = index.indexOf(d2);
          return pos1 - pos2;
        }
      });
    }
  }

  private N findNode(Set<N> oldNodes, Object data) {
    for (N node : oldNodes) {
      if (iseq(adapter.getUserObject(node), data))
        return node;
    }
    return null;
  }

  private boolean iseq(Object obj1, Object obj2) {
    return obj1 == obj2 || obj1 != null && obj2 != null && obj1.equals(obj2);
  }

  private boolean needToLoad(ITreeDataSource source2, Object data) {
    if (source instanceof ITreeDataSourceAsync)
      return ((ITreeDataSourceAsync) source).needLoad(data);
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
      if (deep && (!adapter.getRootNode().equals(treeNode) && !adapter.isExpanded(treeNode))) {
        removeChildren(treeNode);
        reply.onSuccess(true);
        return true;
      }

      if (source instanceof ITreeDataSourceAsync) {
        ITreeDataSourceAsync asyncSource = (ITreeDataSourceAsync) source;
        Object userObject = adapter.getUserObject(treeNode);
        if (asyncSource.needLoad(userObject)) {
          adapter.showLoadSign(treeNode);
          asyncSource.loadAsync(userObject, new AsyncReply() {
            @Override
            public void completed(boolean success) {
              refreshNode(treeNode);
              adapter.removeLoadSign(treeNode);
              if (success) {
                syncChildren(treeNode, deep);
                adapter.expand(treeNode);
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
    find(adapter.getRootNode(), path, new HashSet<Object>(), ++selectCylcleId, new AsyncCallback<N>() {

      @Override
      public void onFailure(Throwable caught) {
      }

      @Override
      public void onSuccess(N result) {
        if (result != null)
          adapter.select(result);
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

            adapter.expand(parent);

            for (N node : adapter.getChildNodes(parent)) {
              if (probe.equals(adapter.getUserObject(node))) {
                trail.add(probe);
                find(node, path, trail, cylcleRunId, asyncCallback);
                return;
              }
            }

            if (source instanceof ITreeDataSourceInvalidator) {
              ((ITreeDataSourceInvalidator) source).invalidateChildren(adapter.getUserObject(parent));
              update(parent, false, new AsyncCallback<Boolean>() {

                @Override
                public void onFailure(Throwable caught) {
                }

                @Override
                public void onSuccess(Boolean result) {
                  for (N node : adapter.getChildNodes(parent)) {
                    if (probe.equals(adapter.getUserObject(node))) {
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
              adapter.select(parent);
          }
          else
            adapter.select(parent);
        }

        @Override
        public void onFailure(Throwable caught) {
          if (selectCylcleId == cylcleRunId)
            asyncCallback.onFailure(caught);
        }
      });
  }

  public void refresh(Iterator<?> nodePath) {
    N parent = adapter.getRootNode();

    while (parent != null && nodePath.hasNext()) {
      Object probe = nodePath.next();
      for (N node : adapter.getChildNodes(parent)) {
        if (probe.equals(adapter.getUserObject(node))) {
          adapter.setUserObject(node, probe);
          refreshNode(node);
          parent = node;
          break;
        }
      }
    }
  }
}
