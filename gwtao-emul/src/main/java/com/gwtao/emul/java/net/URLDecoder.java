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
package java.net;

import java.io.UnsupportedEncodingException;

import com.google.gwt.http.client.URL;

public class URLDecoder {
  private static final String UTF_8 = "UTF-8";

  public static String decode(String s, String enc) throws UnsupportedEncodingException {
    if (!UTF_8.equals(enc))
      throw new UnsupportedEncodingException(enc);

    return URL.decodeComponent(s);
  }
}
