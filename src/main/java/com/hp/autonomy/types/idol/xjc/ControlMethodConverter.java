/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.types.idol.xjc;

import com.hp.autonomy.types.idol.responses.coordinator.ControlMethod;

public class ControlMethodConverter {

    public static ControlMethod parse(final String input) {
        return ControlMethod.valueOf(input);
    }

    public static String print(final ControlMethod input) {
        return input.name();
    }

}
