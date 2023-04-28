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

package com.opentext.idol.types.marshalling.marshallers;


import com.autonomy.aci.client.services.AciErrorException;
import com.opentext.idol.types.responses.Error;

import java.util.Date;

/**
 * Builds error exception from Idol error response
 */
@SuppressWarnings({"UseOfObsoleteDateTimeApi", "WeakerAccess"})
public class AciErrorExceptionBuilder {
    private final String errorId;
    private final String rawErrorId;
    private final String errorString;
    private final String errorDescription;
    private final String errorCode;
    private final Date errorTime;

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
