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
package com.gwtao.portalapp.client.document;

import org.apache.commons.lang.StringUtils;
import org.shu4j.utils.message.IMessageSource;
import org.shu4j.utils.permission.IPermissionDelegate;

import com.gwtao.portalapp.client.actionmanager.IActionManager;
import com.gwtao.portalapp.client.deprecated.client.actions.CheckInAction;
import com.gwtao.portalapp.client.deprecated.client.actions.CheckOutAction;
import com.gwtao.portalapp.client.deprecated.client.actions.RefreshAction;
import com.gwtao.portalapp.client.deprecated.client.actions.RevertAction;
import com.gwtao.portalapp.client.deprecated.client.editcontext.EditContextActionAdapter;
import com.gwtao.portalapp.client.deprecated.client.editcontext.EditContextListenerAdapter;
import com.gwtao.portalapp.client.deprecated.client.editcontext.IEditContext;
import com.gwtao.portalapp.client.deprecated.client.editcontext.IEditContextOwner;
import com.gwtao.portalapp.client.deprecated.client.i18n.ContextConstants;
import com.gwtao.portalapp.client.i18n.PortalConstants;
import com.gwtao.ui.data.client.source.events.DataChangedEvent;
import com.gwtao.ui.util.client.mask.IWaitMask;
import com.gwtao.ui.util.client.mask.WaitMask;

public abstract class DocumentEditor<T> extends Document implements IDocumentEditor, IEditContextOwner {
  private final CheckOutAction checkOutAction = new CheckOutAction(this);
  private final RefreshAction refreshAction = new RefreshAction(this);
  private final CheckInAction checkInAction = new CheckInAction(this);
  private final RevertAction revertAction = new RevertAction(this);
  private IWaitMask create;

  protected DocumentEditor(IDocumentDescriptor descr) {
    super(descr);
  }

  public abstract IEditContext<T> getEditContext();

  @Override
  public void init() {
    getEditContext().start(getParameter());
    getEditContext().addStateListener(new EditContextListenerAdapter() {
      @Override
      public void onValidatePost(IMessageSource msgs) {
        // Weitersagen...
      }

      @Override
      public void onDataStateChange() {
        getViewContext().updateTitle();
      }
    });

    getEditContext().addHandler(DataChangedEvent.TYPE,new DataChangedEvent.Handler() {
      @Override
      public void onDataChanged() {
        getViewContext().updateTitle();
      }
    });

    addActions(getViewContext().getActionManager());
  }

  protected void addActions(IActionManager am) {
    EditContextActionAdapter actionCO = new EditContextActionAdapter(checkOutAction, getEditContext());
    am.addAction(actionCO);
    EditContextActionAdapter actionRF = new EditContextActionAdapter(refreshAction, getEditContext());
    am.addAction(actionRF);
    EditContextActionAdapter actionCI = new EditContextActionAdapter(checkInAction, getEditContext());
    am.addAction(actionCI);
    EditContextActionAdapter actionRV = new EditContextActionAdapter(revertAction, getEditContext());
    am.addAction(actionRV);

    // getViewContext().getAccelerators().add('S', IAcceleratorKey.CTRL, actionCI);
    // getViewContext().getAccelerators().add('O', IAcceleratorKey.CTRL, actionCO);
    // getViewContext().getAccelerators().add(IAcceleratorKey.F5, IAcceleratorKey.NONE, actionRF);
  }

  public void setEditPrivilege(IPermissionDelegate delegete) {
    checkOutAction.setPermissionDelegate(delegete);
    checkInAction.setPermissionDelegate(delegete);
    revertAction.setPermissionDelegate(delegete);
  }

  @Override
  public boolean isDirty() {
    return getEditContext().isDirty();
  }

  @Override
  public String getDisplayText() {
    StringBuilder buff = new StringBuilder();
    if (isDirty())
      buff.append("*");
    if (getEditContext().isNew())
      buff.append(PortalConstants.c.neW()).append(":");

    if (getEditContext().isNull()) {
      String title = super.getDisplayText();
      if (StringUtils.isEmpty(title))
        buff.append("<i>").append(PortalConstants.c.unknown()).append("</i>");
      else
        buff.append(title);
    }
    else {
      String title = getTitle(getEditContext().getData());
      if (StringUtils.isEmpty(title))
        buff.append("<i>").append(ContextConstants.c.emptyTitle()).append("</i>");
      else
        buff.append(title);
    }

    return buff.toString();
  }

  protected abstract String getTitle(T data);

  @Override
  public IWaitMask getMask() {
    if (create == null)
      create = new WaitMask(getWidget());
    return create;
  }
}