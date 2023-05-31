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

package com.opentext.idol.types.marshalling.processors;

import com.autonomy.aci.client.services.Processor;
import com.autonomy.aci.client.transport.AciResponseInputStream;
import com.opentext.idol.types.marshalling.marshallers.ResponseDataParser;

/**
 * Generic processor for handling Idol responses with known content type.
 * Note that this uses DOM processing behind the scenes so should not be used for very large responses.
 */
@SuppressWarnings({"WeakerAccess", "NonSerializableFieldInSerializableClass"})
public class ResponseDataProcessor<T> implements Processor<T> {
    private static final long serialVersionUID = -1983490659468698548L;

    private final ResponseDataParser<T> responseDataMarshaller;

    public ResponseDataProcessor(final ResponseDataParser<T> responseDataMarshaller) {
        this.responseDataMarshaller = responseDataMarshaller;
    }

    @Override
    public T process(final AciResponseInputStream aciResponse) {
        return responseDataMarshaller.parseResponseData(aciResponse);
    }
}
