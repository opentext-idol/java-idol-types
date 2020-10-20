/*
 * (c) Copyright 2016 Micro Focus or one of its affiliates.
 *
 * Licensed under the MIT License (the "License"); you may not use this file
 * except in compliance with the License.
 *
 * The only warranties for products and services of Micro Focus and its affiliates
 * and licensors ("Micro Focus") are as may be set forth in the express warranty
 * statements accompanying such products and services. Nothing herein should be
 * construed as constituting an additional warranty. Micro Focus shall not be
 * liable for technical or editorial errors or omissions contained herein. The
 * information contained herein is subject to change without notice.
 */

package com.hp.autonomy.types.idol.xjc;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

@SuppressWarnings({"UseOfObsoleteDateTimeApi", "UtilityClass", "WeakerAccess"}) //TODO rework now we can use java8
public class DateAdapter {
    private static final String IDOL_DATE_PATTERN = "dd MMM yy HH:mm:ss";
    private static final String QUERY_RESPONSE_DATE_PATTERN = "HH:mm:ss dd/MM/y[ G]";
    private static final String QUEUE_INFO_DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";

    private static final SimpleDateFormat IDOL_DATE_FORMAT = new SimpleDateFormat(IDOL_DATE_PATTERN, Locale.ENGLISH);
    private static final DateTimeFormatter QUERY_RESPONSE_DATE_FORMAT = DateTimeFormatter.ofPattern(QUERY_RESPONSE_DATE_PATTERN, Locale.ENGLISH)
            .withZone(ZoneOffset.UTC);
    private static final String OPTIONAL_ERA_SUFFIX = " AD";
    private static final DateTimeFormatter QUEUE_INFO_DATE_FORMAT = DateTimeFormatter.ofPattern(QUEUE_INFO_DATE_PATTERN, Locale.ENGLISH)
            .withZone(ZoneOffset.UTC);

    public static Date parseEpochDate(final String epochDate) {
        return new Date(Long.parseLong(epochDate) * 1000);
    }

    public static String printEpochDate(final Date date) {
        return String.valueOf(date.getTime() / 1000);
    }

    public static Date parseDate(final String idolDateString) {
        try {
            return IDOL_DATE_FORMAT.parse(idolDateString);
        } catch (final ParseException e) {
            throw new IdolDateParsingException("Unexpected error parsing date", e);
        }
    }

    public static String printDate(final Date date) {
        return IDOL_DATE_FORMAT.format(date);
    }

    public static ZonedDateTime parseQueryResponseDate(final CharSequence idolDateString) {
        return ZonedDateTime.parse(idolDateString, QUERY_RESPONSE_DATE_FORMAT);
    }

    @SuppressWarnings("TypeMayBeWeakened")
    public static String printQueryResponseDate(final ZonedDateTime date) {
        final String dateString = QUERY_RESPONSE_DATE_FORMAT.format(date);
        return dateString.endsWith(OPTIONAL_ERA_SUFFIX) ? dateString.substring(0, dateString.length() - OPTIONAL_ERA_SUFFIX.length()) : dateString;
    }

    public static ZonedDateTime parseQueueInfoDate(final String date) {
        return ZonedDateTime.parse(date, QUEUE_INFO_DATE_FORMAT);
    }

    public static String printQueueInfoDate(final ZonedDateTime date) {
        return QUEUE_INFO_DATE_FORMAT.format(date);
    }
}
