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

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ResourceUtil {
  public static String loadFile(Class<?> location, String file) throws IOException {
    return new String(loadFileBin(location, file));
  }

  public static byte[] loadFileBin(Class<?> location, String file) throws IOException {
    InputStream stream = openResourceStream(location, file);

    try {
      int length = stream.available();
      byte[] buffer = new byte[length];
      int len = stream.read(buffer);
      int offset = len;
      while (offset < length) {
        len = stream.read(buffer, offset, length - offset);
        offset += len;
      }
      return buffer;
    }
    finally {
      stream.close();
    }
  }

  public static InputStream openResourceStream(Class<?> location, String file, String charset) throws IOException {
    InputStream stream = openResourceStream(location, file);
    if (charset != null)
      return new ReaderInputStream(new InputStreamReader(stream, charset));
    return stream;
  }

  public static InputStream openResourceStream(Class<?> location, String file) throws FileNotFoundException {
    String resourceName = '/' + location.getPackage().getName().replace('.', '/') + '/' + file;
    InputStream stream = location.getResourceAsStream(resourceName);
    if (stream == null)
      throw new FileNotFoundException("Resource '" + resourceName + "' not available.");
    return stream;
  }
}
