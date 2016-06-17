/*
 * Copyright 2016 Hewlett Packard Enterprise Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.types.idol;

import biweekly.ICalVersion;
import biweekly.ValidationWarnings;
import biweekly.util.Recurrence;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.joda.time.DateTime;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;

@SuppressWarnings("WeakerAccess")
@Getter
@EqualsAndHashCode
@JsonDeserialize(builder = Schedule.Builder.class)
@XmlJavaTypeAdapter(ScheduleAdapter.class)
public class Schedule implements Serializable {
    private static final long serialVersionUID = 7216265876306134378L;

    private final String productId;
    private final DateTime startDate;
    private final DateTime endDate;
    private final DateTime until;
    private final Recurrence.Frequency frequency;

    private Schedule(final Builder builder) {
        productId = builder.productId;
        startDate = builder.startDate;
        endDate = builder.endDate;
        until = builder.until;
        frequency = builder.frequency;
    }

    public ValidationWarnings validate() {
        return new ScheduleAdapter().toICalendar(this).validate(ICalVersion.V2_0);
    }

    @Override
    public String toString() {
        return new ScheduleAdapter().marshal(this);
    }

    @SuppressWarnings("WeakerAccess")
    @Accessors(chain = true)
    @Setter
    @JsonPOJOBuilder(withPrefix = "set")
    public static class Builder {
        private String productId;
        private DateTime startDate;
        private DateTime endDate;
        private DateTime until;
        private Recurrence.Frequency frequency;

        public Schedule build() {
            return new Schedule(this);
        }
    }
}
