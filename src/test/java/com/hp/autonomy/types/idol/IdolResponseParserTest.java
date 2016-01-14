/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.types.idol;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.custommonkey.xmlunit.Diff;
import org.custommonkey.xmlunit.Difference;
import org.custommonkey.xmlunit.DifferenceConstants;
import org.custommonkey.xmlunit.DifferenceListener;
import org.custommonkey.xmlunit.XMLUnit;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collection;

import static org.custommonkey.xmlunit.XMLAssert.assertXMLEqual;
import static org.junit.Assert.assertNotNull;

@SuppressWarnings("OverlyCoupledClass")
@RunWith(Parameterized.class)
public class IdolResponseParserTest<T> {
    private static final String ERROR_FILE_NAME = "/error.xml";

    @SuppressWarnings("OverlyCoupledMethod")
    @Parameterized.Parameters(name = "{1}")
    public static Collection<Object[]> data(){
        return Arrays.asList(
//                new Object[]{DateConvertResponseData.class, ""},
//                new Object[]{DetectLanguageResponseData.class, ""},
//                new Object[]{DocumentStatsResponseData.class, ""},
//                new Object[]{GetAllRefsResponseData.class, ""},
                new Object[]{GetContentResponseData.class, "/getContent.xml"},
//                new Object[]{GetQueryTagValuesResponseData.class, ""},
//                new Object[]{GetSampleResponseData.class, ""},
                new Object[]{GetStatusResponseData.class, "/getStatus.xml"},
                new Object[]{GetTagNamesResponseData.class, "/getTagNames.xml"},
//                new Object[]{GetTagValuesResponseData.class, ""},
                new Object[]{GetVersionResponseData.class, "/getVersion.xml"},
//                new Object[]{HighlightResponseData.class, ""},
                new Object[]{LanguageSettingsResponseData.class, "/languageSettings.xml"},
//                new Object[]{ListResponseData.class, ""},
                new Object[]{QueryResponseData.class, "/query.xml"},
                new Object[]{QueryResponseData.class, "/queryForPromotions.xml"},
                new Object[]{QueryResponseData.class, "/querySummary.xml"},
//                new Object[]{QuerySummaryManagementResponseData.class, ""},
//                new Object[]{SuggestOnTextResponseData.class, ""},
//                new Object[]{SuggestResponseData.class, ""},
//                new Object[]{SummarizeResponseData.class, ""},
//                new Object[]{TermExpandResponseData.class, ""},
//                new Object[]{TermGetAllResponseData.class, ""},
//                new Object[]{TermGetBestResponseData.class, ""},
//                new Object[]{TermGetInfoResponseData.class, ""},
                new Object[]{TypeAheadResponseData.class, "/typeAhead.xml"}
        );
    }


    private final Class<T> type;
    private final String xml;
    private final String errorXml;

    private IdolResponseParser<CustomParsingException, CustomProcessingException> idolResponseParser;

    public IdolResponseParserTest(final Class<T> type, final String fileName) throws IOException {
        this.type = type;
        xml = IOUtils.toString(IdolResponseParserTest.class.getResource(fileName));
        errorXml = IOUtils.toString(IdolResponseParserTest.class.getResource(ERROR_FILE_NAME));
    }

    @Before
    public void setUp() {
        idolResponseParser = new IdolResponseParser<>(new IdolResponseParser.Function<Error, CustomParsingException>() {
            @Override
            public CustomParsingException apply(final Error error) {
                return new CustomParsingException(error);
            }
        }, new IdolResponseParser.BiFunction<String, Exception, CustomProcessingException>() {
            @Override
            public CustomProcessingException apply(final String message, final Exception cause) {
                return new CustomProcessingException(message, cause);
            }
        });
    }

    @Test
    public void parseResponse() throws JAXBException, IOException, SAXException, CustomParsingException, CustomProcessingException {
        final Autnresponse response = idolResponseParser.parseIdolResponse(xml, type);

        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        JAXBContext.newInstance(Autnresponse.class, type).createMarshaller().marshal(response, outputStream);
        final String generatedXml = new String(outputStream.toByteArray(), StandardCharsets.UTF_8);

        XMLUnit.setIgnoreWhitespace(true);
        XMLUnit.setIgnoreComments(true);
        XMLUnit.setIgnoreAttributeOrder(true);
        final Diff diff = new Diff(xml, generatedXml);
        diff.overrideDifferenceListener(new XMLDifferenceListener());
        assertXMLEqual(diff, true);
    }

    @Test
    public void parseResponseData() throws CustomParsingException, CustomProcessingException {
        assertNotNull(idolResponseParser.parseIdolResponseData(xml, type));
    }

    @Test(expected = CustomParsingException.class)
    public void parseErrorResponse() throws CustomParsingException, CustomProcessingException {
        idolResponseParser.parseIdolResponse(errorXml, type);
    }

    @Test(expected = CustomProcessingException.class)
    public void processingError() throws CustomParsingException, CustomProcessingException {
        idolResponseParser.parseIdolResponse("bad", type);
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

    private static class CustomParsingException extends Exception {
        private static final long serialVersionUID = -5167479141615219983L;

        public CustomParsingException(final Error error) {
            super(error.getErrorstring());
        }
    }

    private static class CustomProcessingException extends Exception {
        private static final long serialVersionUID = -5167479141615219983L;

        public CustomProcessingException(final String message, final Throwable cause) {
            super(message, cause);
        }
    }
}
