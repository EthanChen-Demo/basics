package com.system.io;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;

public class SystemIOTest {
    public static void main(String[] args) {
        OutputStream output = null;
        try {
            output = new FileOutputStream("d:\\data1\\system.out1.txt");
            PrintStream printOut = new PrintStream(output);
            System.setOut(printOut);

            System.out.print("system out redirects !");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
