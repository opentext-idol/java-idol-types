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

package com.opentext.idol.types.responses.answer;

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
