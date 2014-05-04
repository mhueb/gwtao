/* 
 * Copyright 2012 Matthias Huebner
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.gwtao.rpc.fileio.shared;

import java.io.Serializable;

public class FileUploadStatus implements Serializable {
  private static final long serialVersionUID = 1L;

  private String ident;
  private String filename;
  private long bytesReceived;
  private long bytesTotal;
  private int item;
  private int itemCount;

  private int processingProgress;

  public FileUploadStatus() {
  }

  public FileUploadStatus(String ident) {
    this.ident = ident;
  }

  public String getIdent() {
    return ident;
  }

  public String getFilename() {
    return filename;
  }

  public long getBytesReceived() {
    return bytesReceived;
  }

  public long getBytesTotal() {
    return bytesTotal;
  }

  public int getItem() {
    return item;
  }

  public int getItemCount() {
    return itemCount;
  }

  public int getProcessingProgress() {
    return processingProgress;
  }

  public void setFilename(String filename) {
    this.filename = filename;
  }

  public void setBytesReceived(long bytesReceived) {
    this.bytesReceived = bytesReceived;
  }

  public void setBytesTotal(long bytesTotal) {
    this.bytesTotal = bytesTotal;
  }

  public void setItem(int item) {
    this.item = item;
  }

  public void setItemCount(int size) {
    this.itemCount = size;
  }

  public Integer getProgress() {
    if (bytesTotal == 0)
      return 0;
    return (int) (100 * bytesReceived / bytesTotal);
  }

  public void setProcessingProgress(int processingProgress) {
    this.processingProgress = processingProgress;
  }
}