/* 
 * GWTAO
 * 
 * Copyright (C) 2012 Matthias Huebner
 * 
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 3 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 *
 */
package com.gwtao.ui.portal.client.document;

import org.apache.commons.lang.StringUtils;
import org.mortbay.util.StringUtil;

import com.gwtao.common.shared.message.IMessageSource;
import com.gwtao.common.shared.permission.IPermissionDelegate;
import com.gwtao.ui.context.client.actions.CheckInAction;
import com.gwtao.ui.context.client.actions.CheckOutAction;
import com.gwtao.ui.context.client.actions.RefreshAction;
import com.gwtao.ui.context.client.actions.RevertAction;
import com.gwtao.ui.context.client.datacontext.IDataChangeListener;
import com.gwtao.ui.context.client.editcontext.EditContextListenerAdapter;
import com.gwtao.ui.context.client.editcontext.IEditContext;
import com.gwtao.ui.context.client.editcontext.IEditContextOwner;
import com.gwtao.ui.context.client.i18n.ContextConstants;
import com.gwtao.ui.portal.client.actionmanager.IActionManager;
import com.gwtao.ui.portal.client.i18n.PortalConstants;
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

    getEditContext().addChangeListener(new IDataChangeListener() {
      @Override
      public void onDataChange() {
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
  public String getTitle() {
    StringBuilder buff = new StringBuilder();
    if (isDirty())
      buff.append("*");
    if (getEditContext().isNew())
      buff.append(PortalConstants.c.neW()).append(":");

    if (getEditContext().isDataNull()) {
      String title = super.getTitle();
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