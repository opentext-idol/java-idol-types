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
import com.opentext.idol.types.marshalling.Jaxb2ParsingConfiguration;
import com.opentext.idol.types.marshalling.ProcessorFactory;
import com.opentext.idol.types.marshalling.marshallers.MarshallerFactory;
import com.opentext.idol.types.marshalling.marshallers.ResponseParser;
import com.opentext.idol.types.responses.Autnresponse;
import lombok.Data;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.w3c.dom.Attr;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;
import org.xmlunit.assertj3.XmlAssert;
import org.xmlunit.diff.*;

import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

@SuppressWarnings("SpringJavaAutowiredMembersInspection")
@SpringBootTest(classes = Jaxb2ParsingConfiguration.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
@TestInstance(PER_CLASS)
public abstract class AbstractParsingTest<B> {
    @Autowired
    protected MarshallerFactory marshallerFactory;
    @Autowired
    protected ProcessorFactory processorFactory;

    protected abstract Stream<TestData<? extends B>> getData();

    private Stream<? extends Arguments> getTestData() {
        return getData().map(data -> {
            try {
                data.setXml(IOUtils.toString(getClass().getResource(data.getXmlFileName())));
            } catch (final IOException e) {
                throw new RuntimeException(e);
            }
            return Arguments.of(data);
        });
    }

    protected abstract <T extends B> Processor<T> getProcessor(final ProcessorFactory processorFactory, final TestData<T> data);

    protected abstract <T extends B> ResponseParser getResponseParser(final MarshallerFactory marshallerFactory, final TestData<T> data);

    @ParameterizedTest
    @MethodSource("getTestData")
    public <T extends B> void parseResponse(final TestData<T> data) throws IOException {
        final Processor<T> processor = getProcessor(processorFactory, data);
        try (final AciResponseInputStream mockInputStream = new MockAciResponseInputStream(new ByteArrayInputStream(data.getXml().getBytes(StandardCharsets.UTF_8)))) {
            final T javaObject = processor.process(mockInputStream);
            if (Void.class.equals(data.getType())) {
                assertNull(javaObject);
            } else {
                assertNotNull(javaObject);
            }
        }
    }

    @ParameterizedTest
    @MethodSource("getTestData")
    public <T extends B> void symmetry(final TestData<T> data) throws IOException, SAXException {
        final ResponseParser responseParser = getResponseParser(marshallerFactory, data);
        final Autnresponse autnresponse = responseParser.parseResponse(new ByteArrayInputStream(data.getXml().getBytes(StandardCharsets.UTF_8)));

        final Jaxb2Marshaller jaxb2Marshaller = new Jaxb2Marshaller();
        jaxb2Marshaller.setClassesToBeBound(Autnresponse.class, data.getType());

        final String generatedXml;
        try (final ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            jaxb2Marshaller.marshal(autnresponse, new StreamResult(outputStream));
            generatedXml = new String(outputStream.toByteArray(), StandardCharsets.UTF_8);
        }

        // Void equates to an empty responsedata element which we can't regenerate
        if (!Void.class.equals(data.getType())) {
            XmlAssert.assertThat(generatedXml).and(data.getXml())
                .ignoreWhitespace().ignoreComments().ignoreChildNodesOrder()
                .withDifferenceEvaluator(DifferenceEvaluators.chain(
                    DifferenceEvaluators.downgradeDifferencesToEqual(ComparisonType.CHILD_NODELIST_SEQUENCE),
                    new XMLDifferenceEvaluator()
                ))
                .areIdentical();
        }
    }

    private static class XMLDifferenceEvaluator implements DifferenceEvaluator {
        @Override
        public ComparisonResult evaluate(final Comparison comparison, final ComparisonResult outcome) {
            final ComparisonType compType = comparison.getType();
            final Node controlNode = comparison.getControlDetails().getTarget();
            final Node testNode = comparison.getTestDetails().getTarget();
            final Node node = controlNode != null ? controlNode : testNode;
            final String nodeName;
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                nodeName = node.getLocalName();
            } else if (node.getNodeType() == Node.ATTRIBUTE_NODE) {
                nodeName = ((Attr) node).getOwnerElement().getLocalName();
            } else {
                nodeName = null;
            }

            if (outcome == ComparisonResult.DIFFERENT) {
                if (compType == ComparisonType.NAMESPACE_PREFIX) {
                    return ComparisonResult.EQUAL;
                }
                if ((
                    compType == ComparisonType.ELEMENT_NUM_ATTRIBUTES ||
                        compType == ComparisonType.ATTR_NAME_LOOKUP ||
                        compType == ComparisonType.ATTR_VALUE
                ) && "responsedata".equals(nodeName)) {
                    return ComparisonResult.EQUAL;
                }
            }

            return outcome;
        }
    }

    @Data
    protected static class TestData<T> {
        final Class<T> type;
        final String xmlFileName;
        String xml;
    }

}
