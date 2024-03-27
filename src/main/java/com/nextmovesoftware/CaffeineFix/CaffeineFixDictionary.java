/*===================================================*/
/* Copyright (c) 2010-2014 NextMove Software         */
/*===================================================*/
package com.nextmovesoftware.CaffeineFix;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class CaffeineFixDictionary {
  private final byte fsm[];

  public CaffeineFixDictionary(String fname) {
    File file = new File(fname);
    fsm = new byte[(int) file.length()];

    FileInputStream fis = null;
    try {
      fis = new FileInputStream(file);
      fis.read(fsm);
    } catch (IOException e) {
      throw new RuntimeException(e.getMessage(), e);
    } finally {
      if (fis != null) {
        try {
          fis.close();
        } catch (IOException e) {
        }
      }
    }
  }

  public char ch(int state) {
    return (char) (fsm[state] & 0xff);
  }

  public boolean valid(int state) {
    return fsm[state + 1] != 0;
  }

  public int down(int state) {
    int b0 = fsm[state + 2] & 0xff;
    int b1 = fsm[state + 3] & 0xff;
    int b2 = fsm[state + 4] & 0xff;
    int b3 = fsm[state + 5] & 0xff;
    int temp = b0 + (b1 << 8) + (b2 << 16) + (b3 << 24);
    return 10 * temp;
  }

  public int across(int state) {
    int b0 = fsm[state + 6] & 0xff;
    int b1 = fsm[state + 7] & 0xff;
    int b2 = fsm[state + 8] & 0xff;
    int b3 = fsm[state + 9] & 0xff;
    int temp = b0 + (b1 << 8) + (b2 << 16) + (b3 << 24);
    return 10 * temp;
  }
}
