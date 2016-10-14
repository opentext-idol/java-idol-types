/*
 * Copyright 2016 Hewlett Packard Enterprise Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.types.idol.marshalling.processors;

import com.autonomy.aci.client.services.Processor;
import com.autonomy.aci.client.transport.AciResponseInputStream;
import com.hp.autonomy.types.idol.marshalling.marshallers.ResponseParser;

/**
 * Generic processor for handling Idol responses where the response data type is unknown or immaterial.
 */
@SuppressWarnings({"WeakerAccess", "NonSerializableFieldInSerializableClass"})
public class VoidProcessor implements Processor<Void> {
    private static final long serialVersionUID = -1983490659468698548L;

    private final ResponseParser responseParser;

    public VoidProcessor(final ResponseParser responseParser) {
        this.responseParser = responseParser;
    }

    @Override
    public Void process(final AciResponseInputStream aciResponseInputStream) {
        responseParser.parseResponse(aciResponseInputStream);
        return null;
    }
}
