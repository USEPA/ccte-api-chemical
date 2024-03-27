package com.nextmovesoftware.CaffeineFix;

import java.io.*;

public class Dictgrep {
  private static CaffeineFixDictionary dict;

  private static int total = 0;
  private static int files = 0;
  private static boolean cflag = false;
//  private static boolean iflag = false;
  private static boolean lflag = false;
  private static boolean uflag = false;
  private static boolean vflag = false;

  private static boolean match(String str) {
    int len = str.length();
    if (len == 0) {
      return false;
    }

    CaffeineFixStack stack = new CaffeineFixStack();
    char ch = str.charAt(0);
    int state = 0;
    int i = 1;
    if (lflag && ch >= 'A' && ch <= 'Z') {
      ch += 32; /* tolower */
    }
    if (uflag && ch >= 'a' && ch <= 'z') {
      ch -= 32; /* toupper */
    }

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
        if (lflag && ch >= 'A' && ch <= 'Z') {
          ch += 32; /* tolower */
        }
        if (uflag && ch >= 'a' && ch <= 'z') {
          ch -= 32; /* toupper */
        }
      } else {
        state = dict.across(state);
      }
      if (state == 0) {
        return false;
      }
    }
  }

  private static boolean matchLine(String line) {
    return match(line);
  }

  private static void processInputStream(InputStream is) throws IOException {
    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
    int count = 0;
    String line;
    while ((line = reader.readLine()) != null) {
      if (matchLine(line) ^ vflag) {
        if (!cflag) {
          System.out.println(line);
        } else {
          count++;
        }
      }
    }

    if (cflag) {
      System.out.println(count);
    }
    total += count;
    files++;
  }

  private static void processFilename(String fileName) throws IOException {
    File f = new File(fileName);
    FileInputStream fin = new FileInputStream(f);
    processInputStream(fin);
    fin.close();
  }

  private static void loadDictionary(String fname) {
    dict = new CaffeineFixDictionary(fname);
  }

  private static void displayUsage() {
    System.err.println("usage:    java -jar dictgrep.jar [options] <dict.cfx> [<infile> ...]");
    System.err.println("options:  -c  Count matching lines");
    /* System.err.println("          -i  Ignore case distinctions"); */
    System.err.println("          -l  Map input file to lower case");
    System.err.println("          -u  Map input file to upper case");
    System.err.println("          -v  Invert the sense of matching");
    System.exit(1);
  }

  public static void main(String[] args) throws IOException {
    int state = 0;

    for (int i = 0; i < args.length; i++) {
      String arg = args[i];
      if (state == 0) {
        if (arg.length() > 1 && arg.charAt(0) == '-') {
          if (arg.length() == 2 && arg.charAt(1) == '-') {
            state = 1;
            continue;
          } else {
            for (int j = 1; j < arg.length(); j++) {
              char flagChar = arg.charAt(j);
              switch (flagChar) {
              case 'c':
                cflag = true;
                break;
//              case 'i':
//                iflag = true;
//                break;
              case 'l':
                lflag = true;
                break;
              case 'u':
                uflag = true;
                break;
              case 'v':
                vflag = true;
                break;
              default:
                displayUsage();
              }
            }
          }
        } else {
          state = 1;
        }
      }
      if (state == 1) {
        loadDictionary(arg);
        state = 2;
      } else if (state > 1) {
        processFilename(arg);
        state = 3;
      }
    }
    if (state == 2) {
      processInputStream(System.in);
    } else if (state != 3) {
      displayUsage();
    }
    if (cflag && files > 1) {
      System.err.println(total + " matches in " + files + " files");
    }
  }

}
