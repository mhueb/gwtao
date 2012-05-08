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

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.gwtao.common.server.util.ResourceUtil;
import com.gwtao.common.shared.i18n.I18N;

public class I18NStringFactory implements I18N.IFactory {
  private Log logger = LogFactory.getLog(I18NStringFactory.class);

  private final ILocaleGetter localeGetter;

  private final Map<Class<?>, Object> constantMap = new HashMap<Class<?>, Object>();

  private final class InvocationHandlerImplementation implements InvocationHandler {
    private static final String PROPERTIES = ".properties";
    private static final String UTF_8 = "utf-8";
    private final Class<?> cls;
    private final Map<String, Properties> properties = new HashMap<String, Properties>();

    public InvocationHandlerImplementation(Class<?> cls) {
      this.cls = cls;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
      Properties props = getProperties();
      String result = find(props, method.getName());
      if (args != null) {
        for (int i = 0; i < args.length; ++i)
          result = result.replace("{" + i + "}", args[i] == null ? "" : args[i].toString());
      }
      return result;
    }

    private String find(Properties props, String name) {
      String result = props.getProperty(name);
      if (result == null) {
        result = "##" + name + "##";
        props.setProperty(name, result);
      }
      return result;
    }

    private Properties getProperties() {
      String language = localeGetter.getLocale().getLanguage().toLowerCase();
      synchronized (properties) {
        Properties props = properties.get(language);
        if (props == null) {
          props = loadProperties(language);
          properties.put(language, props);
        }
        return props;
      }
    }

    private Properties loadProperties(String language) {
      Properties props = new Properties();
      String resourceName = cls.getSimpleName();
      InputStream resourceAsStream = openResourceStream(resourceName + "_" + language + PROPERTIES);
      if (resourceAsStream == null)
        resourceAsStream = openResourceStream(resourceName + PROPERTIES);
      if (resourceAsStream != null) {
        try {
          props.load(resourceAsStream);
        }
        catch (IOException e) {
          logger.warn("Failed to load i18n properties", e);
        }
        try {
          resourceAsStream.close();
        }
        catch (IOException e) {
          if (logger.isInfoEnabled())
            logger.info("Failed to close i18n property file", e);
        }
      }
      return props;
    }

    private InputStream openResourceStream(String file) {
      try {
        return ResourceUtil.openResourceStream(cls, file, UTF_8);
      }
      catch (IOException e) {
        if (logger.isDebugEnabled())
          logger.debug("I18N property file not available: " + file, e);
        return null;
      }
    }
  }

  public I18NStringFactory(ILocaleGetter localeGetter) {
    this.localeGetter = localeGetter;
  }

  @SuppressWarnings("unchecked")
  public <T> T create(Class<T> cls) {
    synchronized (constantMap) {
      Object t = constantMap.get(cls);
      if (t == null) {
        t = Proxy.newProxyInstance(cls.getClassLoader(), new Class[] { cls }, new InvocationHandlerImplementation(cls));
        constantMap.put(cls, t);
      }
      return (T) t;
    }
  }
}
