package org.wangwei;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        String s = "0000000";
        int size = 6;
        if (s.length() > size) {
            System.out.println(s.substring(0, size));
        }
    }
}
