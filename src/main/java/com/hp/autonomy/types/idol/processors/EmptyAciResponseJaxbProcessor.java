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
public class EmptyAciResponseJaxbProcessor implements Processor<Void> {
    private static final long serialVersionUID = -1983490659468698548L;

    private final IdolJaxbMarshaller idolXmlMarshaller;

    public EmptyAciResponseJaxbProcessor(final IdolJaxbMarshaller idolXmlMarshaller) {
        this.idolXmlMarshaller = idolXmlMarshaller;
    }

    @Override
    public Void process(final AciResponseInputStream aciResponseInputStream) {
        idolXmlMarshaller.parseIdolResponse(aciResponseInputStream);
        return null;
    }
}
