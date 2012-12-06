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
package com.gwtao.fileio.client;

import org.shu4j.utils.exception.InvalidSessionException;
import org.shu4j.utils.exception.ValidateException;
import org.shu4j.utils.message.MessageLevel;
import org.shu4j.utils.util.HexDump;

import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteHandler;
import com.gwtao.fileio.shared.FileIOTag;

public class FileUploadCompleteHandler implements SubmitCompleteHandler {
  private final AsyncCallback<String> callback;

  public FileUploadCompleteHandler(AsyncCallback<String> callback) {
    this.callback = callback;
  }

  @Override
  public void onSubmitComplete(SubmitCompleteEvent event) {
    try {
      callback.onSuccess(processResults(event.getResults()));
    }
    catch (Throwable t) {
      callback.onFailure(t);
    }
  }

  private String processResults(String results) throws ValidateException, InvalidSessionException {
    if (results == null)
      throw new RuntimeException("Unexpected empty result");

    FileIOTag.Result res = FileIOTag.decode(results);
    if (res != null)
      switch (res.tag) {
      case SUCCESS:
        return res.params;

      case NODATA_ERROR:
        throw new ValidateException(res.params == null ? res.params : "No data received");

      case SIZE_ERROR:
        throw new ValidateException(res.params == null ? res.params : "Filesize not supported", MessageLevel.ERROR);

      case VALIDATE_ERROR:
        throw new ValidateException(res.params, MessageLevel.ERROR);

      case RUNTIME_ERROR:
        throw new RuntimeException(res.params);

      case NOSESSION_ERROR:
        throw new InvalidSessionException(res.params);

      case UPLOAD_ERROR:
        throw new ValidateException(res.params, MessageLevel.FATAL);
      }

    throw new RuntimeException("Unexpected servlet result!<br><pre>" + new SafeHtmlBuilder().appendEscapedLines(new HexDump(16, true).dump(HexDump.convert(results.toCharArray()))) + "</pre>");
  }
}