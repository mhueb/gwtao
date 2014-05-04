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
package com.gwtao.rpc.fileio.server.misc;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.shu4j.utils.exception.MessageException;
import org.shu4j.utils.message.MessageLevel;

import com.gwtao.rpc.fileio.shared.FileUploadStatus;

public class FileUploadStatusMap {
  private final Map<String, FileUploadStatus> statusMap = new ConcurrentHashMap<String, FileUploadStatus>();

  public static FileUploadStatusMap get(HttpSession session) {
    FileUploadStatusMap map = (FileUploadStatusMap) session.getAttribute("FileUploadStatusMap");
    if (map == null) {
      synchronized (FileUploadStatusMap.class) {
        map = (FileUploadStatusMap) session.getAttribute("FileUploadStatusMap");
        if (map == null) {
          map = new FileUploadStatusMap();
          session.setAttribute("FileUploadStatusMap", map);
        }
      }
    }
    return map;
  }

  public FileUploadStatus start(String ident) throws MessageException {
    synchronized (statusMap) {
      FileUploadStatus value = new FileUploadStatus(ident);
      if (StringUtils.isEmpty(ident) || statusMap.containsKey(ident))
        throw new MessageException("Invalid upload identifier!", MessageLevel.FATAL);
      statusMap.put(ident, value);
      return value;
    }
  }

  public FileUploadStatus get(String ident) {
    return statusMap.get(ident);
  }

  public void end(FileUploadStatus status) {
    // statusMap.remove(status.getIdent()); stehen lassen, die session dauert ja nicht ewig.
  }

}
