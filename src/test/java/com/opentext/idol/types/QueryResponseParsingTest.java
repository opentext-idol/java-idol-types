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

package com.opentext.idol.types;

import com.autonomy.aci.client.services.Processor;
import com.autonomy.aci.client.transport.AciResponseInputStream;
import com.opentext.idol.types.content.*;
import com.opentext.idol.types.marshalling.ProcessorFactory;
import com.opentext.idol.types.marshalling.marshallers.MarshallerFactory;
import com.opentext.idol.types.marshalling.marshallers.ResponseParser;
import com.opentext.idol.types.responses.QueryResponse;
import com.opentext.idol.types.responses.QueryResponseData;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.xml.sax.SAXException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class QueryResponseParsingTest extends AbstractParsingTest<QueryResponse> {

    @Override
    protected Stream<TestData<? extends QueryResponse>> getData() {
        return Stream.of(
                new TestData<>(QueryResponseData.class, "/blacklists.xml"),
                new TestData<>(QueryResponseData.class, "/dynamicSpotlights.xml"),
                new TestData<>(QueryResponseData.class, "/pinToPosition.xml"),
                new TestData<>(QueryResponseData.class, "/spotlights.xml"),
                new TestData<>(QueryResponseData.class, "/synonyms.xml"));
    }

    private static final Map<String, Class<?>> contentTypeLookup = Map.of(
        "/blacklists.xml", Blacklist.class,
        "/dynamicSpotlights.xml", DynamicSpotlight.class,
        "/pinToPosition.xml", PinToPosition.class,
        "/spotlights.xml", Spotlight.class,
        "/synonyms.xml", SynonymGroup.class
    );

    @ParameterizedTest
    @MethodSource("getTestData")
    public <T extends QueryResponse> void contentType(final TestData<T> data) throws IOException {
        final Class<?> contentType = contentTypeLookup.get(data.getXmlFileName());
        final Processor<T> processor = getProcessor(processorFactory, data);
        try (final AciResponseInputStream mockInputStream = new MockAciResponseInputStream(new ByteArrayInputStream(data.getXml().getBytes(StandardCharsets.UTF_8)))) {
            final T javaObject = processor.process(mockInputStream);
            javaObject.getHits().stream().forEach(hit -> hit.getContent().getContent().stream().forEach(o -> assertTrue(contentType.isAssignableFrom(o.getClass()))));
        }
    }

    @Override
    @ParameterizedTest
    @MethodSource("getTestData")
    public <T extends QueryResponse> void symmetry(final TestData<T> data) throws IOException, SAXException {
        // Cannot guarantee order of document content
    }

    @Override
    protected <T extends QueryResponse> Processor<T> getProcessor(final ProcessorFactory processorFactory, final TestData<T> data) {
        return processorFactory.getQueryResponseDataProcessor(data.getType(), contentTypeLookup.get(data.getXmlFileName()));
    }

    @Override
    protected <T extends QueryResponse> ResponseParser getResponseParser(final MarshallerFactory marshallerFactory, final TestData<T> data) {
        return marshallerFactory.getQueryResponseDataParser(
            marshallerFactory.getResponseDataParser(data.getType()),
            data.getType(),
            contentTypeLookup.get(data.getXmlFileName()));
    }
}
