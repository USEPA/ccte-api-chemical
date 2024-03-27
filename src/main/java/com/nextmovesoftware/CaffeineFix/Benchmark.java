package com.nextmovesoftware.CaffeineFix;

import java.io.*;
import java.text.DecimalFormat;

public class Benchmark {
  private static CaffeineFix caffeineFix;
  private static boolean lflag = false;
  private static boolean uflag = false;
  private static int count = 0;
  private static int valid = 0;
  private static int fixed = 0;

  private static void processLine(String line) {
    if (lflag){
      line = line.toLowerCase();
    }
    else if (uflag){
      line = line.toUpperCase();
    }
    count++;
    if (!caffeineFix.cmatch(line)) {
      String suggestion = caffeineFix.suggest(line);
      if (suggestion != null) {
        fixed++;
      }
    } else {
      valid++;
    }
  }

  private static void processInput(InputStream in) throws IOException {
    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
    String line;
    while ((line = reader.readLine()) != null) {
      if (line.length() == 0) {
        continue;
      }
      char ch = line.charAt(0);
      if (ch == '#' || ch == '\0' || ch == '\n' || ch == '\r' || ch == '\t') {
        continue;
      }
      processLine(line);
    }
    DecimalFormat format = new DecimalFormat("0.00");
    if (count > 0) {
      double fract = ((double) (100 * valid)) / count;
      System.err.println(valid + "/" + count + " (" + format.format(fract) + "%)");
    } else {
      System.err.println("0/0 (100.00%)");
    }
    if (valid != count) {
      int unmatched = count - valid;
      double fract = ((double) (100 * fixed)) / unmatched;
      System.err.println(fixed + "/" + unmatched + " (" + format.format(fract) + "%)");
    } else {
      System.err.println("0/0 (0.00%)");
    }
  }

  private static void displayUsage() {
    System.err.println("usage:    java -jar benchmark.jar [options] <dict.cfx> [<infile>]");
    System.err.println("options:  -l  Map input file to lower case");
    System.err.println("          -u  Map input file to upper case");
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
              case 'l':
                lflag = true;
                break;
              case 'u':
                uflag = true;
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
        caffeineFix = new CaffeineFix(arg);
        state = 2;
      } else if (state > 1) {
        File f = new File(arg);
        FileInputStream fin = new FileInputStream(f);
        processInput(fin);
        fin.close();
        state = 3;
      }
    }
    if (state == 2) {
      processInput(System.in);
    } else if (state != 3) {
      displayUsage();
    }
  }
}
