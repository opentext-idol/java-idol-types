/*
 * Copyright 2016 Hewlett Packard Enterprise Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.types.idol.processors;

import com.autonomy.aci.client.services.Processor;
import com.hp.autonomy.types.idol.IdolJaxbMarshaller;
import com.hp.autonomy.types.idol.IdolJaxbMarshallerImpl;
import com.hp.autonomy.types.idol.QueryResponse;

@SuppressWarnings("WeakerAccess")
public class AciResponseJaxbProcessorFactory {
    protected final IdolJaxbMarshaller marshaller;

    public AciResponseJaxbProcessorFactory() {
        marshaller = new IdolJaxbMarshallerImpl();
    }

    public <T> Processor<T> createAciResponseProcessor(final Class<T> responseDataType) {
        return new AciResponseJaxbProcessor<>(marshaller, responseDataType);
    }

    public <T extends QueryResponse> Processor<T> createQueryAciResponseProcessor(final Class<T> responseDataType, final Class<?> contentType) {
        return new QueryAciResponseJaxbProcessor<>(marshaller, responseDataType, contentType);
    }

    public Processor<Void> createEmptyAciResponseProcessor() {
        return new EmptyAciResponseJaxbProcessor(marshaller);
    }

    public IdolJaxbMarshaller getMarshaller() {
        return marshaller;
    }
}
