package com.gwtao.ui.util.client;

import org.shu4j.utils.permission.PrivilegeManager;

public class GlobalPrivilegeManager extends PrivilegeManager {
  private static final GlobalPrivilegeManager INSTANCE = new GlobalPrivilegeManager();

  public static PrivilegeManager get() {
    return INSTANCE;
  }
}
