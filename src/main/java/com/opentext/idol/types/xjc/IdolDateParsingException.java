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

package com.opentext.idol.types.xjc;

/**
 * Runtime exception parsing Idol date
 */
class IdolDateParsingException extends RuntimeException {
    private static final long serialVersionUID = -6961539917753126036L;

    IdolDateParsingException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
