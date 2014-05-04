package com.gwtao.ui.tree.client;

import java.util.Comparator;
import java.util.List;

import com.google.gwt.user.cellview.client.CellTree;
import com.google.gwt.user.cellview.client.TreeNode;

public class CellTreeAdapter extends
		AbstractTreeAdapter<CellTree, TreeNode> {

	public CellTreeAdapter(CellTree treePanel) {
		super(treePanel);
	}

	@Override
	protected TreeNode getRootNode() {
		return getTree().getRootTreeNode();
	}

	@Override
	protected List<TreeNode> getChildNodes(TreeNode parent) {
		
		return null;
	}

	@Override
	protected Object getUserObject(TreeNode node) {
		return node.getValue();
	}

	@Override
	protected void setUserObject(TreeNode node, Object data) {
		node.setV
	}

	@Override
	protected void refreshNode(TreeNode node, Object data) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void removeChild(TreeNode parent, TreeNode node) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void appendChild(TreeNode parent, TreeNode node) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void showLoadSign(TreeNode treeNode) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void removeLoadSign(TreeNode treeNode) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void updateExpandIcon(TreeNode node, boolean expandable) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void select(TreeNode parent) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected boolean isExpanded(TreeNode treeNode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void collapse(TreeNode parent) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void expand(TreeNode parent) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected TreeNode createNode(Object data) {
		return new CellTreeNode(data);
	}

	@Override
	protected void sort(TreeNode parent, Comparator<TreeNode> comparator) {
		// TODO Auto-generated method stub
		
	}

	

}
