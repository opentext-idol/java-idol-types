/*
 * Copyright 2016 Hewlett Packard Enterprise Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.types.idol.responses.answer;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@SuppressWarnings({"TypeMayBeWeakened", "UtilityClass", "WeakerAccess"})
public class AnswerbankXmlTransformer {
    public static ZonedDateTime unmarshalDate(final String xmlGregorianCalendar) {
        return ZonedDateTime.parse(xmlGregorianCalendar, DateTimeFormatter.ISO_DATE_TIME);
    }

    public static String marshalDate(final ZonedDateTime dateTime) {
        return dateTime.format(DateTimeFormatter.ISO_DATE_TIME);
    }
}
