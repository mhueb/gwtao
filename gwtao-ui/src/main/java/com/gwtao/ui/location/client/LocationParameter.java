package com.gwtao.ui.location.client;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.NotImplementedException;

public final class LocationParameter implements Iterable<LocationParameter.Entry> {
  public static final class Entry {
    private final String name;
    private final String value;

    public Entry(String name, String value) {
      this.name = name;
      this.value = value;
    }

    public String getName() {
      return name;
    }

    public String getValue() {
      return value;
    }
  }

  private final List<Entry> entries = new ArrayList<LocationParameter.Entry>();

  @Override
  public Iterator<Entry> iterator() {
    return Collections.unmodifiableCollection(entries).iterator();
  }

  public static class Builder {
    LocationParameter build() {
      throw new NotImplementedException();
    }
  }
}
