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
package com.gwtao.rpc.fileio.shared;

public enum FileIOTag {
  SUCCESS(0, "SUCCESS"),
  SIZE_ERROR(1, "SIZE-ERROR"),
  UPLOAD_ERROR(2, "UPLOAD-ERROR"),
  VALIDATE_ERROR(3, "VALIDATE-ERROR"),
  RUNTIME_ERROR(4, "RUNTIME-ERROR"),
  NODATA_ERROR(5, "NODATA-ERROR"),
  NOSESSION_ERROR(6, "NOSESSION-ERROR");

  public static class Result {
    public FileIOTag tag;
    public String params;
  }

  private String name;
  private int value;

  private FileIOTag(int i, String name) {
    this.value = i;
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public int getValue() {
    return value;
  }

  public String encode(String params) {
    if (params != null)
      return getName() + ":" + params;
    else
      return getName();
  }

  public static Result decode(String results) {
    results = results.trim();
    if (results.startsWith("<PRE>") || results.startsWith("<pre>"))
      results = results.substring(5);
    if (results.endsWith("</PRE>") || results.endsWith("</pre>"))
      results = results.substring(0, results.length() - 6);
    results = results.trim();
    for (FileIOTag tag : FileIOTag.class.getEnumConstants()) {
      if (results.startsWith(tag.getName())) {
        Result res = new Result();
        res.tag = tag;
        int length = tag.getName().length();
        if (length < results.length())
          res.params = results.substring(length + 1);
        return res;
      }
    }
    return null;
  }
}
