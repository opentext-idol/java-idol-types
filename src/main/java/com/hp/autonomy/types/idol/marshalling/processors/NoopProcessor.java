/*
 * Copyright 2016 Hewlett Packard Enterprise Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.types.idol.marshalling.processors;

import com.autonomy.aci.client.services.Processor;
import com.autonomy.aci.client.transport.AciResponseInputStream;
import org.apache.http.HttpStatus;

/**
 * Processor which only checks the http status and does not examine the body
 */
@SuppressWarnings("WeakerAccess")
public class NoopProcessor implements Processor<Boolean> {
    private static final long serialVersionUID = -3821182089107701210L;

    @Override
    public Boolean process(final AciResponseInputStream aciResponse) {
        return aciResponse.getStatusCode() == HttpStatus.SC_OK;
    }
}
