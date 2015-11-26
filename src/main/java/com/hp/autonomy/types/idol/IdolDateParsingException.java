/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.types.idol;

/**
 * Runtime exception parsing Idol date
 */
public class IdolDateParsingException extends RuntimeException {
    private static final long serialVersionUID = -6961539917753126036L;

    public IdolDateParsingException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
