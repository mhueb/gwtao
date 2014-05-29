package java.util.regex;

import java.security.AccessController;
import java.security.PrivilegedAction;
import java.text.CharacterIterator;
import java.text.Normalizer;
import java.util.Locale;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Arrays;

public final class Pattern implements java.io.Serializable {

  public static final int UNIX_LINES = 0x01;

  public static final int CASE_INSENSITIVE = 0x02;

  public static final int COMMENTS = 0x04;

  public static final int MULTILINE = 0x08;

  public static final int LITERAL = 0x10;

  public static final int DOTALL = 0x20;

  public static final int UNICODE_CASE = 0x40;

  public static final int CANON_EQ = 0x80;

  public static final int UNICODE_CHARACTER_CLASS = 0x100;

  private static final long serialVersionUID = 5073258162644648461L;

  public static Pattern compile(String regexp) {
    return new Pattern(regexp);
  }

  private String regexp;
  
  public Pattern(String regexp) {
    this.regexp = regexp;
  }
  
  public Matcher matcher(String text) {
    return new Matcher();
  }
}
