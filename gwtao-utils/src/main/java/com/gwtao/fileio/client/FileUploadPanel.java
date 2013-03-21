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

import org.apache.commons.lang.StringUtils;
import org.shu4j.utils.exception.ValidateException;
import org.shu4j.utils.message.MessageLevel;

import com.google.gwt.core.client.JavaScriptException;
import com.google.gwt.dom.client.InputElement;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FormPanel;

public class FileUploadPanel extends FormPanel {
  private static class FileUploadEx extends FileUpload {
    public InputElement getInputElement() {
      return getElement().cast();
    }
  };

  private final FileUploadEx upload = new FileUploadEx();
  private final AsyncCallback<String> callback;
  private String tempURL;
  private Integer ident = 0;

  public FileUploadPanel(AsyncCallback<String> callback) {
    this.callback = callback;
    setMethod(FormPanel.METHOD_POST);
    setEncoding(FormPanel.ENCODING_MULTIPART);

    addSubmitCompleteHandler(new FileUploadCompleteHandler(this, callback));

    upload.setName("uploadFormElement");
    add(upload);
  }

  public String getFilename() {
    return upload.getFilename();
  }

  public void setAccept(String... mimeTypes) {
    upload.getInputElement().setAccept(StringUtils.join(mimeTypes, ","));
  }

  @Override
  public void submit() {
    try {
      super.submit();
    }
    catch (JavaScriptException e) {
      callback.onFailure(new ValidateException(e.getDescription(), MessageLevel.ERROR));
    }
  }

  @Override
  public void setAction(String url) {
    this.tempURL = url;
    updateAction();
  }
  
  void updateAction() {
    String url;
    ident = (int) System.currentTimeMillis();
    if (this.tempURL.contains("?"))
      url = this.tempURL + "&UPLOADID=" + getIdent();
    else
      url = this.tempURL + "?UPLOADID=" + getIdent();
    super.setAction(url);
  }

  public String getIdent() {
    return ident.toString();
  }

  public void setWidth(int width) {
    upload.getInputElement().setSize(width);
  }
}
