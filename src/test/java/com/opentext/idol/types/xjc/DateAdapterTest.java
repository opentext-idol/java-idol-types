/*
 * Copyright 2016 Open Text.
 *
 * Licensed under the MIT License (the "License"); you may not use this file
 * except in compliance with the License.
 *
 * The only warranties for products and services of Open Text and its affiliates
 * and licensors ("Open Text") are as may be set forth in the express warranty
 * statements accompanying such products and services. Nothing herein should be
 * construed as constituting an additional warranty. Open Text shall not be
 * liable for technical or editorial errors or omissions contained herein. The
 * information contained herein is subject to change without notice.
 */

package com.opentext.idol.types.xjc;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.time.ZonedDateTime;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SuppressWarnings("UseOfObsoleteDateTimeApi")
public class DateAdapterTest {
    @Test
    public void parseEpochDate() throws ParseException {
        final String sampleDate = "0";
        final Date parsedDate = DateAdapter.parseEpochDate(sampleDate);
        assertNotNull(parsedDate);
        assertEquals(sampleDate, DateAdapter.printEpochDate(parsedDate));
    }

    @Test
    public void parseDate() throws ParseException {
        final String sampleDate = "20 Nov 15 16:49:50";
        final Date parsedDate = DateAdapter.parseDate(sampleDate);
        assertNotNull(parsedDate);
        assertEquals(sampleDate, DateAdapter.printDate(parsedDate));
    }

    @Test
    public void badDate() {
        Assertions.assertThrows(IdolDateParsingException.class,
            () -> DateAdapter.parseDate("20 Aou 15 16:49:50"));
    }

    @Test
    public void parseQueryResponseDate() throws ParseException {
        final String sampleDate = "14:42:52 19/02/2014";
        final ZonedDateTime parsedDate = DateAdapter.parseQueryResponseDate(sampleDate);
        assertNotNull(parsedDate);
        assertEquals(sampleDate, DateAdapter.printQueryResponseDate(parsedDate));
    }

    @Test
    public void parseBCQueryResponseDate() throws ParseException {
        final String sampleDate = "01:00:00 01/01/1 BC";
        final ZonedDateTime parsedDate = DateAdapter.parseQueryResponseDate(sampleDate);
        assertNotNull(parsedDate);
        assertEquals(sampleDate, DateAdapter.printQueryResponseDate(parsedDate));
    }
}
