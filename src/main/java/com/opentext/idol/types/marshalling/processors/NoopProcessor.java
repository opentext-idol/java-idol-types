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
import org.apache.hc.core5.http.HttpStatus;

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
