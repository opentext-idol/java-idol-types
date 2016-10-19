/*
 * Copyright 2016 Hewlett Packard Enterprise Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.types.idol.marshalling.processors;

import com.autonomy.aci.client.services.Processor;
import com.autonomy.aci.client.transport.AciResponseInputStream;
import com.hp.autonomy.types.idol.marshalling.marshallers.ResponseDataParser;

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
