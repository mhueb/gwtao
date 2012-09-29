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
package com.gwtao.ui.portal.client.document;

import com.gwtao.ui.context.client.ContextActionAdapter;
import com.gwtao.ui.context.client.ContextImageBundle;
import com.gwtao.ui.context.client.selectioncontext.ISelectionContext;
import com.gwtao.ui.dialog.client.AsyncYESNOAnswere;
import com.gwtao.ui.dialog.client.MessageDialog;
import com.gwtao.ui.portal.client.Portal;
import com.gwtao.ui.portal.client.actionmanager.IActionManager;
import com.gwtao.ui.portal.client.i18n.PortalConstants;
import com.gwtao.ui.util.client.action.Action;
import com.gwtao.ui.util.client.action.IAction;
import com.gwtao.ui.util.client.action.IPrivilegedAction;
import com.gwtao.utils.shared.permission.IPermissionDelegate;
import com.gwtao.utils.shared.permission.Permission;

public abstract class DocumentSelector<T> extends Document implements IDocumentSelector {
  private static boolean globalUseDoubleClick = true;

  private final IAction searchAction = new Action(PortalConstants.c.search(), ContextImageBundle.SEARCH_ICON) {
    public void execute(Object... data) {
      search();
    }
  };

  private final IPrivilegedAction actionCreate = new Action(PortalConstants.c.create(), ContextImageBundle.NEW_ICON) {
    public void execute(Object... data) {
      create();
    }
  };

  private final IAction actionOpen = new Action(PortalConstants.c.open(), ContextImageBundle.EDIT_ICON) {
    @SuppressWarnings("unchecked")
    public void execute(Object... data) {
      if (data != null && data.length == 1) {
        open((T) data[0]);
      }
    }

    @Override
    public Permission getPermission(Object... data) {
      Permission perm = data != null && data.length == 1 && data[0] != null ? Permission.ALLOWED : Permission.UNALLOWED;
      return perm.add(super.getPermission(data));
    }
  };

  private final IPrivilegedAction actionDelete = new Action(PortalConstants.c.deletE(), ContextImageBundle.DELETE_ICON) {
    @SuppressWarnings("unchecked")
    public void execute(Object... data) {
      delete((T) data[0]);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Permission getPermission(Object... data) {
      Permission perm = data != null && data.length == 1 && data[0] != null && canDelete((T) data[0]) ? Permission.ALLOWED : Permission.UNALLOWED;
      return perm.add(super.getPermission(data));
    }
  };

  private int mode;

  public static final int NONE = 0x00;

  /** If you define this for a selector, you should override {@link #create} */
  public static final int ACREATE = 0x01;
  public static final int AOPEN = 0x02;

  /** If you define this for a selector, you should override {@link #doDelete} */
  public static final int ADELETE = 0x04;

  /** Search action */
  public static final int ASEARCH = 0x08;

  public static final int ALL = ACREATE | AOPEN | ADELETE | ASEARCH;

  // private SearchPanel searchPanel;

  private ISelectionContext selectionContext;

  /**
   * Konstruktor der für den Selector alle verfügbaren Aktions zugänglich macht. Will man das nicht (oder
   * anders gesagt, will man die verfügbaren Aktionen für einen Selektor selber bestimmen), so kann man
   * explizit eine BitMaske angeben, mit deren Hilfe die verfügbaren Aktionen bestimmt werden.
   * 
   * @see {@link #DocumentSelector(int)}
   */
  protected DocumentSelector(IDocumentDescriptor descr) {
    this(descr, ALL);
  }

  /**
   * @param mode Action configuration <br/>
   *          {@link #ALL} Use all default actions <br/>
   *          {@link #NONE} Use no default action
   *          <p>
   *          <br/>
   *          {@link #ACREATE} adds a create action <br/>
   *          {@link #AOPEN} adds an open action <br/>
   *          {@link #ADELETE} adds a delete action <br/>
   *          {@link #ASEARCH} adds a search action
   */
  protected DocumentSelector(IDocumentDescriptor descr, int mode) {
    super(descr);
    this.mode = mode;
  }

  @Override
  public void init() {
    addActions(getViewContext().getActionManager());
  }

  @Override
  public final ISelectionContext getSelectionContext() {
    if (selectionContext == null)
      selectionContext = createSelectionContext();
    return selectionContext;
  }

  protected abstract ISelectionContext createSelectionContext();

  protected abstract void search();

  /**
   * Called if the mode includes {@link #AOPEN}
   */
  protected void addOpenAction() {
    if (isUseDoubleClick())
      getSelectionContext().setDoubleClickAction(actionOpen);
    else
      getSelectionContext().setOpenAction(actionOpen);
  }

  protected boolean isUseDoubleClick() {
    return isGlobalUseDoubleClick();
  }

  public static boolean isGlobalUseDoubleClick() {
    return DocumentSelector.globalUseDoubleClick;
  }

  public static void setGlobalUseDoubleClick(boolean globalUseDoubleClick) {
    DocumentSelector.globalUseDoubleClick = globalUseDoubleClick;
  }

  /**
   * Basierend auf der für Selector angegeben BitMask ({@link #SelectorEx(int)}) werden hier die Aktionen
   * hinzugefügt.
   */
  public void addActions(IActionManager am) {
    if ((mode & AOPEN) == AOPEN)
      addOpenAction();
    if ((mode & ASEARCH) == ASEARCH)
      am.addAction(searchAction);
    if ((mode & AOPEN) == AOPEN)
      am.addAction(new ContextActionAdapter(actionOpen, getSelectionContext()));
    if ((mode & ACREATE) == ACREATE)
      am.addAction(actionCreate);
    if ((mode & ADELETE) == ADELETE)
      am.addAction(new ContextActionAdapter(actionDelete, getSelectionContext()));
  }

  /**
   * Override this is you specified {@link #ACREATE}
   */
  protected void create() {
  }

  /**
   * You can override this if your model is not of the type {@link IModel}. Make sure to add {@link #AOPEN} to
   * the selector mode if you want this to be called and the action for open to be available.
   * 
   * @param t The model to open
   */
  protected void open(T t) {
    Object parameter = Portal.get().mapObject2Parameter(t);
    if (parameter != null)
      Portal.get().openDocument(parameter);
  }

  /**
   * @see {@link #doDelete}
   * @param t The model to delete
   */
  protected void delete(final T t) {
    MessageDialog.confirm(getTitle(), PortalConstants.c.areYouSureToDelete(), new AsyncYESNOAnswere() {
      @Override
      public void onYes() {
        doDelete(t);
      }
    });
  }

  /**
   * Perform the real deletion of the model if the user confirmed the deletion.
   * <p>
   * This is called after the {@link MessageDialog} was closed with a click on the yes button.
   * 
   * @see {@link #canDelete}
   * @see {@link #delete}
   * @param t The model to delete
   */
  protected void doDelete(T t) {
  }

  /**
   * @see {@link #doDelete}
   * @param t The model to delete
   * @return <code>true</code> if the given model can be deleted, <code>false</code> otherwise.
   */
  protected boolean canDelete(T t) {
    return false;
  }

  public void setPrivilege(IPermissionDelegate delegate) {
    setCreatePrivilege(delegate);
    setDeletePrivilege(delegate);
  }

  public void setDeletePrivilege(IPermissionDelegate delegate) {
    actionDelete.setPermissionDelegate(delegate);
  }

  public void setCreatePrivilege(IPermissionDelegate delegate) {
    actionCreate.setPermissionDelegate(delegate);
  }
}
