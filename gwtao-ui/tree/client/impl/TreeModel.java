package com.gwtao.ui.tree.client.impl;

import com.google.gwt.view.client.SelectionModel;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.gwt.view.client.TreeViewModel;

import de.itinformatik.dadi.dto.search.SearchResultDTO;

public class TreeModel implements TreeViewModel {

	private final SelectionModel<SearchResultDTO> selectionModel = new SingleSelectionModel<SearchResultDTO>();
	
	@Override
	public <T> NodeInfo<?> getNodeInfo(T value) {
		return new TreeModelItem(selectionModel);
	}

	@Override
	public boolean isLeaf(Object value) {
		return value == null ? false : ()
	}

}
