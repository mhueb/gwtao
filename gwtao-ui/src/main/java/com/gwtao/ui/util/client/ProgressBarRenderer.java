package com.gwtao.ui.util.client;

import org.apache.commons.lang.StringUtils;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;

public final class ProgressBarRenderer {

  public interface ProgressBarCss extends CssResource {
  }

  public interface ProgressBarResources extends ClientBundle {

    @Source("progressbar.css")
    public ProgressBarCss css();
  }

  private static final String BARCOLOR = "a0a0f0";

  public static final ProgressBarResources RESOURCE = GWT.create(ProgressBarResources.class);

  private String color = BARCOLOR;
  private String frameClass = "gwtao-progress-frame";
  private String progressBarClass = "gwtao-progress-bar";
  private String progressTextClass = "gwtao-progress-text";
  private String labelClass = "gwtao-progress-label";

  public ProgressBarRenderer() {
    RESOURCE.css().ensureInjected();
  }

  public void setColor(String color) {
    if (StringUtils.isBlank(color))
      color = BARCOLOR;
    this.color = color;
  }

  public String generate(Integer percent, String msg) {
    int width;
    if (percent != null)
      width = Math.max(0, Math.min(percent, 100));
    else
      width = 0;

    StringBuilder buff = new StringBuilder();
    buff.append("<div class=\"").append(frameClass).append("\">");
    buff.append("<div class=\"").append(progressBarClass).append("\" style=\"background-color: #");
    buff.append(color);
    buff.append("; width: ");
    buff.append(width);
    buff.append("%;\">&nbsp;</div>"); // ; height:100%

    buff.append("<div class=\"").append(progressTextClass).append("\">");
    if (msg == null) {
      if (percent != null) {
        buff.append(percent);
        buff.append('%');
      }
    }
    else
      buff.append(msg);

    buff.append("</div></div>");

    return buff.toString();
  }

  public String generate(Integer percent, String msg, String labelText, int labelWidth, int height) {
    StringBuilder buff = new StringBuilder();

    buff.append("<div style=\"height:" + height + "px;\">");
    if (labelText != null) {
      buff.append("<label class=\"").append(labelClass).append("\" style=\"float:left;width:").append(labelWidth).append("px;\">");
      buff.append(labelText);
      buff.append("</label>");
    }
    buff.append("<span class=\"").append(labelClass).append("\" style=\"width:100%;height:" + (height) + "px;\">");
    buff.append(generate(percent, msg));
    buff.append("</span>");
    buff.append("</div>");

    return buff.toString();
  }
}
