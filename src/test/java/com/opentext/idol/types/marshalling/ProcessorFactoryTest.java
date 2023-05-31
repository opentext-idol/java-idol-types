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

package com.opentext.idol.types.marshalling;

import com.opentext.idol.types.content.Blacklist;
import com.opentext.idol.types.marshalling.marshallers.DocumentGenerator;
import com.opentext.idol.types.marshalling.marshallers.MarshallerFactory;
import com.opentext.idol.types.marshalling.marshallers.ResponseDataParser;
import com.opentext.idol.types.marshalling.marshallers.ResponseParser;
import com.opentext.idol.types.responses.GetStatusResponseData;
import com.opentext.idol.types.responses.QueryResponse;
import com.opentext.idol.types.responses.QueryResponseData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProcessorFactoryTest {
    @Mock
    private MarshallerFactory marshallerFactory;
    @Mock
    private ResponseParser responseParser;
    @Mock
    private ResponseDataParser<Object> responseDataParser;
    @Mock
    private ResponseDataParser<QueryResponse> queryResponseDataParser;
    @Mock
    private DocumentGenerator<Object> documentGenerator;

    private ProcessorFactory processorFactory;

    @BeforeEach
    public void setUp() {
        processorFactory = new ProcessorFactoryImpl(marshallerFactory);

        Mockito.lenient().when(marshallerFactory.getResponseParser()).thenReturn(responseParser);
        Mockito.lenient().when(marshallerFactory.getResponseDataParser(any())).thenReturn(responseDataParser);
        Mockito.lenient().when(marshallerFactory.getQueryResponseDataParser(any(), any(), any())).thenReturn(queryResponseDataParser);
        Mockito.lenient().when(marshallerFactory.getDocumentGenerator(any())).thenReturn(documentGenerator);
    }

    @Test
    public void getVoidProcessor() {
        assertNotNull(processorFactory.getVoidProcessor());
    }

    @Test
    public void getResponseDataProcessor() {
        assertNotNull(processorFactory.getResponseDataProcessor(GetStatusResponseData.class));
    }

    @Test
    public void getResponseDataProcessorCaching() {
        processorFactory.getResponseDataProcessor(GetStatusResponseData.class);
        processorFactory.getResponseDataProcessor(GetStatusResponseData.class);
        verify(marshallerFactory).getResponseDataParser(GetStatusResponseData.class);
    }

    @Test
    public void getResponseDataWrapperProcessor() {
        assertNotNull(processorFactory.getResponseDataWrapperProcessor(GetStatusResponseData.class, GetStatusResponseData::getLanguageTypeSettings));
    }

    @Test
    public void getResponseDataWrapperProcessorCaching() {
        processorFactory.getResponseDataWrapperProcessor(GetStatusResponseData.class, GetStatusResponseData::getLanguageTypeSettings);
        processorFactory.getResponseDataWrapperProcessor(GetStatusResponseData.class, GetStatusResponseData::getLanguageTypeSettings);
        verify(marshallerFactory).getResponseDataParser(GetStatusResponseData.class);
    }

    @Test
    public void getQueryResponseDataProcessor() {
        assertNotNull(processorFactory.getQueryResponseDataProcessor(QueryResponseData.class, Blacklist.class));
    }

    @Test
    public void getQueryResponseDataProcessorCaching() {
        processorFactory.getQueryResponseDataProcessor(QueryResponseData.class, Blacklist.class);
        processorFactory.getQueryResponseDataProcessor(QueryResponseData.class, Blacklist.class);
        verify(marshallerFactory).getQueryResponseDataParser(any(), eq(QueryResponseData.class), eq(Blacklist.class));
    }

    @Test
    public void getDocumentGenerator() {
        assertNotNull(processorFactory.getDocumentGenerator(Blacklist.class));
    }

    @Test
    public void getDocumentGeneratorCaching() {
        processorFactory.getDocumentGenerator(Blacklist.class);
        processorFactory.getDocumentGenerator(Blacklist.class);
        verify(marshallerFactory).getDocumentGenerator(Blacklist.class);
    }
}
