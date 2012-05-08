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
package com.gwtao.common.shared.progress;

/**
 * Dummy implementation.<br>
 * This class is usable if no special progress monitor is used, especially in tests. It is recommended to use
 * an instance of this class instead of null.
 * 
 * @author heb
 * 
 */
public final class ProgressMonitorDummy implements IProgressMonitor {
  private static ProgressMonitorDummy instance = null;

  private ProgressMonitorDummy() {
  }

  public static ProgressMonitorDummy getInstance() {
    if (null == instance) {
      instance = new ProgressMonitorDummy();
    }
    return instance;
  }

  public void begin(String title, int steps) {
  }

  public void log(String comment, LogLevel level) {
  }

  public void step(int units) throws Cancel {
  }

  @Override
  public IProgressMonitor getSubProgress(int stepCount) {
    return this;
  }

  @Override
  public void done() {
  }
}
