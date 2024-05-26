package io.marregui;

import io.marregui.util.ILogger;
import io.marregui.util.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

public class LoggerTest {


    @Test
    public void test0() {
        ILogger log0 = Logger.loggerFor(LoggerTest.class);
        ILogger log = Logger.loggerFor(LoggerTest.class);
        Assertions.assertTrue(log== log0);

        PrintStream originalOut = System.out;
        ByteArrayOutputStream bos = new ByteArrayOutputStream(255);
        try(PrintStream out = new PrintStream(bos)) {
            System.setOut(out);
            log.info("%s is %d %b or not!%%%tyes","hey", 0, true);
        }
        String s = bos.toString(StandardCharsets.UTF_8);
        System.setOut(originalOut);
        System.out.println(s);

    }
}
