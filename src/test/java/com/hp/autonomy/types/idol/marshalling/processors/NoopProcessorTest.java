/*
 * Copyright 2016 Hewlett Packard Enterprise Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.types.idol.marshalling.processors;

import com.autonomy.aci.client.services.Processor;
import com.autonomy.aci.client.transport.AciResponseInputStream;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class NoopProcessorTest {
    @Mock
    private AciResponseInputStream inputStream;

    private Processor<Boolean> processor;

    @Before
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
