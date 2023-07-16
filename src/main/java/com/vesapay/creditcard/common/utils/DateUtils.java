package com.vesapay.creditcard.common.utils;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

public final class DateUtils {

    private DateUtils() {
    }

    public static OffsetDateTime convertToOffsetDateTime(Timestamp timestamp) {
        return Optional.ofNullable(timestamp).map(timestampValue -> {
            long millisSinceEpoch = timestamp.getTime();
            Instant instant = Instant.ofEpochMilli(millisSinceEpoch);
            return OffsetDateTime.ofInstant(instant, ZoneId.of("UTC")).truncatedTo(ChronoUnit.SECONDS);
        }).orElse(null);
    }

    public static Timestamp convertToTimestamp(OffsetDateTime offsetDateTime) {
        return Optional.ofNullable(offsetDateTime)
                .map(offsetDateTimeValue ->
                        Timestamp.valueOf(offsetDateTimeValue.truncatedTo(ChronoUnit.SECONDS)
                                .atZoneSameInstant(ZoneOffset.UTC)
                                .toLocalDateTime()))
                .orElse(null);
    }

    public static boolean isValid(String datTimeValue) {
        try {
            OffsetDateTime.parse(datTimeValue, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static OffsetDateTime toDateTime(String datTimeValue) {
        return Optional.ofNullable(datTimeValue).map(value ->
                        OffsetDateTime.parse(datTimeValue, DateTimeFormatter.ISO_OFFSET_DATE_TIME)
                                .truncatedTo(ChronoUnit.SECONDS))
                .orElseThrow(() -> new IllegalArgumentException("Invalid date value"));
    }

}
