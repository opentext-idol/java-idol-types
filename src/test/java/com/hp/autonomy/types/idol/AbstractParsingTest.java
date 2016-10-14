/*
 * Copyright 2016 Hewlett Packard Enterprise Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.types.idol;

import com.autonomy.aci.client.services.Processor;
import com.autonomy.aci.client.transport.AciResponseInputStream;
import com.hp.autonomy.types.idol.marshalling.Jaxb2ParsingConfiguration;
import com.hp.autonomy.types.idol.marshalling.ProcessorFactory;
import com.hp.autonomy.types.idol.marshalling.marshallers.MarshallerFactory;
import com.hp.autonomy.types.idol.marshalling.marshallers.ResponseParser;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.custommonkey.xmlunit.Diff;
import org.custommonkey.xmlunit.Difference;
import org.custommonkey.xmlunit.DifferenceConstants;
import org.custommonkey.xmlunit.DifferenceListener;
import org.custommonkey.xmlunit.XMLUnit;
import org.junit.ClassRule;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.test.context.junit4.rules.SpringClassRule;
import org.springframework.test.context.junit4.rules.SpringMethodRule;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.custommonkey.xmlunit.XMLAssert.assertXMLEqual;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@SuppressWarnings("SpringJavaAutowiredMembersInspection")
@SpringBootTest(classes = Jaxb2ParsingConfiguration.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
public abstract class AbstractParsingTest<T> {
    @ClassRule
    public static final SpringClassRule SCR = new SpringClassRule();
    @org.junit.Rule
    public final SpringMethodRule springMethodRule = new SpringMethodRule();

    final Class<T> type;
    final String xml;

    @Autowired
    protected MarshallerFactory marshallerFactory;
    @Autowired
    protected ProcessorFactory processorFactory;

    AbstractParsingTest(final Class<T> type, final String fileName) throws IOException {
        this.type = type;
        xml = IOUtils.toString(getClass().getResource(fileName));
    }

    protected abstract Processor<T> getProcessor(final ProcessorFactory processorFactory, final Class<T> type);

    protected abstract ResponseParser getResponseParser(final MarshallerFactory marshallerFactory, final Class<T> type);

    @Test
    public void parseResponse() throws IOException {
        final Processor<T> processor = getProcessor(processorFactory, type);
        try (final AciResponseInputStream mockInputStream = new MockAciResponseInputStream(new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8)))) {
            final T javaObject = processor.process(mockInputStream);
            if (Void.class.equals(type)) {
                assertNull(javaObject);
            } else {
                assertNotNull(javaObject);
            }
        }
    }

    @Test
    public void symmetry() throws IOException, SAXException {
        final ResponseParser responseParser = getResponseParser(marshallerFactory, type);
        final Autnresponse autnresponse = responseParser.parseResponse(new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8)));

        final Jaxb2Marshaller jaxb2Marshaller = new Jaxb2Marshaller();
        jaxb2Marshaller.setClassesToBeBound(Autnresponse.class, type);

        final String generatedXml;
        try (final ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            jaxb2Marshaller.marshal(autnresponse, new StreamResult(outputStream));
            generatedXml = new String(outputStream.toByteArray(), StandardCharsets.UTF_8);
        }

        // Void equates to an empty responsedata element which we can't regenerate
        if (!Void.class.equals(type)) {
            XMLUnit.setIgnoreWhitespace(true);
            XMLUnit.setIgnoreComments(true);
            XMLUnit.setIgnoreAttributeOrder(true);
            final Diff diff = new Diff(xml, generatedXml);
            diff.overrideDifferenceListener(new XMLDifferenceListener());
            assertXMLEqual(diff, true);
        }
    }

    private static class XMLDifferenceListener implements DifferenceListener {
        @Override
        public int differenceFound(final Difference difference) {
            int returnType = DifferenceListener.RETURN_ACCEPT_DIFFERENCE;

            final int differenceId = difference.getId();
            final Node controlNode = difference.getControlNodeDetail().getNode();
            if (differenceId == DifferenceConstants.NAMESPACE_PREFIX_ID || (differenceId == DifferenceConstants.ELEMENT_NUM_ATTRIBUTES_ID || differenceId == DifferenceConstants.ATTR_NAME_NOT_FOUND_ID || differenceId == DifferenceConstants.ATTR_VALUE_ID) && "responsedata".equals(controlNode.getLocalName())) {
                returnType = DifferenceListener.RETURN_IGNORE_DIFFERENCE_NODES_IDENTICAL;
            }

            return returnType;
        }

        @Override
        public void skippedComparison(final Node node, final Node node1) {
        }
    }
}
