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
 *  
 */
public final class SubProgressMonitorAdapter implements IProgressMonitor {

  private final IProgressMonitor parent;
  private final int parentStepCount;
  private int parentStepCounter;
  private String title;
  private Double parentInc;
  private double parentCounter;

  public SubProgressMonitorAdapter(IProgressMonitor parent, int parentStepCount) {
    if (parent == null)
      throw new IllegalArgumentException("parent must not be null!");
    if (parentStepCount < 1)
      throw new IllegalArgumentException("parentStepSize must be greater than 0, but is=" + parentStepCount);
    this.parent = parent;
    this.parentStepCount = parentStepCount;
  }

  public void begin(String title, int stepCount) {
    if (title==null || title.isEmpty() )
      throw new IllegalArgumentException("invalid title='" + title + "'");
    if (parentInc != null)
      throw new IllegalStateException("Not allowed to call twice!");
    this.title = title;
    parent.log(this.title, LogLevel.INFO);

    if (stepCount < 1)
      this.parentInc = 0.0;
    else {
      this.parentInc = (double) parentStepCount / (double) stepCount;
      if (Double.isNaN(this.parentInc) || Double.isInfinite(this.parentInc))
        this.parentInc = 0.0;
    }
  }

  public void log(String comment, LogLevel level) {
    parent.log(comment, level);
    // if (StringUtil.isNull(comment))
    // parent.log(this.title, level);
    // else
    // parent.log(this.title + "/" + comment, level);
  }

  public void done() {
    try {
      parent.step(parentStepCount - parentStepCounter);
      parentStepCounter = parentStepCount;
      parentCounter = 0.0;
    }
    catch (Cancel e) {
      // all is done
    }
  }

  public void step(int steps) throws Cancel {
    if (parentInc == null)
      return; // throw new IllegalStateException("Unexpected call to step. Call begin first!"); besser
    // loggen????

    parentCounter += parentInc * steps;
    int inc = (int) parentCounter; // inc may be 0
    if (parentStepCounter + inc > parentStepCount) {
      inc = parentStepCount - parentStepCounter;
      parentStepCounter = parentStepCount;
      parentCounter = 0.0;
    }
    else {
      parentCounter -= inc;
      parentStepCounter += inc;
    }
    parent.step(inc);
  }

  @Override
  public IProgressMonitor getSubProgress(int steps) {
    return new SubProgressMonitorAdapter(this, steps);
  }
}
