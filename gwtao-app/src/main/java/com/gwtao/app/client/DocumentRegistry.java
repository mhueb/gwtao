package com.gwtao.app.client;

import java.util.HashMap;
import java.util.Map;


public final class DocumentRegistry {
  public interface Entry {
    String getToken();

    IDocument create();
  }

  private final Map<String, Entry> descriptorMap = new HashMap<String, Entry>();

  public void register(Entry entry) {
    descriptorMap.put(entry.getToken(), entry);
  }

  public void register(Documents docs) {
    for (Entry entry : docs.getEntries())
      register(entry);
  }

  public IDocument create(String token) {
    Entry factory = descriptorMap.get(token);
    if (factory == null)
      throw new RuntimeException();

    return factory.create();
  }
}
