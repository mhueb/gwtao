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

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.imageio.stream.MemoryCacheImageOutputStream;

import com.gwtao.common.shared.util.UnexpectedErrorException;

public class ImageUtil {

  public static final class ImageUtilException extends Exception {
    private static final long serialVersionUID = 1L;

    public ImageUtilException(Throwable e) {
      super(e);
    }
  }

  public static byte[] getImageData(BufferedImage image, String formatName) {
    try {
      ByteArrayOutputStream out = new ByteArrayOutputStream();
      ImageIO.write(image, formatName, new MemoryCacheImageOutputStream(out));
      return out.toByteArray();
    }
    catch (IOException e) {
      throw new UnexpectedErrorException(e);
    }
  }

}
