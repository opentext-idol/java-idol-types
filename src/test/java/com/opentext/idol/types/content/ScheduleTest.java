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

package com.opentext.idol.types.content;

import biweekly.util.Recurrence;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

public class ScheduleTest {
    private final Schedule schedule = new Schedule.Builder()
            .setProductId("Idol Types")
            .setStartDate(Instant.ofEpochMilli(1423612800000L))
            .setEndDate(Instant.ofEpochMilli(1423785600000L))
            .setUntil(Instant.ofEpochMilli(1432162800000L))
            .setFrequency(Recurrence.Frequency.MONTHLY)
            .build();

    @Test
    public void json() throws IOException {
        final ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
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
