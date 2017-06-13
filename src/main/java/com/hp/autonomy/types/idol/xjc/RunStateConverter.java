/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.types.idol.xjc;

import com.hp.autonomy.types.idol.responses.coordinator.RunState;

public class RunStateConverter {

    public static RunState parse(final String input) {
        return RunState.valueOf(input);
    }

    public static String print(final RunState input) {
        return input.name();
    }

}
