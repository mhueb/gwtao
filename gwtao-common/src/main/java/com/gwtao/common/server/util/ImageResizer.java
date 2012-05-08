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
package com.gwtao.common.server.util;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

public final class ImageResizer {
  public static final int SCALE_CROP = 0x0000;
  public static final int SCALE_SHRINKTOFIT = 0x0001;
  public static final int SCALE_ZOOMTOFIT = 0x0002;

  public static final int SCALE_LEFT = 0x0000;
  public static final int SCALE_HCENTER = 0x0100;
  public static final int SCALE_RIGHT = 0x0200;

  public static final int SCALE_TOP = 0x0000;
  public static final int SCALE_VCENTER = 0x1000;
  public static final int SCALE_BOTTOM = 0x2000;

  private final int width;
  private final int height;
  private final int scaleMode;

  /**
   * Resize images to new size with transparent background.
   * 
   * @param width target width
   * @param height target height
   * @param scaleMode use one or more of these:
   *          <ul>
   *          <li>{@link #SCALE_CROP} (default)
   *          <li>{@link #SCALE_SHRINKTOFIT}
   *          <li>{@link #SCALE_ZOOMTOFIT}
   *          <li>{@link #SCALE_LEFT} (default)
   *          <li>{@link #SCALE_HCENTER}
   *          <li>{@link #SCALE_RIGHT}
   *          <li>{@link #SCALE_TOP} (default)
   *          <li>{@link #SCALE_VCENTER}
   *          <li>{@link #SCALE_BOTTOM}
   *          </ul>
   * @return Scaled image
   */
  public ImageResizer(int width, int height, int scaleMode) {
    if (width <= 0)
      throw new IllegalArgumentException("Illegal width=" + width);
    if (height <= 0)
      throw new IllegalArgumentException("Illegal height=" + height);
    this.width = width;
    this.height = height;
    this.scaleMode = scaleMode;
  }

  public BufferedImage resize(BufferedImage sourceImage) {
    if (sourceImage.getWidth() == width && sourceImage.getHeight() == height)
      return sourceImage;

    BufferedImage resizedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    Graphics2D g = resizedImage.createGraphics();

    g.setBackground(new Color(255, 255, 255, 0));
    g.clearRect(0, 0, width, height);

    g.setComposite(AlphaComposite.Src);
    g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
    g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
    g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
    g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    g.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);

    final int newHeight;
    final int newWidth;
    if (isShrinkToFit(sourceImage) || isZoomToFit(sourceImage)) {
      double scale = Math.max(sourceImage.getWidth() / (double) width, sourceImage.getHeight() / (double) height);
      newHeight = (int) (sourceImage.getHeight() / scale);
      newWidth = (int) (sourceImage.getWidth() / scale);
    }
    else {
      newHeight = sourceImage.getHeight();
      newWidth = sourceImage.getWidth();
    }

    g.drawImage(sourceImage, calcXPos(newWidth), calcYPos(newHeight), newWidth, newHeight, null);
    g.dispose();

    return resizedImage;
  }

  private int calcYPos(int newHeight) {
    if (newHeight < height) {
      switch (scaleMode & 0xf000) {
      case SCALE_BOTTOM:
        return height - newHeight;
      case SCALE_VCENTER:
        return (height - newHeight) / 2;
      }
    }
    return 0;
  }

  private int calcXPos(int newWidth) {
    if (newWidth < width) {
      switch (scaleMode & 0xf00) {
      case SCALE_RIGHT:
        return width - newWidth;
      case SCALE_HCENTER:
        return (width - newWidth) / 2;
      }
    }
    return 0;
  }

  private boolean isShrinkToFit(BufferedImage sourceImage) {
    return sourceImage.getWidth() >= width && sourceImage.getHeight() >= height && (scaleMode & SCALE_SHRINKTOFIT) == SCALE_SHRINKTOFIT;
  }

  private boolean isZoomToFit(BufferedImage sourceImage) {
    return sourceImage.getWidth() <= width && sourceImage.getHeight() <= height && (scaleMode & SCALE_ZOOMTOFIT) == SCALE_ZOOMTOFIT;
  }
}
