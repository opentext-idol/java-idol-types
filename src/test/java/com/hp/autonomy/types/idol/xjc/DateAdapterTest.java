/*
 * Copyright 2016 Hewlett Packard Enterprise Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.types.idol.xjc;

import com.hp.autonomy.types.idol.xjc.DateAdapter;
import com.hp.autonomy.types.idol.xjc.IdolDateParsingException;
import org.junit.Test;

import java.text.ParseException;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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

    @Test(expected = IdolDateParsingException.class)
    public void badDate() {
        new DateAdapter();
        DateAdapter.parseDate("20 Aou 15 16:49:50");
    }

    @Test
    public void parseQueryResponseDate() throws ParseException {
        final String sampleDate = "14:42:52 19/02/2014";
        final Date parsedDate = DateAdapter.parseQueryResponseDate(sampleDate);
        assertNotNull(parsedDate);
        assertEquals(sampleDate, DateAdapter.printQueryResponseDate(parsedDate));
    }

    @Test(expected = IdolDateParsingException.class)
    public void badQueryResponseDate() {
        DateAdapter.parseQueryResponseDate("20 Aou 15 16:49:50");
    }
}
