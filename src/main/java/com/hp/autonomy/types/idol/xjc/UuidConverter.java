/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.types.idol.xjc;

import java.util.UUID;

public class UuidConverter {
    public static UUID parse(final String xmlValue) {
        return UUID.fromString(xmlValue);
    }

    public static String print(final UUID value) {
        return value.toString();
    }
}
