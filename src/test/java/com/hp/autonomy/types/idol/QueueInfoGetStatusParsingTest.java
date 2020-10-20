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

package com.hp.autonomy.types.idol;

import com.autonomy.aci.client.services.Processor;
import com.autonomy.aci.client.transport.AciResponseInputStream;
import com.hp.autonomy.types.idol.connectors.DocumentCounts;
import com.hp.autonomy.types.idol.connectors.Identifiers;
import com.hp.autonomy.types.idol.connectors.ResponseIdentifier;
import com.hp.autonomy.types.idol.marshalling.ProcessorFactory;
import com.hp.autonomy.types.idol.marshalling.marshallers.MarshallerFactory;
import com.hp.autonomy.types.idol.marshalling.marshallers.ResponseParser;
import com.hp.autonomy.types.idol.responses.Action;
import com.hp.autonomy.types.idol.responses.QueueInfoGetStatusResponseData;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.xml.sax.SAXException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;

@RunWith(Parameterized.class)
public class QueueInfoGetStatusParsingTest extends AbstractParsingTest<QueueInfoGetStatusResponseData> {

    private final Map<String, Class<?>> resultTypes;

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        final Map<String, Class<?>> expectedResults = new HashMap<>();
        expectedResults.put("identifiers", Identifiers.class);
        expectedResults.put("documentcounts", DocumentCounts.class);

        return Collections.singletonList(
                new Object[]{QueueInfoGetStatusResponseData.class, "/connectors/identifiers.xml", expectedResults}
        );
    }

    public QueueInfoGetStatusParsingTest(
            final Class<QueueInfoGetStatusResponseData> type,
            final String fileName,
            final Map<String, Class<?>> resultTypes
    ) throws IOException {
        super(type, fileName);

        this.resultTypes = resultTypes;
    }

    @Override
    protected Processor<QueueInfoGetStatusResponseData> getProcessor(final ProcessorFactory processorFactory, final Class<QueueInfoGetStatusResponseData> type) {
        return processorFactory.getQueueInfoGetStatusResponseDataProcessor(resultTypes);
    }

    @Override
    protected ResponseParser getResponseParser(final MarshallerFactory marshallerFactory, final Class<QueueInfoGetStatusResponseData> type) {
        return marshallerFactory.getQueueInfoGetStatusResponseDataParser(marshallerFactory.getResponseDataParser(type), resultTypes);
    }

    @Override
    public void symmetry() throws IOException, SAXException {
        // this is difficult to test because of the nested results (ordering is a concern), and ultimately we don't care
    }

    @Test
    public void testResultParsing() throws IOException {
        final Processor<QueueInfoGetStatusResponseData> processor = getProcessor(processorFactory, type);

        try (final AciResponseInputStream mockInputStream = new MockAciResponseInputStream(new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8)))) {
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
