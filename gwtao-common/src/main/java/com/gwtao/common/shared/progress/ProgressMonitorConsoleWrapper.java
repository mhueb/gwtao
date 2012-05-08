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
 * For Debugging.<br>
 * 
 * @author jg
 * 
 */
public final class ProgressMonitorConsoleWrapper implements IProgressMonitor {
  private final IProgressMonitor progressMonitor;
  private String indent;
  private final int level;

  public ProgressMonitorConsoleWrapper(IProgressMonitor progressMonitor) {
    this.progressMonitor = progressMonitor;
    this.level = 0;
    this.indent = "";
  }

  private ProgressMonitorConsoleWrapper(IProgressMonitor progressMonitor, int level) {
    this.progressMonitor = progressMonitor;
    this.level = level;
    this.indent = "";
    for (int i = 0; i < level; i++)
      this.indent += "  ";
  }

  public void begin(String title, int steps) {
    progressMonitor.begin(title, steps);
    System.out.println(this.indent + "begin '" + title + "' with " + steps + "");
  }

  public void log(String comment, LogLevel level) {
    progressMonitor.log(comment, level);
    if (level == LogLevel.ERROR)
      System.err.println(this.indent + comment);
    else
      System.out.println(this.indent + comment);
  }

  public void step(int units) throws Cancel {
    progressMonitor.step(units);
    System.out.println(this.indent + "step " + units);
  }

  @Override
  public IProgressMonitor getSubProgress(int stepCount) {
    System.out.println(this.indent + "getSubProgress(" + stepCount + ")");
    return new ProgressMonitorConsoleWrapper(progressMonitor.getSubProgress(stepCount), this.level + 1);
  }

  @Override
  public void done() {
    progressMonitor.done();
    System.out.println(this.indent + "done.");
  }
}
