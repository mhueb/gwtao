package com.gwtao.ui.util.client;

import org.shu4j.utils.privilege.IPermissionSource;

public class GlobalPrivilegeManager {
  private static final IPermissionSource INSTANCE = null;

  public static IPermissionSource get() {
    return INSTANCE;
  }
}
