package com.gwtao.ui.util.client;

import org.shu4j.utils.util.HasDisplayText;

import com.google.gwt.text.shared.AbstractRenderer;
import com.google.gwt.text.shared.Renderer;

public class DisplayTextRenderer<T extends HasDisplayText> extends AbstractRenderer<T> {

  private static final DisplayTextRenderer<HasDisplayText> RENDERER = new DisplayTextRenderer<HasDisplayText>();

  private DisplayTextRenderer() {
  }

  @Override
  public String render(T object) {
    return object == null ? "" : object.getDisplayText();
  }

  @SuppressWarnings("unchecked")
  public static <T extends HasDisplayText> Renderer<T> getRenderer() {
    return (Renderer<T>) RENDERER;
  }
}
