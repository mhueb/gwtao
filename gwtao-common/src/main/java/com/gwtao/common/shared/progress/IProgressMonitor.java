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

public interface IProgressMonitor {
  /**
   * Exception to indicate a user interruption.
   * 
   */
  static public class Cancel extends Exception {
    private static final long serialVersionUID = 1L;

    public Cancel() {
      super("Cancelation");
    }
  }

  /**
   * 
   * @param title
   * @param stepCount amount of steps necessary to fulfill this Progress
   */
  void begin(String title, int stepCount);

  /**
   * 
   * @param steps amount of work solved as part of the stepCount parameter handed into {@link begin}
   * @throws Cancel
   */
  void step(int steps) throws Cancel;

  /**
   * setting this progress to completed
   */
  void done();

  public static enum LogLevel {
    INFO,
    WARNING,
    ERROR,
    STDOUT,
    STDERR,
    DEBUG
  }

  /**
   * @param comment The comment to add to the log
   * @param level With {@link LogLevel#INFO} the user will see the given comment as current activity.
   */
  void log(String comment, LogLevel level);

  /**
   * 
   * @param stepCount part of the steps of this progress (see {@link begin}) delegated to a sub-progress. The
   *          sub-progress has to be started by {@link begin} with its own arbitrary amount of steps.
   * @return
   */
  IProgressMonitor getSubProgress(int stepCount);
}
