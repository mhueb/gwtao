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
package com.gwtao.rpc.fileio.server;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.shu4j.utils.exception.InvalidSessionException;
import org.shu4j.utils.exception.MessageException;
import org.shu4j.utils.util.LoggerUtil;

public abstract class FileDownloadServlet extends HttpServlet {
  private static final long serialVersionUID = -2638224318753451420L;

  private final Logger log = LoggerUtil.getLogger(FileDownloadServlet.class);

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    if (this.log.isLoggable(Level.FINER))
      this.log.fine("processing download request");

    try {
      IFile item = getData(req);
      if (item != null) {
        if (this.log.isLoggable(Level.FINER))
          this.log.fine("sending file '" + item.getName() + "' to browser");

        if (StringUtils.isNotBlank(item.getContentType()))
          resp.setContentType(item.getContentType());

        resp.setHeader("Content-Disposition", makeContentDisposition(item.getName()));

        if (isDisableCache()) {
          resp.setHeader("Cache-Control", "no-store");
          resp.setHeader("Pragma", "no-cache");
          resp.setDateHeader("Expires", 0);
        }

        ServletOutputStream out = resp.getOutputStream();

        if (item instanceof IFileStream) {
          IFileStream stream = (IFileStream) item;
          stream.write(out);
        }
        else {
          InputStream is = item.getInputStream();
          if (is != null) {
            int read;
            byte[] buff = new byte[1024];
            while ((read = is.read(buff)) != -1) {
              out.write(buff, 0, read);
            }
          }
          else {
            byte[] data = item.getData();
            if (data != null)
              out.write(data);
            else {
              resp.sendError(HttpServletResponse.SC_NO_CONTENT, "Data not available");
              return;
            }
          }
        }
        out.close();
        if (this.log.isLoggable(Level.FINER))
          this.log.fine("sent file done.");
      }
      else
        resp.sendError(HttpServletResponse.SC_NO_CONTENT, "Data not available");
    }
    catch (InvalidSessionException e) {
      resp.sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
      this.log.log(Level.SEVERE, "download failed", e);
    }
    catch (Exception e) {
      resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
      this.log.log(Level.SEVERE, "download failed", e);
    }
  }

  private String makeContentDisposition(String file) {
    StringBuilder buff = new StringBuilder();

    if (isAttachment())
      buff.append("attachment");
    else
      buff.append("inline");

    buff.append(";");

    if (StringUtils.isNotBlank(file))
      buff.append("filename=\"").append(file).append("\"");

    return buff.toString();
  }

  protected boolean isAttachment() {
    return true;
  }

  protected boolean isDisableCache() {
    return false;
  }

  protected abstract IFile getData(HttpServletRequest req) throws MessageException, InvalidSessionException;
}
