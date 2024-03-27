/*============================================*/
/* Copyright (c) 2011-2014 NextMove Software  */
/*============================================*/
package com.nextmovesoftware.CaffeineFix;

import java.util.ArrayList;
import java.util.List;

public class CaffeineFixCorrection {
  private CaffeineFixDictionary dict;
  private int maxdist, len;
  private int src, dst, ttl;
  private int maxCount;
  private List<String> suggestions;
  private String str;
  private char[] buffer;
  private char[] stack;
  private int top;

  public CaffeineFixCorrection(String istr, int imaxdist, CaffeineFixDictionary idict) {
    len = istr.length();
    buffer = new char[len + imaxdist + 1];
    stack = new char[64];
    maxdist = imaxdist;
    dict = idict;
    str = istr;
  }

  private String current() {
    int i;
    String tmp = "";
    for (i = 0; i < dst; i++) {
      tmp += buffer[i];
    }
    return tmp;
  }

  private void done() {
    String tmp = current();
    if (!suggestions.contains(tmp)) {
      suggestions.add(tmp);
    }
  }

  private boolean is_ok(char ch) {
    switch (ch) {
    case '(':
      if (top == 64) {
        return false;
      }
      stack[top++] = ')';
      break;

    case '[':
      if (top == 64) {
        return false;
      }
      stack[top++] = ']';
      break;

    case '{':
      if (top == 64) {
        return false;
      }
      stack[top++] = '}';
      break;

    case ')':
      if (top == 0) {
        return false;
      }
      if (stack[top - 1] != ')') {
        return false;
      }
      top--;
      break;

    case ']':
      if (top == 0) {
        return false;
      }
      if (stack[top - 1] != ']') {
        return false;
      }
      top--;
      break;

    case '}':
      if (top == 0) {
        return false;
      }
      if (stack[top - 1] != '}') {
        return false;
      }
      top--;
      break;
    }
    return true;
  }

  private void undo(char ch) {
    switch (ch) {
    case '(':
    case '[':
    case '{':
      top--;
      break;

    case ')':
    case ']':
    case '}':
      stack[top++] = ch;
      break;
    }
  }

  private boolean check_delete(char ch) {
    if (src + 1 == len) {
      return true;
    }
    return str.charAt(src + 1) != ch;
  }

  private void recurse(int state, boolean accept) {
    char ch, nch;
    int i;

    if (src == len) {
      if (accept && top == 0) {
        done();
        return;
      }
      if (ttl > 0 && state != 0) {
        /* Extend truncation */
        i = state;
        do {
          nch = dict.ch(i);
          if (is_ok(nch)) {
            buffer[dst] = nch;
            dst++;
            ttl--;
            recurse(dict.down(i), dict.valid(i));
            dst--;
            ttl++;
            undo(nch);
            if (suggestions.size() == maxCount) {
              return;
            }
          }
          i = dict.across(i);
        } while (i != 0);
      }
      return;
    }

    if (state == 0 && accept) {
      if (ttl > 0 && src + 1 == len) {
        done();
      }
      return;
    }

    /* Exact match */
    i = state;
    ch = str.charAt(src);
    do {
      if (dict.ch(i) == ch && is_ok(ch)) {
        buffer[dst] = ch;
        dst++;
        src++;
        recurse(dict.down(i), dict.valid(i));
        src--;
        dst--;
        undo(ch);
        if (suggestions.size() == maxCount) {
          return;
        }
        break;
      }
      i = dict.across(i);
    } while (i != 0);

    if (ttl > 0) {
      /* Substitution */
      i = state;
      do {
        nch = dict.ch(i);
        if (nch != ch && is_ok(nch)) {
          buffer[dst] = nch;
          dst++;
          src++;
          ttl--;
          recurse(dict.down(i), dict.valid(i));
          src--;
          dst--;
          ttl++;
          undo(nch);
          if (suggestions.size() == maxCount) {
            return;
          }
        }
        i = dict.across(i);
      } while (i != 0);

      /* Delete insertion */
      if (check_delete(ch)) {
        src++;
        ttl--;
        recurse(state, accept);
        src--;
        ttl++;
        if (suggestions.size() == maxCount) {
          return;
        }
      }

      /* Insert deletion */
      i = state;
      do {
        nch = dict.ch(i);
        if (nch != ch && is_ok(nch)) {
          buffer[dst] = nch;
          dst++;
          ttl--;
          recurse(dict.down(i), dict.valid(i));
          dst--;
          ttl++;
          undo(nch);
          if (suggestions.size() == maxCount) {
            return;
          }
        }
        i = dict.across(i);
      } while (i != 0);
    }
  }

  public String suggestion() {
    List<String> suggestions = suggestions(false);
    if (suggestions.size() == 1) {
      return suggestions.get(0);
    }

    return null;
  }

  public List<String> suggestions() {
    return suggestions(true);
  }

  private List<String> suggestions(boolean all) {
    suggestions = new ArrayList<String>();
    int dist;
    for (dist = 0; dist <= maxdist; dist++) {
      suggestions = new ArrayList<String>();
      maxCount = all ? Integer.MAX_VALUE : 2;
      ttl = dist;
      src = 0;
      dst = 0;
      top = 0;

      recurse(0, false);

      if (suggestions.size() > 0) {
        return suggestions;
      }
    }
    return suggestions;
  }
}
