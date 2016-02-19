/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.types.idol;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@SuppressWarnings({"UseOfObsoleteDateTimeApi", "UtilityClass"}) //TODO rework once we can use java8
public class DateAdapter {
    private static final String IDOL_DATE_PATTERN = "dd MMM yy HH:mm:ss";
    private static final String QUERY_RESPONSE_DATE_PATTERN = "HH:mm:ss dd/MM/yyyy";
    private static final SimpleDateFormat IDOL_DATE_FORMAT = new SimpleDateFormat(IDOL_DATE_PATTERN, Locale.ENGLISH);
    private static final SimpleDateFormat QUERY_RESPONSE_DATE_FORMAT = new SimpleDateFormat(QUERY_RESPONSE_DATE_PATTERN, Locale.ENGLISH);

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

    public static Date parseQueryResponseDate(final String idolDateString) {
        try {
            return QUERY_RESPONSE_DATE_FORMAT.parse(idolDateString);
        } catch (final ParseException e) {
            throw new IdolDateParsingException("Unexpected error parsing date", e);
        }
    }

    public static String printQueryResponseDate(final Date date) {
        return QUERY_RESPONSE_DATE_FORMAT.format(date);
    }
}
