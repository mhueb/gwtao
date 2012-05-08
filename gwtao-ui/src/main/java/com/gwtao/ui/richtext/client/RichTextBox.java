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
package com.gwtao.ui.richtext.client;

import java.io.IOException;
import java.text.ParseException;

import com.google.gwt.dom.client.Element;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.IsEditor;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.text.shared.Parser;
import com.google.gwt.text.shared.Renderer;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ValueBoxBase;
import com.google.gwt.user.client.ui.VerticalPanel;

public class RichTextBox extends Composite implements IsEditor<Editor<String>> {

	public class RichTextValueBox extends ValueBoxBase<String> {
		public RichTextValueBox(Element element) {
			super(element, new Renderer<String>() {

				@Override
				public String render(String object) {
					return object;
				}

				@Override
				public void render(String object, Appendable appendable) throws IOException {
					// TODO Auto-generated method stub
				}
			}, new Parser<String>() {

				@Override
				public String parse(CharSequence text) throws ParseException {
					return text == null ? "" : text.toString();
				}
			});

		}

		@Override
		public String getText() {
			return getHTML();
		}

		@Override
		public void setText(String text) {
			setHTML(text);
		}
	}

	private VerticalPanel content = new VerticalPanel();
	private RichTextAreaEx editor;
	private RichTextViewer viewer;
	private boolean readonly;
	private BlurHandler blurHandler;
	private final RichTextValueBox valueBox;

	public RichTextBox() {
		content.setWidth("100%");
		content.setHeight("100%");
		valueBox = new RichTextValueBox(content.getElement());
		initWidget(content);
		setReadOnly(true);
	}

	public void setReadOnly(boolean b) {
		if (this.readonly != b) {

			String html;
			if (editor != null)
				html = editor.getHTML();
			else if (viewer != null)
				html = viewer.getHTML();
			else
				html = "";

			while (content.getWidgetCount() > 0)
				content.remove(0);

			if (b) {
				editor = null;
				viewer = new RichTextViewer();
				viewer.setHTML(html);
				content.add(viewer);
				content.setCellHeight(viewer, "100%");
			} else {
				editor = new RichTextAreaEx();
				if (blurHandler != null)
					editor.addBlurHandler(blurHandler);
				viewer = null;
				editor.setHTML(html);
				content.add(editor);
				content.setCellHeight(editor, "100%");
			}

			this.readonly = b;
		}
	}
	
	@Override
	public Editor<String> asEditor() {
		return valueBox.asEditor();
	}

	public void setHTML(String html) {
		if (viewer != null)
			viewer.setHTML(html);
		else if (editor != null)
			editor.setHTML(html);
	}

	public String getHTML() {
		if (viewer != null)
			return viewer.getHTML();
		else if (editor != null)
			return editor.getHTML();
		else
			return null;
	}

	public void addBlurHandler(BlurHandler blurHandler) {
		this.blurHandler = blurHandler;
		if (editor != null)
			editor.addBlurHandler(blurHandler);
	}
}
