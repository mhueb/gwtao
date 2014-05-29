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
package com.gwtao.ui.util.client;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public final class ParameterList implements Iterable<ParameterList.Entry> {
  public static final class Entry {
    private final String name;
    private final String value;

    public Entry(String value) {
      this.name = "";
      this.value = value;
    }

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

    @Override
    public String toString() {
      return "Entry [name=" + name + ", value=" + value + "]";
    }

  }

  private final List<Entry> entries = new ArrayList<ParameterList.Entry>();

  public ParameterList(List<Entry> entries) {
    this.entries.addAll(entries);
  }

  @Override
  public Iterator<Entry> iterator() {
    return Collections.unmodifiableCollection(entries).iterator();
  }

  public static class Builder {
    private final List<Entry> entries = new ArrayList<ParameterList.Entry>();

    public ParameterList build() {
      return new ParameterList(entries);
    }

    public Builder() {
    }

    public Builder(ParameterList params) {
      entries.addAll(params.entries);
    }

    public Builder(String name, String value) {
      entries.add(new Entry(name, value));
    }

    public Builder add(String name, Object value) {
      entries.add(new Entry(name, String.valueOf(value)));
      return this;
    }

    public Builder add(String value) {
      entries.add(new Entry(value));
      return this;
    }
  }

  public static Builder getBuilder() {
    return new Builder();
  }

  public boolean isEmpty() {
    return this.entries.isEmpty();
  }

  public Long getLong(String name) {
    String s = get(name);
    return s == null ? null : Long.parseLong(s);
  }

  public Integer getInt(String name) {
    String s = get(name);
    return s == null ? null : Integer.parseInt(s);
  }

  public Boolean getBoolean(String name) {
    String s = get(name);
    return s == null ? null : Boolean.parseBoolean(s);
  }

  public boolean isBoolean(String name) {
    String s = get(name);
    return s == null ? false : Boolean.parseBoolean(s);
  }

  public String get(String string) {
    for (Entry entry : entries)
      if (entry.getName().equals(string))
        return entry.getValue();
    return null;
  }

  @Override
  public String toString() {
    StringBuilder buff = new StringBuilder();
    for (Entry elem : entries) {
      buff.append(elem).append("\n");
    }
    return buff.toString();
  }
}
