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
public abstract class AbstractProgressMonitor implements IProgressMonitor {
  private int stepCount;
  private int progressCounter;
  private String title;
  private boolean fireCancel;

  public void begin(String title, int stepCount) {
    if (title==null||title.isEmpty())
      throw new IllegalArgumentException("Title is null or empty!");
    if (stepCount < 1)
      throw new IllegalArgumentException("stepCount must not be less than 1, but is=" + stepCount);
    this.title = title;
    this.stepCount = stepCount;
    log(title, LogLevel.INFO);
    onBegin();
  }

  public String getTitle() {
    return title;
  }

  protected void onBegin() {
  }

  public void done() {
    progressCounter = stepCount;
    onDone();
  }

  protected void onDone() {
  }

  public void step(int inc) throws Cancel {
    if (fireCancel)
      throw new Cancel();

    if (inc > 0) {
      int next = progressCounter + inc;
      if (next > stepCount)
        next = stepCount;
      progressCounter = next;
    }

    onProgress();
  }

  public void triggerCancel() {
    fireCancel = true;
  }

  protected void onProgress() {
  }

  @Override
  public IProgressMonitor getSubProgress(int steps) {
    return new SubProgressMonitorAdapter(this, steps);
  }

  public boolean isCancelTriggered() {
    return fireCancel;
  }

  public int getStepCount() {
    return stepCount;
  }

  /**
   * @return Progress value between 0 and {@link #getStepCount()}.
   */
  public int getProgress() {
    return progressCounter;
  }

  /**
   * @return Progress value between 0 and 100.
   */
  public int getProgress100() {
    return stepCount == 0 ? 0 : progressCounter * 100 / stepCount;
  }
}
