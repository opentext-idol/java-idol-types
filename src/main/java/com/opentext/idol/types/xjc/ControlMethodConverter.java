/*
 * (c) Copyright 2015 Micro Focus or one of its affiliates.
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

package com.opentext.idol.types.xjc;

import com.opentext.idol.types.responses.coordinator.ControlMethod;

public class ControlMethodConverter {

    public static ControlMethod parse(final String input) {
        return ControlMethod.valueOf(input);
    }

    public static String print(final ControlMethod input) {
        return input.name();
    }

}
