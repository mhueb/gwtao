package com.gwtao.app.client;

import java.util.HashMap;
import java.util.Map;

public final class PageFactoryRegistry {
  public interface Entry {
    String getToken();

    IPage create();
  }

  private final Map<String, Entry> descriptorMap = new HashMap<String, Entry>();

  public void register(Entry entry) {
    descriptorMap.put(entry.getToken(), entry);
  }

  public IPage create(String token) {
    Entry factory = descriptorMap.get(token);
    if (factory == null)
      throw new RuntimeException();

    return factory.create();
  }
}
