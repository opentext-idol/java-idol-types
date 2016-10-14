/*
 * Copyright 2016 Hewlett Packard Enterprise Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.types.idol.marshalling.processors;

import com.autonomy.aci.client.services.Processor;
import com.autonomy.aci.client.transport.AciResponseInputStream;
import com.hp.autonomy.types.idol.responses.GetStatusResponseData;
import com.hp.autonomy.types.idol.responses.LanguageTypeSettings;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class WrapperProcessorTest {
    @Mock
    private Processor<GetStatusResponseData> innerProcessor;

    @Mock
    private AciResponseInputStream inputStream;

    private WrapperProcessor<GetStatusResponseData, LanguageTypeSettings> processor;

    @Before
    public void setUp() {
        processor = new WrapperProcessor<>(innerProcessor, GetStatusResponseData::getLanguageTypeSettings);
        when(innerProcessor.process(any())).thenReturn(new GetStatusResponseData());
    }

    @Test
    public void process() {
        processor.process(inputStream);
        verify(innerProcessor).process(any(AciResponseInputStream.class));
    }
}
