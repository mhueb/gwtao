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
package com.gwtao.common.server.i18n;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.gwtao.common.server.util.ResourceUtil;

public class ClassDescriptor {
  private final Log log = LogFactory.getLog(ClassDescriptor.class);

  private Properties props;
  private String name;

  public ClassDescriptor(Class<?> cls) {
    props = loadProperties(cls);
    name = cls.getSimpleName();
    name = getName(null);
    if (name == null) {
      name = cls.getSimpleName();
    }
  }

  private Properties loadProperties(Class<?> cls) {
    try {
      Properties props = loadProperties(cls, cls.getSimpleName() + "_" + Locale.getDefault().getLanguage());
      if (props == null)
        props = loadProperties(cls, cls.getSimpleName());
      return props;
    }
    catch (IOException e) {
      log.error("Failed to load properties of class bundle " + cls.getName(), e);
    }
    return null;
  }

  private Properties loadProperties(Class<?> cls, String name) throws IOException {
    String file = name + ".properties";

    try {
      InputStream input = ResourceUtil.openResourceStream(cls, file, "utf-8");
      if (log.isTraceEnabled())
        log.trace("Loading properties of class bundle " + cls.getName() + " from " + file);

      Properties props = new Properties();
      props.load(input);
      return props;
    }
    catch (FileNotFoundException e) {
      return null;
    }
  }

  public String getDescription(String member) {
    if (props != null) {
      String descr;
      if (member != null)
        descr = props.getProperty(member + ".description");
      else
        descr = props.getProperty("description");
      if (StringUtils.isEmpty(descr) && log.isTraceEnabled())
        log.trace(this.name + " description not set for member " + member);
      return descr;
    }
    else
      return null;
  }

  public String getName(String member) {
    if (props != null) {
      String name;
      if (member != null)
        name = props.getProperty(member + ".name");
      else
        name = props.getProperty("name");
      if (StringUtils.isEmpty(name)) {
        if (log.isWarnEnabled() && member != null)
          log.warn(this.name + ": " + member + ".name not set!");
      }
      else
        return name;
    }
    return null;
  }

}
