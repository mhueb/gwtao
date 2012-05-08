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
package com.gwtao.ui.richtext.client.images;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

/**
 * This {@link ClientBundle} is used for all the button icons. Using an image bundle allows all of these
 * images to be packed into a single image, which saves a lot of HTTP requests, drastically improving startup
 * time.
 */
public interface Images extends ClientBundle {
  public static final Images INSTANCE = GWT.create(Images.class);

  @Source("bold.gif")
  public ImageResource bold();

  @Source("createLink.gif")
  public ImageResource createLink();

  @Source("hr.gif")
  public ImageResource hr();

  @Source("indent.gif")
  public ImageResource indent();

  @Source("insertImage.gif")
  public ImageResource insertImage();

  @Source("italic.gif")
  public ImageResource italic();

  @Source("justifyCenter.gif")
  public ImageResource justifyCenter();

  @Source("justifyLeft.gif")
  public ImageResource justifyLeft();

  @Source("justifyRight.gif")
  public ImageResource justifyRight();

  @Source("ol.gif")
  public ImageResource ol();

  @Source("outdent.gif")
  public ImageResource outdent();

  @Source("removeFormat.gif")
  public ImageResource removeFormat();

  @Source("removeLink.gif")
  public ImageResource removeLink();

  @Source("strikeThrough.gif")
  public ImageResource strikeThrough();

  @Source("subscript.gif")
  public ImageResource subscript();

  @Source("superscript.gif")
  public ImageResource superscript();

  @Source("ul.gif")
  public ImageResource ul();

  @Source("underline.gif")
  public ImageResource underline();
}
