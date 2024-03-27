package com.nextmovesoftware.CaffeineFix;

import java.io.*;

public class ChemGrep {
  private static CaffeineFix caffeineFix;
  private static int count = 0;
  private static String inpname = null;
  private static String outname = null;
  private static boolean cflag = false;
  private static boolean vflag = false;

  static void processLine(String line, Writer writer) throws IOException {
    if (caffeineFix.cmatch(line) ^ vflag) {
      if (!cflag) {
        writer.write(line + "\n");
      } else {
        count++;
      }
    }
  }

  static void processFile(InputStream in, OutputStream out) throws IOException {
    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));
    String line;
    while ((line = reader.readLine()) != null) {
      if (line.length() == 0) {
        continue;
      }
      char ch = line.charAt(0);
      if (ch != '#' && ch != '\t' && ch != '\0') {
        processLine(line, writer);
      }
    }
    if (cflag) {
      writer.write(count + "\n");
    }
    writer.flush();
  }

  static void displayUsage() {
    System.err.println("usage:  chemgrep [-c][-v] [<infile> [<outfile>]]");
    System.exit(1);
  }

  private static void processCommandLine(String[] args) {
    int i = 0;
    for (String arg : args) {
      if (arg.startsWith("-") && arg.length() > 1) {
        if (arg.charAt(1) == 'c' && arg.length() == 2) {
          cflag = true;
        } else if (arg.charAt(1) == 'v' && arg.length() == 2) {
          vflag = true;
        } else {
          displayUsage();
        }
      } else {
        switch (i++) {
        case 0:
          inpname = arg;
          break;
        case 1:
          outname = arg;
          break;
        default:
          displayUsage();
          break;
        }
      }
    }
  }

  public static void main(String[] args) throws IOException {
    processCommandLine(args);
    InputStream in = null;
    OutputStream out = null;
    if (inpname != null) {
      try {
        in = new FileInputStream(new File(inpname));
      } catch (FileNotFoundException e) {
        System.err.println("Error: Unable to open input file!");
        System.exit(1);
      }
    } else {
      in = System.in;
    }
    if (outname != null) {
      try {
        out = new FileOutputStream(new File(outname));
      } catch (FileNotFoundException e) {
        System.err.println("Error: Unable to create output file!");
        System.exit(1);
      }
    } else {
      out = System.out;
    }
    caffeineFix = new CaffeineFix("../data/dict.cfx");
    processFile(in, out);
    in.close();
    out.close();
  }
}
