/*============================================*/
/* Copyright (c) 2011-2014 NextMove Software  */
/*============================================*/
package com.nextmovesoftware.CaffeineFix;

public class CaffeineFixStack {
  private char[] stack;
  private int top;

  public CaffeineFixStack() {
    stack = new char[64];
    top = 0;
  }

  public boolean empty() {
    return top == 0;
  }

  public boolean fail(char ch) {
    switch (ch) {
    case '(':
      if (top == 64) {
        return true;
      }
      stack[top++] = ')';
      break;

    case '[':
      if (top == 64) {
        return true;
      }
      stack[top++] = ']';
      break;

    case '{':
      if (top == 64) {
        return true;
      }
      stack[top++] = '}';
      break;

    case ')':
      if (top == 0) {
        return true;
      }
      if (stack[top - 1] != ')') {
        return true;
      }
      top--;
      break;

    case ']':
      if (top == 0) {
        return true;
      }
      if (stack[top - 1] != ']') {
        return true;
      }
      top--;
      break;

    case '}':
      if (top == 0) {
        return true;
      }
      if (stack[top - 1] != '}') {
        return true;
      }
      top--;
      break;
    }
    return false;
  }
}
