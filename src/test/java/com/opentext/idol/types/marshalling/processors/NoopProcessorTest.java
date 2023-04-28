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

package com.opentext.idol.types.marshalling.processors;

import com.autonomy.aci.client.services.Processor;
import com.autonomy.aci.client.transport.AciResponseInputStream;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class NoopProcessorTest {
    @Mock
    private AciResponseInputStream inputStream;

    private Processor<Boolean> processor;

    @BeforeEach
    public void setUp() {
        processor = new NoopProcessor();
    }

    @Test
    public void success() {
        when(inputStream.getStatusCode()).thenReturn(HttpStatus.SC_OK);
        assertTrue(processor.process(inputStream));
    }

    @Test
    public void failure() {
        when(inputStream.getStatusCode()).thenReturn(HttpStatus.SC_BAD_REQUEST);
        assertFalse(processor.process(inputStream));
    }
}
