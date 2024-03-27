package com.nextmovesoftware.CaffeineFix;

import java.io.*;

public class DictCorrect {
  private static CaffeineFix caffeineFix;
  private static int fixed = 0;

  private static void processLine(String line) {
    if (!caffeineFix.cmatch(line)) {
      String suggestion = caffeineFix.suggest(line);
      if (suggestion != null) {
        System.out.println(suggestion);
        fixed++;
        return;
      }
    }
    System.out.println(line);
  }

  private static void processInput(InputStream in) throws IOException {
    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
    String line;
    while ((line = reader.readLine()) != null) {
      if (line.length() == 0) {
        System.out.println(line);
        continue;
      }
      char ch = line.charAt(0);
      if (ch == '#' || ch == '\0' || ch == '\n' || ch == '\r' || ch == '\t') {
        System.out.println(line);
        continue;
      }
      processLine(line);
    }
    if (fixed !=1){
      System.err.println(fixed +" changes");
    }
    else{
      System.err.println("1 change");
    }
  }

  private static void displayUsage() {
    System.err.println("usage:    java -jar dictcorrect.jar <dict.cfx> [<infile>]");
    System.exit(1);
  }

  public static void main(String[] args) throws IOException {
    if (args.length != 1 && args.length != 2) {
      displayUsage();
    }
    caffeineFix = new CaffeineFix(args[0]);
    if (args.length == 2) {
      File f = new File(args[1]);
      FileInputStream fin = new FileInputStream(f);
      processInput(fin);
      fin.close();
    } else {
      processInput(System.in);
    }
  }
}
