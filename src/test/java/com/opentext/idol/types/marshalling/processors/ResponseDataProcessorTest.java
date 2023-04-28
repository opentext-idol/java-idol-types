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

import com.autonomy.aci.client.transport.AciResponseInputStream;
import com.opentext.idol.types.marshalling.marshallers.ResponseDataParser;
import com.opentext.idol.types.responses.GetStatusResponseData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.InputStream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ResponseDataProcessorTest {
    @Mock
    private ResponseDataParser<GetStatusResponseData> responseDataParser;

    @Mock
    private AciResponseInputStream inputStream;

    private ResponseDataProcessor<GetStatusResponseData> aciResponseProcessor;

    @BeforeEach
    public void setUp() {
        aciResponseProcessor = new ResponseDataProcessor<>(responseDataParser);
    }

    @Test
    public void process() {
        aciResponseProcessor.process(inputStream);
        verify(responseDataParser).parseResponseData(any(InputStream.class));
    }
}
