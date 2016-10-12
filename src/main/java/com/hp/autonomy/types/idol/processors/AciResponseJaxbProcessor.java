/*
 * Copyright 2016 Hewlett Packard Enterprise Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.types.idol.processors;

import com.autonomy.aci.client.services.Processor;
import com.autonomy.aci.client.transport.AciResponseInputStream;
import com.hp.autonomy.types.idol.IdolJaxbMarshaller;

/**
 * Generic processor for handling Idol responses.
 * Note that this uses DOM processing behind the scenes so should not be used for very large responses.
 */
@SuppressWarnings({"WeakerAccess", "NonSerializableFieldInSerializableClass"})
public class AciResponseJaxbProcessor<T> implements Processor<T> {
    private static final long serialVersionUID = -1983490659468698548L;

    private final IdolJaxbMarshaller idolXmlMarshaller;
    private final Class<T> responseDataType;

    public AciResponseJaxbProcessor(final IdolJaxbMarshaller idolXmlMarshaller, final Class<T> responseDataType) {
        this.idolXmlMarshaller = idolXmlMarshaller;
        this.responseDataType = responseDataType;
    }

    @Override
    public T process(final AciResponseInputStream aciResponseInputStream) {
        return idolXmlMarshaller.parseIdolResponseData(aciResponseInputStream, responseDataType);
    }
}
