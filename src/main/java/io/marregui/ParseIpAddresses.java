package io.marregui;

import java.util.regex.Pattern;

public class ParseIpAddresses {
    // Write a class called MyRegex which will contain a string pattern. You need to write
    // a regular expression and assign it to the pattern such that it can be used to validate
    // an IP address. Use the following definition of an IP address:
    //
    // IP address is a string in the form "A.B.C.D", where the value of A, B, C, and D may
    // range from 0 to 255. Leading zeros are allowed. The length of A, B, C, or D can't
    // be greater than 3.
    // Some valid IP address:
    //
    // 000.12.12.034
    // 121.234.12.12
    // 23.45.12.56
    // Some invalid IP address:
    //
    // 000.12.234.23.23
    // 666.666.23.23
    // .213.123.23.32
    // 23.45.22.32.
    // I.Am.not.an.ip
    // In this problem you will be provided strings containing any combination of ASCII characters.
    // You have to write a regular expression to find the valid IPs.
    //
    // Just write the MyRegex class which contains a String . The string should contain the correct regular expression.
    //
    // (MyRegex class MUST NOT be public)
    //
    // Sample Input
    //
    // 000.12.12.034
    // 121.234.12.12
    // 23.45.12.56
    // 00.12.123.123123.123
    // 122.23
    // Hello.IP
    // Sample Output
    //
    // true
    // true
    // true
    // false
    // false
    // false

    public static void main(String[] args) throws Exception {
        Pattern pattern = Pattern.compile("^(([0-1]?[0-9]?[0-9]?|2[0-4][0-9]|25[0-5])\\.){3}([0-1]?[0-9]?[0-9]?|2[0-4][0-9]|25[0-5]){1}$");
        for (String line : """
                12.12.12.12
                13.13.13.112
                VUUT.12.12
                111.111.11.111
                1.1.1.1.1.1.1
                .....
                1...1..1..1
                0.0.0.0
                255.0.255.0
                266.266.266.266
                00000.000000.0000000.00001
                0023.0012.0012.0034
                """.split("\n")) {
            System.out.printf("%s -> %b%n", line, pattern.matcher(line).matches());
        }

        // true
        // true
        // false
        // true
        // false
        // false
        // false
        // true
        // true
        // false
        // false
        // false
    }
}
