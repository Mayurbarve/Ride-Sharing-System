package com.airtribe.ridewise.util;

import java.util.concurrent.atomic.AtomicInteger;

public final class IdGenerator {
    private static final AtomicInteger COUNTER = new AtomicInteger(0);

    private IdGenerator() {}

    public static String next(String prefix) {
        return prefix + "-" + COUNTER.incrementAndGet();
    }
}
