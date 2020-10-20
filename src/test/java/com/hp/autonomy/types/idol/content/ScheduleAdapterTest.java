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
import com.hp.autonomy.types.idol.content.Schedule;
import com.hp.autonomy.types.idol.content.ScheduleAdapter;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ScheduleAdapterTest {
    private ScheduleAdapter adapter;

    @Before
    public void setUp() {
        adapter = new ScheduleAdapter();
    }

    @Test
    public void unmarshal() {
        unmarshal("BEGIN:VCALENDAR+\\\\VERSION:2.0+\\\\PRODID:IDOL Search Optimizer+\\\\BEGIN:VEVENT+\\\\UID:27779db8-553c-4922-94d9-a13102e46fab+\\\\DTSTAMP:20150210T175405Z+\\\\DTSTART;VALUE=DATE:20150211+\\\\DTEND;VALUE=DATE:20150213+\\\\RRULE:FREQ=MONTHLY;UNTIL=20150520T230000Z+\\\\END:VEVENT+\\\\END:VCALENDAR+\\\\");
    }

    private Schedule unmarshal(final String scheduleAsString) {
        final Schedule schedule = adapter.unmarshal(scheduleAsString);
        assertNotNull(schedule.getProductId());
        assertNotNull(schedule.getStartDate());
        assertNotNull(schedule.getEndDate());
        assertNotNull(schedule.getUntil());
        assertEquals(Recurrence.Frequency.MONTHLY, schedule.getFrequency());
        assertTrue(schedule.validate().isEmpty());
        return schedule;
    }

    @Test
    public void unmarshalEmptyNode() {
        assertNull(adapter.unmarshal(null));
        assertNull(adapter.unmarshal(""));
    }

    @Test
    public void marshal() {
        final Schedule schedule = new Schedule.Builder()
                .setProductId("Idol Types")
                .setStartDate(new DateTime(1423612800000L))
                .setEndDate(new DateTime(1423785600000L))
                .setUntil(new DateTime(1432162800000L))
                .setFrequency(Recurrence.Frequency.MONTHLY)
                .build();
        final String scheduleAsString = adapter.marshal(schedule);
        assertNotNull(scheduleAsString);
        final Schedule outputSchedule = unmarshal(scheduleAsString);
        assertEquals(schedule, outputSchedule);
    }
}
