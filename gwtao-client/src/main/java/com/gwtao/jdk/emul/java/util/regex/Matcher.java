package java.util.regex;

import java.util.Objects;

public final class Matcher implements MatchResult {
  
  public boolean matches() {
    return false;
  }

  public int start() {
    return 0;
  }

  public int start(int group) {
    return 0;
  }

  public int end() {
    return 0;
  }

  public int end(int group) {
    return 0;
  }

  public String group() {
    return null;
  }

  public String group(int group) {
    return null;
  }

  public int groupCount() {
    return 0;
  }
}
