/*
 * Copyright 2016 Hewlett Packard Enterprise Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.types.idol.marshalling;

import com.hp.autonomy.types.idol.content.Blacklist;
import com.hp.autonomy.types.idol.marshalling.marshallers.DocumentGenerator;
import com.hp.autonomy.types.idol.marshalling.marshallers.MarshallerFactory;
import com.hp.autonomy.types.idol.marshalling.marshallers.ResponseDataParser;
import com.hp.autonomy.types.idol.marshalling.marshallers.ResponseParser;
import com.hp.autonomy.types.idol.responses.GetStatusResponseData;
import com.hp.autonomy.types.idol.responses.QueryResponse;
import com.hp.autonomy.types.idol.responses.QueryResponseData;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
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

    @Before
    public void setUp() {
        processorFactory = new ProcessorFactoryImpl(marshallerFactory);

        when(marshallerFactory.getResponseParser()).thenReturn(responseParser);
        when(marshallerFactory.getResponseDataParser(any())).thenReturn(responseDataParser);
        when(marshallerFactory.getQueryResponseDataParser(any(), any(), any())).thenReturn(queryResponseDataParser);
        when(marshallerFactory.getDocumentGenerator(any())).thenReturn(documentGenerator);
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
