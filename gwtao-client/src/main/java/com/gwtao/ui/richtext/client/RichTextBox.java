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
          appendable.append(render(object));
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
      }
      else {
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
