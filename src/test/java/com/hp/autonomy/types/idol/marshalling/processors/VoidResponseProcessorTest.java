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

package com.hp.autonomy.types.idol.marshalling.processors;

import com.autonomy.aci.client.transport.AciResponseInputStream;
import com.hp.autonomy.types.idol.marshalling.marshallers.ResponseParser;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.InputStream;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class VoidResponseProcessorTest {
    @Mock
    private ResponseParser responseParser;
    @Mock
    private AciResponseInputStream inputStream;

    private VoidProcessor aciResponseProcessor;

    @Before
    public void setUp() {
        aciResponseProcessor = new VoidProcessor(responseParser);
    }

    @Test
    public void process() {
        aciResponseProcessor.process(inputStream);
        verify(responseParser).parseResponse(any(InputStream.class));
    }
}
