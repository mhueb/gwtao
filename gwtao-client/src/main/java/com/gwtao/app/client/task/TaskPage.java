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
package com.gwtao.app.client.task;

import com.google.gwt.core.shared.GWT;
import com.gwtao.app.client.DefaultPageHandler;
import com.gwtao.app.client.IPage;
import com.gwtao.app.client.IPageContext;
import com.gwtao.ui.data.client.source.events.DataChangedEvent;
import com.gwtao.ui.dialog.client.AsyncOkAnswere;
import com.gwtao.ui.dialog.client.AsyncOkCancelAnswere;
import com.gwtao.ui.dialog.client.MessageDialog;
import com.gwtao.ui.place.client.IParameterConverter;
import com.gwtao.ui.place.client.TokenUtils;
import com.gwtao.ui.task.client.IAsyncDataReader;
import com.gwtao.ui.task.client.IAsyncTaskPerformer;
import com.gwtao.ui.task.client.ITaskController;
import com.gwtao.ui.task.client.ITaskView;
import com.gwtao.ui.task.client.TaskController;
import com.gwtao.ui.task.client.i18n.DataConstants;
import com.gwtao.ui.util.client.action.Action;
import com.gwtao.ui.util.client.action.IAction;
import com.gwtao.ui.viewdriver.client.IViewDriver;

public abstract class TaskPage<P, M> implements IPage, ITaskView {
  private static final DataConstants DATA_CONSTS = GWT.create(DataConstants.class);

  private TaskController<P, M> editor;

  private IAction performAction;

  private IAction refreshAction;

  private IParameterConverter<P, M> converter;

  private IPageContext ctx;

  @Override
  public void init(IPageContext ctx) {
    this.ctx = ctx;
    this.ctx.addHandler(new DefaultPageHandler() {
      @Override
      public void onInit() {
        start();
      }
    });
  }

  public IPageContext getCtx() {
    return ctx;
  }

  protected void initEditor(IViewDriver<M> viewMgr, IParameterConverter<P, M> converter, IAsyncDataReader<P, M> reader, IAsyncTaskPerformer<M> performer) {
    this.editor = new TaskController<P, M>(viewMgr, converter, reader, performer);
    this.editor.initView(this);
    this.editor.addHandler(DataChangedEvent.TYPE, new DataChangedEvent.Handler() {

      @Override
      public void onDataChanged() {
        getCtx().updateTitle();
      }
    });

    this.converter = converter;
    performAction = new Action(editor.getPerformerTitle()) {

      @Override
      public void execute(Object... data) {
        editor.perform();
      }
    };

    refreshAction = new Action(DATA_CONSTS.refresh()) {

      @Override
      public void execute(Object... data) {
        editor.refresh();
      }
    };

  }

  public ITaskController<P, M> getEditor() {
    return editor;
  }

  public IAction getPerformAction() {
    return performAction;
  }

  public IAction getRefreshAction() {
    return refreshAction;
  }

  protected void start() {
    editor.start(converter.decode(TokenUtils.parse(getCtx().getParameter())));
  }

  @Override
  public void alert(String title, String message, AsyncOkAnswere answere) {
    MessageDialog.alert(title, message, answere);
  }

  @Override
  public void confirm(String title, String message, AsyncOkCancelAnswere answere) {
    MessageDialog.confirm(title, message, answere);
  }
}
