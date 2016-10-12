/*
 * Copyright 2016 Hewlett Packard Enterprise Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.types.idol;


import com.autonomy.aci.client.services.AciErrorException;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * Builds error exception from Idol error response
 */
@SuppressWarnings({"UseOfObsoleteDateTimeApi", "WeakerAccess"})
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class AciErrorExceptionBuilder {
    private String errorId;
    private String rawErrorId;
    private String errorString;
    private String errorDescription;
    private String errorCode;
    private Date errorTime;

    public AciErrorExceptionBuilder(final Error error) {
        errorId = error.getErrorid();
        rawErrorId = error.getRawerrorid();
        errorString = error.getErrorstring();
        errorDescription = error.getErrordescription();
        errorCode = error.getErrorcode();
        errorTime = error.getErrortime();
    }

    public AciErrorException build() {
        final AciErrorException aciErrorException = new AciErrorException();
        aciErrorException.setErrorId(errorId);
        aciErrorException.setRawErrorId(rawErrorId);
        aciErrorException.setErrorString(errorString);
        aciErrorException.setErrorDescription(errorDescription);
        aciErrorException.setErrorCode(errorCode);
        aciErrorException.setErrorTime(errorTime);
        return aciErrorException;
    }
}
