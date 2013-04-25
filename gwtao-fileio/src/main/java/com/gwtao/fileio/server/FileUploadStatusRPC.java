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
package com.gwtao.fileio.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.gwtao.fileio.server.misc.FileUploadStatusMap;
import com.gwtao.fileio.shared.FileUploadStatus;
import com.gwtao.fileio.shared.IFileUploadStatusRPC;

/**
 * <code><pre>
 *   &lt;servlet&gt;
 *     &lt;servlet-name&gt;FileUploadStatus&lt;/servlet-name&gt;
 *     &lt;servlet-class&gt;com.gwtao.fileio.server.FileUploadStatusRPC&lt;/servlet-class&gt;
 *   &lt;/servlet&gt;
 *   &lt;servlet-mapping&gt;
 *     &lt;servlet-name&gt;FileUploadStatus&lt;/servlet-name&gt;
 *     &lt;url-pattern&gt;/MODULENAME/FileUploadStatus&lt;/url-pattern&gt;
 *   &lt;/servlet-mapping&gt;  
 * </pre></code>
 * 
 * @author mah
 */
public class FileUploadStatusRPC extends RemoteServiceServlet implements IFileUploadStatusRPC {
  private static final long serialVersionUID = 1L;

  @Override
  public FileUploadStatus getStatus(String ident) {
    return FileUploadStatusMap.get(getThreadLocalRequest().getSession()).get(ident);
  }
}
