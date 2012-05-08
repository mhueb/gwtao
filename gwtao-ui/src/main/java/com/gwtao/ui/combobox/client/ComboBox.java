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
package com.gwtao.ui.combobox.client;

import java.util.List;

import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.IsEditor;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DecoratedPopupPanel;
import com.google.gwt.user.client.ui.IntegerBox;
import com.google.gwt.user.client.ui.PopupPanel;

/*
 * 
 * Textfield (readonly vorerst) 
 * per doppelclick popup öffnen 
 * darin eine einfach liste mit auswahl
 * 
 * Dann Auswahl erfolgt per id, erstmal ein Integer.
 * null = keine Auswahl sonst die id des content
 * Ein Adapter sorgt für mapping id -> Text 
 * Ein Adapter sorgt für auswahlbeschaffung
 * 
 * 
 * 
 */
public class ComboBox extends Composite implements IsEditor<Editor<Integer>> {

	public interface IDataFormator<T> {
		Integer getId(T t);

		String getText(T t);
	}

	public interface IDataSource<T> {
		void load(AsyncCallback<List<T>> callback);

		void load(Integer id, AsyncCallback<T> callback);
	}

	private final IntegerBox textBox;
//	private final TextBox textBox;

	public ComboBox() {
		textBox = new IntegerBox();
		initWidget(textBox);
	}

	protected PopupPanel createPopup() {
		PopupPanel p = new DecoratedPopupPanel(true, false);
		p.setStyleName("gwt-SuggestBoxPopup");
		p.setPreviewingAllNativeEvents(true);
		return p;
	}

	@Override
	public Editor<Integer> asEditor() {
		return textBox.asEditor();
	}

}
