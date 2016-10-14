/*
 * Copyright 2016 Hewlett Packard Enterprise Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
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
