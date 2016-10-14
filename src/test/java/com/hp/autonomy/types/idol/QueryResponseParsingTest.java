/*
 * Copyright 2016 Hewlett Packard Enterprise Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.types.idol;

import com.autonomy.aci.client.services.Processor;
import com.autonomy.aci.client.transport.AciResponseInputStream;
import com.hp.autonomy.types.idol.content.Blacklist;
import com.hp.autonomy.types.idol.content.DynamicSpotlight;
import com.hp.autonomy.types.idol.content.PinToPosition;
import com.hp.autonomy.types.idol.content.Spotlight;
import com.hp.autonomy.types.idol.content.SynonymGroup;
import com.hp.autonomy.types.idol.marshalling.ProcessorFactory;
import com.hp.autonomy.types.idol.marshalling.marshallers.MarshallerFactory;
import com.hp.autonomy.types.idol.marshalling.marshallers.ResponseParser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.xml.sax.SAXException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class QueryResponseParsingTest<R extends QueryResponse, C> extends AbstractParsingTest<R> {
    @SuppressWarnings("OverlyCoupledMethod")
    @Parameterized.Parameters(name = "{2}")
    public static Collection<Object[]> data() {
        return Arrays.asList(
                new Object[]{QueryResponseData.class, Blacklist.class, "/blacklists.xml"},
                new Object[]{QueryResponseData.class, DynamicSpotlight.class, "/dynamicSpotlights.xml"},
                new Object[]{QueryResponseData.class, PinToPosition.class, "/pinToPosition.xml"},
                new Object[]{QueryResponseData.class, Spotlight.class, "/spotlights.xml"},
                new Object[]{QueryResponseData.class, SynonymGroup.class, "/synonyms.xml"});
    }

    private final Class<C> contentType;

    public QueryResponseParsingTest(final Class<R> responseDataType, final Class<C> contentType, final String fileName) throws IOException {
        super(responseDataType, fileName);
        this.contentType = contentType;
    }

    @Override
    public void symmetry() throws IOException, SAXException {
        // Cannot guarantee order of document content
    }

    @Test
    public void contentType() throws IOException {
        final Processor<R> processor = getProcessor(processorFactory, type);
        try (final AciResponseInputStream mockInputStream = new MockAciResponseInputStream(new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8)))) {
            final R javaObject = processor.process(mockInputStream);
            javaObject.getHits().stream().forEach(hit -> hit.getContent().getContent().stream().forEach(o -> assertTrue(contentType.isAssignableFrom(o.getClass()))));
        }
    }

    @Override
    protected Processor<R> getProcessor(final ProcessorFactory processorFactory, final Class<R> type) {
        return processorFactory.getQueryResponseDataProcessor(type, contentType);
    }

    @Override
    protected ResponseParser getResponseParser(final MarshallerFactory marshallerFactory, final Class<R> type) {
        return marshallerFactory.getQueryResponseDataParser(marshallerFactory.getResponseDataParser(type), type, contentType);
    }
}
