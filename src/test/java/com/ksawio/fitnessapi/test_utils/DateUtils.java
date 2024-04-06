package com.ksawio.fitnessapi.test_utils;

import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public final class DateUtils {
    static private final long now = System.currentTimeMillis();
    static private final long hundredYearsAgo = now - TimeUnit.DAYS.toMillis(365 * 100);

    // prevent initialization
    private DateUtils() {}

    public static Date randomDate() {
        long randomMillisSinceEpoch = ThreadLocalRandom.current()
                .nextLong(hundredYearsAgo, now);
        return new Date(randomMillisSinceEpoch);
    }
}
