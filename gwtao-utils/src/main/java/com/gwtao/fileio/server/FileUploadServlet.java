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

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadBase.FileSizeLimitExceededException;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.ProgressListener;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.shu4j.utils.exception.InvalidSessionException;
import org.shu4j.utils.exception.ValidateException;
import org.shu4j.utils.progress.AbstractProgressMonitor;
import org.shu4j.utils.progress.IProgressMonitor;

import com.gwtao.fileio.server.misc.FileItemWrapper;
import com.gwtao.fileio.server.misc.FileUploadStatusMap;
import com.gwtao.fileio.shared.FileIOTag;
import com.gwtao.fileio.shared.FileUploadStatus;

public abstract class FileUploadServlet extends HttpServlet {
  private static final int SUBPROGRESS_RANGE = 100000;

  private final class ProcessingProgressMonitor extends AbstractProgressMonitor {
    private final FileUploadStatus progressStatus;

    private ProcessingProgressMonitor(FileUploadStatus progressStatus) {
      this.progressStatus = progressStatus;
    }

    @Override
    public void log(String comment, LogLevel level) {
      switch (level) {
      case ERROR:
        FileUploadServlet.this.log.error(comment);
        break;
      case WARNING:
        FileUploadServlet.this.log.warn(comment);
        break;
      }
    }

    @Override
    public void done() {
      super.done();
      this.progressStatus.setProcessingProgress(getProgress100());
    }

    @Override
    protected void onProgress() {
      this.progressStatus.setProcessingProgress(getProgress100());
    }
  }

  private final Log log = LogFactory.getLog(FileUploadServlet.class);

  private static final long serialVersionUID = 1L;

  public static final String UPLOAD_STATUS = "FileUploadStatus";

  public static final String FILE = "File";

  protected FileUploadServlet() {
  }

  @SuppressWarnings("rawtypes")
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    if (this.log.isDebugEnabled())
      this.log.debug("processing upload request");

    response.setContentType("text/plain");

    final PrintWriter writer = response.getWriter();

    FileItemFactory factory = new DiskFileItemFactory();
    ServletFileUpload upload = new ServletFileUpload(factory);
    upload.setFileSizeMax(getMaxFileSize());

    FileUploadStatus status;
    try {
      String ident = request.getParameter("UPLOADID");
      if (ident == null)
        throw new ValidateException("Invalid call. Missing UPLOADID");
      status = FileUploadStatusMap.get(request.getSession()).start(ident);
    }
    catch (ValidateException e) {
      this.log.error("upload failed", e);
      writer.write(FileIOTag.VALIDATE_ERROR.encode(e.getMessage()));
      return;
    }

    try {
      final FileUploadStatus progressStatus = status;
      upload.setProgressListener(new ProgressListener() {
        @Override
        public void update(long bytesRead, long contentLength, int item) {
          progressStatus.setBytesReceived(bytesRead);
          progressStatus.setBytesTotal(contentLength);
          progressStatus.setItem(item);
        }
      });

      StringBuilder buff = new StringBuilder();

      List items = upload.parseRequest(request);
      Iterator it = items.iterator();

      status.setItemCount(items.size());

      IProgressMonitor progress = new ProcessingProgressMonitor(progressStatus);
      progress.begin("Uploading", items.size() * SUBPROGRESS_RANGE);

      while (it.hasNext()) {
        FileItem item = (FileItem) it.next();
        if (acceptContentType(item.getContentType())) {
          if (this.log.isDebugEnabled())
            this.log.debug("received file " + item.getContentType() + ": " + item.getName());

          status.setFilename(item.getName());
          String data = onFileReceived(new FileItemWrapper(item), request, progress.getSubProgress(SUBPROGRESS_RANGE));
          if (data != null) {
            buff.append(data);
            if (it.hasNext())
              buff.append('\n');
          }
        }
        else
          throw new ValidateException("Unexpected content type " + item.getContentType() + ": " + item.getName());
      }
      writer.write(FileIOTag.SUCCESS.encode(buff.toString()));
    }
    catch (FileSizeLimitExceededException e) {
      this.log.error("upload failed", e);
      writer.write(FileIOTag.SIZE_ERROR.encode(e.getMessage()));
    }
    catch (FileUploadException e) {
      this.log.error("upload failed", e);
      writer.write(FileIOTag.UPLOAD_ERROR.encode(e.getMessage()));
    }
    catch (ValidateException e) {
      this.log.error("upload failed", e);
      writer.write(FileIOTag.VALIDATE_ERROR.encode(e.getMessage()));
    }
    catch (InvalidSessionException e) {
      this.log.error("upload failed", e);
      writer.write(FileIOTag.NOSESSION_ERROR.encode(e.getMessage()));
    }
    catch (RuntimeException e) {
      this.log.error("upload failed", e);
      writer.write(FileIOTag.RUNTIME_ERROR.encode(e.getMessage()));
    }
    finally {
      FileUploadStatusMap.get(request.getSession()).end(status);
    }
  }

  protected abstract int getMaxFileSize();

  protected abstract boolean acceptContentType(String contentType);

  /**
   * Process received file
   * 
   * @param item upload file
   * @param req the session
   * @param progress TODO
   * @return Info string for calling client
   * @throws ValidateException
   */
  protected abstract String onFileReceived(IFile item, HttpServletRequest req, IProgressMonitor progress) throws ValidateException, InvalidSessionException;
}
