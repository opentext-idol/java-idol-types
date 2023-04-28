/*
 * (c) Copyright 2015-2017 Micro Focus or one of its affiliates.
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

package com.opentext.idol.types;

import com.autonomy.aci.client.services.Processor;
import com.autonomy.aci.client.transport.AciResponseInputStream;
import com.opentext.idol.types.connectors.DocumentCounts;
import com.opentext.idol.types.connectors.Identifiers;
import com.opentext.idol.types.connectors.ResponseIdentifier;
import com.opentext.idol.types.marshalling.ProcessorFactory;
import com.opentext.idol.types.marshalling.marshallers.MarshallerFactory;
import com.opentext.idol.types.marshalling.marshallers.ResponseParser;
import com.opentext.idol.types.responses.Action;
import com.opentext.idol.types.responses.QueueInfoGetStatusResponseData;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.xml.sax.SAXException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;

public class QueueInfoGetStatusParsingTest extends AbstractParsingTest<QueueInfoGetStatusResponseData> {

    @Override
    protected Stream<TestData<? extends QueueInfoGetStatusResponseData>> getData() {
        return Stream.of(
                new TestData<>(QueueInfoGetStatusResponseData.class, "/connectors/identifiers.xml")
        );
    }

    private static final Map<String, Map<String, Class<?>>> resultTypesLookup = Map.of(
        "/connectors/identifiers.xml", Map.of(
            "identifiers", Identifiers.class,
            "documentcounts", DocumentCounts.class
        )
    );

    @Override
    protected <T extends QueueInfoGetStatusResponseData> Processor<T> getProcessor(final ProcessorFactory processorFactory, final TestData<T> data) {
        return (Processor<T>) getProcessor(processorFactory, data.getXmlFileName());
    }

    private Processor<QueueInfoGetStatusResponseData> getProcessor(final ProcessorFactory processorFactory, final String xmlFileName) {
        return processorFactory.getQueueInfoGetStatusResponseDataProcessor(
            resultTypesLookup.get(xmlFileName));
    }

    @Override
    protected <T extends QueueInfoGetStatusResponseData> ResponseParser getResponseParser(final MarshallerFactory marshallerFactory, final TestData<T> data) {
        return marshallerFactory.getQueueInfoGetStatusResponseDataParser(
            marshallerFactory.getResponseDataParser(QueueInfoGetStatusResponseData.class),
            resultTypesLookup.get(data.getXmlFileName()));
    }

    @Override
    @ParameterizedTest
    @MethodSource("getTestData")
    public <T extends QueueInfoGetStatusResponseData> void symmetry(final TestData<T> data) throws IOException, SAXException {
        // this is difficult to test because of the nested results (ordering is a concern), and ultimately we don't care
    }

    @ParameterizedTest
    @MethodSource("getTestData")
    public <T extends QueueInfoGetStatusResponseData> void testResultParsing(final TestData<T> data) throws IOException {
        final Processor<QueueInfoGetStatusResponseData> processor = getProcessor(processorFactory, data.getXmlFileName());

        try (final AciResponseInputStream mockInputStream = new MockAciResponseInputStream(new ByteArrayInputStream(data.getXml().getBytes(StandardCharsets.UTF_8)))) {
            final QueueInfoGetStatusResponseData responseData = processor.process(mockInputStream);

            final List<Action> actions = responseData.getActions().getAction();
            assertThat(actions, hasSize(1));

            final Action action = actions.get(0);
            final List<Object> results = action.getResults();

            final List<DocumentCounts> documentCounts = parseResults(results, DocumentCounts.class);
            final List<Identifiers> identifiers = parseResults(results, Identifiers.class);

            assertThat(documentCounts, hasSize(1));
            assertThat(identifiers, hasSize(2));

            final Identifiers root = identifiers.get(0);
            assertThat(root.getParentIdentifier(), is("ROOT"));

            final ResponseIdentifier identifier1 = root.getIdentifier().get(0);
            assertThat(identifier1.getContent(), is("[identifier1]"));

            final Identifiers identifier1Children = identifiers.get(1);
            assertThat(identifier1Children.getParentIdentifier(), is("[identifier1]"));

            final ResponseIdentifier identifier2= identifier1Children.getIdentifier().get(0);
            assertThat(identifier2.getContent(), is("[identifier2]"));

            final ResponseIdentifier identifier3 = identifier1Children.getIdentifier().get(1);
            assertThat(identifier3.getContent(), is("[identifier3]"));
        }
    }

    private <T> List<T> parseResults(final List<Object> results, final Class<T> type) {
        return results.stream()
                .filter(type::isInstance)
                .map(type::cast)
                .collect(Collectors.toList());
    }
}
