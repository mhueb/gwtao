/* 
 * Copyright 2012 GWTAO
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
package com.gwtao.rpc.fileio.server.misc;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.fileupload.FileItem;

import com.gwtao.rpc.fileio.server.IFile;

/**
 * Hides apache FileItem behind IFile interface.
 * 
 * @author Matthias Huebner
 * 
 */
public class FileItemWrapper implements IFile {
  private final FileItem item;

  public FileItemWrapper(FileItem item) {
    this.item = item;
  }

  @Override
  public byte[] getData() {
    return this.item.get();
  }

  @Override
  public InputStream getInputStream() throws IOException {
    return this.item.getInputStream();
  }

  @Override
  public String getName() {
    return this.item.getName();
  }

  @Override
  public long getSize() {
    return this.item.getSize();
  }

  @Override
  public String getContentType() {
    return this.item.getContentType();
  }
}
