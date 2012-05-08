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
 * Console implementation.<br>
 * This class is usable if no special progress monitor is used, especially in tests. It is recommended to use
 * an instance of this class instead of null.
 * 
 * @author heb
 * 
 */
public final class ProgressMonitorConsole implements IProgressMonitor {
  private static ProgressMonitorConsole instance = null;
  private int steps;
  private int count;

  private ProgressMonitorConsole() {
  }

  public static ProgressMonitorConsole getInstance() {
    if (null == instance) {
      instance = new ProgressMonitorConsole();
    }
    return instance;
  }

  public void begin(String title, int steps) {
    this.steps = steps;
    this.count = 0;
    System.out.println("begin '" + title + "' with " + steps + "\n");
  }

  public void log(String comment, LogLevel level) {
    if (level == LogLevel.ERROR || level == LogLevel.STDERR)
      System.err.println(comment);
    else
      System.out.println(comment);
  }

  public void step(int units) throws Cancel {
    count += units;
    if (steps == 0)
      System.err.println("Called step without begin!             \r");
    else
      System.out.println("" + count * 100 / steps + "%             \r");
  }

  @Override
  public IProgressMonitor getSubProgress(int stepCount) {
    return new SubProgressMonitorAdapter(this, stepCount);
  }

  @Override
  public void done() {
    System.out.println("done.\n");
  }
}
