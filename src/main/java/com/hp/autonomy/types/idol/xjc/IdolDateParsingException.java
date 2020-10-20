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

package com.hp.autonomy.types.idol.xjc;

/**
 * Runtime exception parsing Idol date
 */
class IdolDateParsingException extends RuntimeException {
    private static final long serialVersionUID = -6961539917753126036L;

    IdolDateParsingException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
