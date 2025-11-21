package com.example.mini_project_community_center.common.utils;

import java.time.*;
import java.time.format.DateTimeFormatter;

public class DateUtils {
    private static final ZoneId ZONE_KST = ZoneId.of("Asia/Seoul");

    private static final DateTimeFormatter KST_FORMAT
            = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private static final DateTimeFormatter ISO_UTC
            = DateTimeFormatter.ISO_OFFSET_DATE_TIME;

    public static String toKstString(LocalDateTime utcLocalDateTime) {
        if (utcLocalDateTime == null) return null;
        ZonedDateTime zdtUtc = utcLocalDateTime.atZone(ZoneId.of("UTC"));
        ZonedDateTime zdtKst = zdtUtc.withZoneSameInstant(ZONE_KST);
        return zdtKst.format(KST_FORMAT);
    }

    public static String toKstDateString(LocalDate date) {
        if(date == null) return null;
        return date.atStartOfDay().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    public static String toUtcString(LocalDateTime utcLocalDateTime) {
        if (utcLocalDateTime == null) return null;
        OffsetDateTime odt = utcLocalDateTime.atOffset(ZoneOffset.UTC);
        return ISO_UTC.format(odt);
    }

    public static LocalDateTime kstToUtc(LocalDateTime kstDateTime) {
        if (kstDateTime == null) return null;
        return kstDateTime.atZone(ZONE_KST)
                .withZoneSameInstant(ZoneOffset.UTC)
                .toLocalDateTime();
    }

    public static LocalDate kstToUtcDate(LocalDate kstDate) {
        if (kstDate == null) return null;

        LocalDateTime kstDateTime = kstDate.atStartOfDay();

        LocalDateTime utcDateTime = kstDateTime
                .atZone(ZONE_KST)
                .withZoneSameInstant(ZoneOffset.UTC)
                .toLocalDateTime();

        return utcDateTime.toLocalDate();
    }
}
