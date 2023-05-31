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
import com.opentext.idol.types.responses.GetStatusResponseData;
import com.opentext.idol.types.responses.LanguageTypeSettings;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class WrapperProcessorTest {
    @Mock
    private Processor<GetStatusResponseData> innerProcessor;

    @Mock
    private AciResponseInputStream inputStream;

    private WrapperProcessor<GetStatusResponseData, LanguageTypeSettings> processor;

    @BeforeEach
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
