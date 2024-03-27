/*===================================================*/
/* Copyright (c) 2010-2014 NextMove Software         */
/*===================================================*/
package com.nextmovesoftware.CaffeineFix;

import java.util.List;

public class CaffeineFix {
  private CaffeineFixDictionary dict;

  public CaffeineFix(String fname) {
    dict = new CaffeineFixDictionary(fname);
  }

  public CaffeineFixDictionary getDict() {
    return dict;
  }

  /**
   * Is the string accepted by this dictionary
   * 
   * @param str
   * @return
   */
  public boolean match(String str) {
    if (str == null) {
      throw new IllegalArgumentException("null string given to match function");
    }
    int len = str.length();
    if (len == 0) {
      return false;
    }

    char ch = str.charAt(0);
    int state = 0;
    int i = 1;

    for (;;) {
      if (dict.ch(state) == ch) {
        if (i == len) {
          return dict.valid(state);
        }
        state = dict.down(state);
        ch = str.charAt(i++);
      } else {
        state = dict.across(state);
      }
      if (state == 0) {
        return false;
      }
    }
  }

  /**
   * Is this string a prefix of a term in the dictionary
   * 
   * @param str
   * @return
   */
  public boolean prefix(String str) {
    if (str == null) {
      throw new IllegalArgumentException("null string given to prefix function");
    }
    int len = str.length();
    if (len == 0) {
      return true;
    }

    char ch = str.charAt(0);
    int state = 0;
    int i = 1;

    for (;;) {
      if (dict.ch(state) == ch) {
        if (i == len) {
          return true;
        }
        state = dict.down(state);
        ch = str.charAt(i++);
      } else {
        state = dict.across(state);
      }
      if (state == 0) {
        return false;
      }
    }
  }

  /**
   * Attempts to find an entry in the dictionary by "autocompleting" i.e. if
   * ONLY one entry can be reached by appending additional characters this entry
   * is returned
   * 
   * @param str
   * @return
   */
  public String complete(String str) {
    if (str == null) {
      return null;
    }
    int len = str.length();
    if (len == 0) {
      return null;
    }

    char ch = str.charAt(0);
    int state = 0;
    int i = 1;

    for (;;) {
      if (dict.ch(state) == ch) {
        if (i == len) {
          if (dict.valid(state)) {
            return null;
          }
          break;
        }
        state = dict.down(state);
        ch = str.charAt(i++);
      } else {
        state = dict.across(state);
      }
      if (state == 0) {
        return null;
      }
    }

    state = dict.down(state);
    if (dict.across(state) != 0) {
      return null;
    }

    String result = str + dict.ch(state);
    while (!dict.valid(state)) {
      state = dict.down(state);
      if (dict.across(state) != 0) {
        break;
      }
      result += dict.ch(state);
    }
    return result;
  }

  /**
   * Suggests spelling corrections with an edit distance of up to maxDist from
   * the given string
   * 
   * @param str
   * @param maxDist
   * @return
   */
  public List<String> suggestions(String str, int maxDist) {
    CaffeineFixCorrection correction = new CaffeineFixCorrection(str, maxDist, dict);
    return correction.suggestions();
  }

  /**
   * Suggests a spelling correction with an edit distance of up to maxDist from
   * the given string
   * 
   * @param str
   * @param maxDist
   * @return
   */
  public String suggest(String str, int maxDist) {
    CaffeineFixCorrection correction = new CaffeineFixCorrection(str, maxDist, dict);
    return correction.suggestion();
  }

  /**
   * Suggests a spelling correction with an edit distance of up to 1 from the
   * given string
   * 
   * @param str
   * @return
   */
  public String suggest(String str) {
    return suggest(str, 1);
  }

  /**
   * Like "match" but for chemistry grammars (enforces correct bracketting)
   * 
   * @param str
   * @return
   */
  public boolean cmatch(String str) {
    int len = str.length();
    if (len == 0) {
      return false;
    }

    CaffeineFixStack stack = new CaffeineFixStack();
    char ch = str.charAt(0);
    int state = 0;
    int i = 1;

    for (;;) {
      if (dict.ch(state) == ch) {
        if (stack.fail(ch)) {
          return false;
        }
        if (i == len) {
          return dict.valid(state) && stack.empty();
        }
        state = dict.down(state);
        ch = str.charAt(i++);
      } else {
        state = dict.across(state);
      }
      if (state == 0) {
        return false;
      }
    }
  }

  /**
   * Like "prefix" but for chemistry grammars (enforces correct bracketting)
   * 
   * @param str
   * @return
   */
  public boolean cprefix(String str) {
    int len = str.length();
    if (len == 0) {
      return true;
    }

    CaffeineFixStack stack = new CaffeineFixStack();
    char ch = str.charAt(0);
    int state = 0;
    int i = 1;

    for (;;) {
      if (dict.ch(state) == ch) {
        if (stack.fail(ch)) {
          return false;
        }
        if (i == len) {
          return true;
        }
        state = dict.down(state);
        ch = str.charAt(i++);
      } else {
        state = dict.across(state);
      }
      if (state == 0) {
        return false;
      }
    }
  }
}
