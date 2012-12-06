/* 
 * Copyright 2012 Matthias Huebner
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
package com.gwtao.ui.tree.client;

import java.util.Comparator;
import java.util.List;

import com.google.gwt.user.cellview.client.CellTree;
import com.google.gwt.user.cellview.client.TreeNode;

public class CellTreeAdapter implements ITreeAdapter<CellTree, TreeNode> {

  private final CellTree treePanel;

  public CellTreeAdapter(CellTree treePanel) {
    this.treePanel = treePanel;
  }

  @Override
  public CellTree getTree() {
    return treePanel;
  }

  @Override
  public TreeNode getRootNode() {
    return treePanel.getRootTreeNode();
  }

  @Override
  public List<TreeNode> getChildNodes(TreeNode parent) {

    return null;
  }

  @Override
  public Object getUserObject(TreeNode node) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void setUserObject(TreeNode node, Object data) {
    // TODO Auto-generated method stub

  }

  @Override
  public void refreshNode(TreeNode node, Object data) {
    // TODO Auto-generated method stub

  }

  @Override
  public void removeChild(TreeNode parent, TreeNode node) {
    // TODO Auto-generated method stub

  }

  @Override
  public void appendChild(TreeNode parent, TreeNode node) {
    // TODO Auto-generated method stub

  }

  @Override
  public void showLoadSign(TreeNode treeNode) {
    // TODO Auto-generated method stub

  }

  @Override
  public void removeLoadSign(TreeNode treeNode) {
    // TODO Auto-generated method stub

  }

  @Override
  public void updateExpandIcon(TreeNode node, boolean expandable) {
    // TODO Auto-generated method stub

  }

  @Override
  public void select(TreeNode parent) {
    // TODO Auto-generated method stub

  }

  @Override
  public boolean isExpanded(TreeNode treeNode) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public void collapse(TreeNode parent) {
    // TODO Auto-generated method stub

  }

  @Override
  public void expand(TreeNode parent) {
    // TODO Auto-generated method stub

  }

  @Override
  public TreeNode createNode(Object data) {
    return null;// new CellTreeNode(data);
  }

  @Override
  public void sort(TreeNode parent, Comparator<TreeNode> comparator) {
    // TODO Auto-generated method stub

  }

}
