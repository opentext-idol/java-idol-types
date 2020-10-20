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

package com.hp.autonomy.types.idol.content;

import biweekly.util.Recurrence;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.hp.autonomy.types.idol.content.Schedule;
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
