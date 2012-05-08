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

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.RichTextArea;
import com.google.gwt.user.client.ui.RichTextArea.Formatter;
import com.google.gwt.user.client.ui.ToggleButton;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.gwtao.ui.richtext.client.images.Images;

/**
 * A sample toolbar for use with {@link RichTextArea}. It provides a simple UI for all rich text formatting,
 * dynamically displayed only for the available functionality.
 */
public class RichTextToolbar extends Composite {
  /**
   * We use an inner EventListener class to avoid exposing event methods on the RichTextToolbar itself.
   */
  private class EventListener implements ClickHandler, ChangeHandler, KeyUpHandler {
    @Override
    public void onClick(ClickEvent event) {
      Object sender = event.getSource();
      if (sender == bold) {
        formatter.toggleBold();
      }
      else if (sender == italic) {
        formatter.toggleItalic();
      }
      else if (sender == underline) {
        formatter.toggleUnderline();
      }
      else if (sender == subscript) {
        formatter.toggleSubscript();
      }
      else if (sender == superscript) {
        formatter.toggleSuperscript();
      }
      else if (sender == strikethrough) {
        formatter.toggleStrikethrough();
      }
      else if (sender == indent) {
        formatter.rightIndent();
      }
      else if (sender == outdent) {
        formatter.leftIndent();
      }
      else if (sender == justifyLeft) {
        formatter.setJustification(RichTextArea.Justification.LEFT);
      }
      else if (sender == justifyCenter) {
        formatter.setJustification(RichTextArea.Justification.CENTER);
      }
      else if (sender == justifyRight) {
        formatter.setJustification(RichTextArea.Justification.RIGHT);
      }
      else if (sender == insertImage) {
        String url = Window.prompt("Enter an image URL:", "http://");
        if (url != null) {
          formatter.insertImage(url);
        }
      }
      else if (sender == createLink) {
        String url = Window.prompt("Enter a link URL:", "http://");
        if (url != null) {
          formatter.createLink(url);
        }
      }
      else if (sender == removeLink) {
        formatter.removeLink();
      }
      else if (sender == hr) {
        formatter.insertHorizontalRule();
      }
      else if (sender == ol) {
        formatter.insertOrderedList();
      }
      else if (sender == ul) {
        formatter.insertUnorderedList();
      }
      else if (sender == removeFormat) {
        formatter.removeFormat();
      }
      else if (sender == richText) {
        // We use the RichTextArea's onKeyUp event to update the toolbar status.
        // This will catch any cases where the user moves the cursur using the
        // keyboard, or uses one of the browser's built-in keyboard shortcuts.
        updateStatus();
      }
    }

    @Override
    public void onChange(ChangeEvent event) {
      Object sender = event.getSource();
      if (sender == backColors) {
        formatter.setBackColor(backColors.getValue(backColors.getSelectedIndex()));
        backColors.setSelectedIndex(0);
      }
      else if (sender == foreColors) {
        formatter.setForeColor(foreColors.getValue(foreColors.getSelectedIndex()));
        foreColors.setSelectedIndex(0);
      }
      else if (sender == fonts) {
        formatter.setFontName(fonts.getValue(fonts.getSelectedIndex()));
        fonts.setSelectedIndex(0);
      }
      else if (sender == fontSizes) {
        formatter.setFontSize(fontSizesConstants[fontSizes.getSelectedIndex() - 1]);
        fontSizes.setSelectedIndex(0);
      }
    }

    @Override
    public void onKeyUp(KeyUpEvent event) {
      Object sender = event.getSource();
      if (sender == richText) {
        // We use the RichTextArea's onKeyUp event to update the toolbar status.
        // This will catch any cases where the user moves the cursur using the
        // keyboard, or uses one of the browser's built-in keyboard shortcuts.
        updateStatus();
      }
    }
  }

  private static final RichTextArea.FontSize[] fontSizesConstants = new RichTextArea.FontSize[] {
      RichTextArea.FontSize.XX_SMALL,
      RichTextArea.FontSize.X_SMALL,
      RichTextArea.FontSize.SMALL,
      RichTextArea.FontSize.MEDIUM,
      RichTextArea.FontSize.LARGE,
      RichTextArea.FontSize.X_LARGE,
      RichTextArea.FontSize.XX_LARGE };

  private Images images = Images.INSTANCE;
  private RichTextConstants strings = (RichTextConstants) GWT.create(RichTextConstants.class);
  private EventListener listener = new EventListener();

  private RichTextArea richText;

  private VerticalPanel outer = new VerticalPanel();
  private HorizontalPanel topPanel = new HorizontalPanel();
  private HorizontalPanel bottomPanel = new HorizontalPanel();
  private ToggleButton bold;
  private ToggleButton italic;
  private ToggleButton underline;
  private ToggleButton subscript;
  private ToggleButton superscript;
  private ToggleButton strikethrough;
  private PushButton indent;
  private PushButton outdent;
  private PushButton justifyLeft;
  private PushButton justifyCenter;
  private PushButton justifyRight;
  private PushButton hr;
  private PushButton ol;
  private PushButton ul;
  private PushButton insertImage;
  private PushButton createLink;
  private PushButton removeLink;
  private PushButton removeFormat;

  private ListBox backColors;
  private ListBox foreColors;
  private ListBox fonts;
  private ListBox fontSizes;

  private Formatter formatter;

  /**
   * Creates a new toolbar that drives the given rich text area.
   * 
   * @param richText the rich text area to be controlled
   */
  public RichTextToolbar(RichTextArea richText) {
    this(richText, true);
  }

  public RichTextToolbar(RichTextArea richText, boolean addBottomPanel) {
    this.richText = richText;
    this.formatter = richText.getFormatter();

    outer.add(topPanel);
    if (addBottomPanel)
      outer.add(bottomPanel);

    initWidget(outer);
    setStyleName("gwtaf-RichTextToolbar");
    richText.addStyleName("gwtaf-hasRichTextToolbar");

    if (formatter != null) {
      topPanel.add(bold = createToggleButton(images.bold(), strings.bold()));
      topPanel.add(italic = createToggleButton(images.italic(), strings.italic()));
      topPanel.add(underline = createToggleButton(images.underline(), strings.underline()));
      topPanel.add(subscript = createToggleButton(images.subscript(), strings.subscript()));
      topPanel.add(superscript = createToggleButton(images.superscript(), strings.superscript()));
      topPanel.add(justifyLeft = createPushButton(images.justifyLeft(), strings.justifyLeft()));
      topPanel.add(justifyCenter = createPushButton(images.justifyCenter(), strings.justifyCenter()));
      topPanel.add(justifyRight = createPushButton(images.justifyRight(), strings.justifyRight()));
      topPanel.add(strikethrough = createToggleButton(images.strikeThrough(), strings.strikeThrough()));
      topPanel.add(indent = createPushButton(images.indent(), strings.indent()));
      topPanel.add(outdent = createPushButton(images.outdent(), strings.outdent()));
      topPanel.add(hr = createPushButton(images.hr(), strings.hr()));
      topPanel.add(ol = createPushButton(images.ol(), strings.ol()));
      topPanel.add(ul = createPushButton(images.ul(), strings.ul()));
      topPanel.add(insertImage = createPushButton(images.insertImage(), strings.insertImage()));
      topPanel.add(createLink = createPushButton(images.createLink(), strings.createLink()));
      topPanel.add(removeLink = createPushButton(images.removeLink(), strings.removeLink()));
      topPanel.add(removeFormat = createPushButton(images.removeFormat(), strings.removeFormat()));
      bottomPanel.add(backColors = createColorList("Background"));
      bottomPanel.add(foreColors = createColorList("Foreground"));
      bottomPanel.add(fonts = createFontList());
      bottomPanel.add(fontSizes = createFontSizes());

      // We only use these listeners for updating status, so don't hook them up
      // unless at least basic editing is supported.
      richText.addKeyUpHandler(listener);
      richText.addClickHandler(listener);
    }
  }

  private ListBox createColorList(String caption) {
    ListBox lb = new ListBox();
    lb.addChangeHandler(listener);
    lb.setVisibleItemCount(1);

    lb.addItem(caption);
    lb.addItem(strings.white(), "white");
    lb.addItem(strings.black(), "black");
    lb.addItem(strings.red(), "red");
    lb.addItem(strings.green(), "green");
    lb.addItem(strings.yellow(), "yellow");
    lb.addItem(strings.blue(), "blue");
    return lb;
  }

  private ListBox createFontList() {
    ListBox lb = new ListBox();
    lb.addChangeHandler(listener);
    lb.setVisibleItemCount(1);

    lb.addItem(strings.font(), "");
    lb.addItem(strings.normal(), "");
    lb.addItem("Times New Roman", "Times New Roman");
    lb.addItem("Arial", "Arial");
    lb.addItem("Courier New", "Courier New");
    lb.addItem("Georgia", "Georgia");
    lb.addItem("Trebuchet", "Trebuchet");
    lb.addItem("Verdana", "Verdana");
    return lb;
  }

  private ListBox createFontSizes() {
    ListBox lb = new ListBox();
    lb.addChangeHandler(listener);
    lb.setVisibleItemCount(1);

    lb.addItem(strings.size());
    lb.addItem(strings.xxsmall());
    lb.addItem(strings.xsmall());
    lb.addItem(strings.small());
    lb.addItem(strings.medium());
    lb.addItem(strings.large());
    lb.addItem(strings.xlarge());
    lb.addItem(strings.xxlarge());
    return lb;
  }

  private PushButton createPushButton(ImageResource img, String tip) {
    PushButton pb = new PushButton(new Image(img));
    pb.setStylePrimaryName("gwtaf-RichTextToolbar-Button");
    pb.addClickHandler(listener);
    pb.setTitle(tip);
    return pb;
  }

  private ToggleButton createToggleButton(ImageResource img, String tip) {
    ToggleButton tb = new ToggleButton(new Image(img));
    tb.setStylePrimaryName("gwtaf-RichTextToolbar-Toggle");
    tb.addClickHandler(listener);
    tb.setTitle(tip);
    return tb;
  }

  /**
   * Updates the status of all the stateful buttons.
   */
  private void updateStatus() {
    if (formatter != null) {
      bold.setDown(formatter.isBold());
      italic.setDown(formatter.isItalic());
      underline.setDown(formatter.isUnderlined());
      subscript.setDown(formatter.isSubscript());
      superscript.setDown(formatter.isSuperscript());
      strikethrough.setDown(formatter.isStrikethrough());
    }
  }

  protected void flushFocus() {
    bold.setFocus(true);
    bold.setFocus(false);
  }
}
