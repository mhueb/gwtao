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
