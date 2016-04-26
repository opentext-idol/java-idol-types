/*
 * Copyright 2016 Hewlett Packard Enterprise Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.types.idol;

import biweekly.util.Recurrence;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class ScheduleTest {
    private final Schedule schedule = new Schedule.Builder()
            .setProductId("Idol Types")
            .setStartDate(new DateTime(1423612800000L, DateTimeZone.UTC))
            .setEndDate(new DateTime(1423785600000L, DateTimeZone.UTC))
            .setUntil(new DateTime(1432162800000L, DateTimeZone.UTC))
            .setFrequency(Recurrence.Frequency.MONTHLY)
            .build();

    @Test
    public void json() throws IOException {
        final ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JodaModule());
        final byte[] json = objectMapper.writeValueAsBytes(schedule);
        assertNotNull(json);
        final Schedule processedSchedule = objectMapper.readValue(json, Schedule.class);
        assertEquals(schedule, processedSchedule);
    }

    @Test
    public void toStringTest() {
        assertTrue(schedule.toString().contains("Idol Types"));
    }
}
