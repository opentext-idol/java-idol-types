/*
 * Copyright 2016 Hewlett Packard Enterprise Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.types.idol.processors;

import com.autonomy.aci.client.services.Processor;
import com.autonomy.aci.client.transport.AciResponseInputStream;
import com.hp.autonomy.types.idol.IdolJaxbMarshaller;
import com.hp.autonomy.types.idol.QueryResponse;

@SuppressWarnings({"WeakerAccess", "NonSerializableFieldInSerializableClass"})
public class QueryAciResponseJaxbProcessor<T extends QueryResponse> implements Processor<T> {
    private static final long serialVersionUID = -1983490659468698548L;

    private final IdolJaxbMarshaller idolXmlMarshaller;
    private final Class<T> responseDataType;
    private final Class<?> contentType;

    public QueryAciResponseJaxbProcessor(final IdolJaxbMarshaller idolXmlMarshaller,
                                         final Class<T> responseDataType,
                                         final Class<?> contentType) {
        this.idolXmlMarshaller = idolXmlMarshaller;
        this.responseDataType = responseDataType;
        this.contentType = contentType;
    }

    @Override
    public T process(final AciResponseInputStream aciResponseInputStream) {
        return idolXmlMarshaller.parseIdolQueryResponseData(aciResponseInputStream, responseDataType, contentType);
    }
}
