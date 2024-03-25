package ru.job4j.io;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UsageLog4j {

    private static final Logger LOG = LoggerFactory.getLogger(UsageLog4j.class.getName());

    public static void main(String[] args) {
        LOG.trace("trace message");
        LOG.debug("debug message");
        LOG.info("info message");
        LOG.warn("warn message");
        LOG.error("error message");
        byte a = -127;
        short b = -1;
        int c = 7;
        long d = 3L;
        float e = -0.14F;
        double f = 3.14D;
        boolean bool = true;
        char symbol = '♥';
        LOG.debug("""
                information about types of local variables:\s
                byte: {},\s
                short: {},\s
                int: {},\s
                long: {},\s
                float: {},\s
                double: {},\s
                boolean: {},\s
                char: {}\s""", a, b, c, d, e, f, bool, symbol);
    }
}