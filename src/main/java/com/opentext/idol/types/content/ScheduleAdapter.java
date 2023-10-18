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

import biweekly.Biweekly;
import biweekly.ICalendar;
import biweekly.component.VEvent;
import biweekly.property.DateEnd;
import biweekly.property.DateStart;
import biweekly.util.ICalDate;
import biweekly.util.Recurrence;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;
import org.apache.commons.lang3.StringUtils;

import java.time.Instant;
import java.util.Date;
import java.util.regex.Pattern;

@SuppressWarnings("WeakerAccess")
public class ScheduleAdapter extends XmlAdapter<String, Schedule> {
    private static final Pattern UNMARSHAL_PATTERN = Pattern.compile("\\+\\\\\\\\");
    private static final Pattern MARSHAL_PATTERN = Pattern.compile("\r\n");

    @Override
    public Schedule unmarshal(final String scheduleAsString) {
        if (StringUtils.isBlank(scheduleAsString)) {
            return null;
        }

        final String correctCalendar = UNMARSHAL_PATTERN.matcher(scheduleAsString).replaceAll("\r\n");
        final ICalendar iCalendar = Biweekly.parse(correctCalendar).first();

        String productId = null;
        if (iCalendar.getProductId() != null) {
            productId = iCalendar.getProductId().getValue();
        }

        final VEvent event = iCalendar.getEvents().get(0);
        final Instant startDate = event.getDateStart().getValue().toInstant();
        final Instant endDate = event.getDateEnd().getValue().toInstant();

        Instant until = null;
        Recurrence.Frequency frequency = null;

        if (event.getRecurrenceRule() != null) {
            frequency = event.getRecurrenceRule().getValue().getFrequency();

            if (event.getRecurrenceRule().getValue().getUntil() != null) {
                until = event.getRecurrenceRule().getValue().getUntil().getRawComponents().toDate().toInstant();
            }
        }

        return new Schedule.Builder()
                .setProductId(productId)
                .setStartDate(startDate)
                .setEndDate(endDate)
                .setUntil(until)
                .setFrequency(frequency)
                .build();
    }

    @Override
    public String marshal(final Schedule schedule) {
        final ICalendar iCalendar = toICalendar(schedule);
        // we need the replace because IDOL is doing it wrong
        // we need 8 slashes because the replacement string is not really a literal
        return MARSHAL_PATTERN.matcher(iCalendar.write()).replaceAll("+\\\\\\\\");
    }

    public ICalendar toICalendar(final Schedule schedule) {
        final ICalendar iCalendar = new ICalendar();

        final VEvent event = new VEvent();
        event.setDateStart(new DateStart(Date.from(schedule.getStartDate()), true));
        event.setDateEnd(new DateEnd(Date.from(schedule.getEndDate()), true));

        final Recurrence.Frequency frequency = schedule.getFrequency();
        if (frequency != null) {
            final Recurrence.Builder builder = new Recurrence.Builder(frequency);

            final Instant until = schedule.getUntil();
            if (until != null) {
                builder.until(new ICalDate(Date.from(until)));
            }

            event.setRecurrenceRule(builder.build());
        }

        iCalendar.addEvent(event);
        iCalendar.setProductId(schedule.getProductId());

        return iCalendar;
    }
}
