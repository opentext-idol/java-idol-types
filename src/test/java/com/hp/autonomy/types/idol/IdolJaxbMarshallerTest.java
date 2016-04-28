/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.types.idol;

import com.hp.autonomy.types.idol.content.Blacklist;
import com.hp.autonomy.types.idol.content.DynamicSpotlight;
import com.hp.autonomy.types.idol.content.PinToPosition;
import com.hp.autonomy.types.idol.content.Spotlight;
import com.hp.autonomy.types.idol.content.SynonymGroup;
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
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import static org.custommonkey.xmlunit.XMLAssert.assertXMLEqual;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@SuppressWarnings("OverlyCoupledClass")
@RunWith(Parameterized.class)
public class IdolJaxbMarshallerTest<T, U> {
    private static final String ERROR_FILE_NAME = "/error.xml";

    @SuppressWarnings("OverlyCoupledMethod")
    @Parameterized.Parameters(name = "{2}")
    public static Collection<Object[]> data() {
        return Arrays.asList(
//                new Object[]{DateConvertResponseData.class, ""},
//                new Object[]{DetectLanguageResponseData.class, ""},
//                new Object[]{DocumentStatsResponseData.class, ""},
//                new Object[]{GetAllRefsResponseData.class, ""},
                new Object[]{GetContentResponseData.class, null, "/getContent.xml"},
//                new Object[]{GetQueryTagValuesResponseData.class, ""},
//                new Object[]{GetSampleResponseData.class, ""},
                new Object[]{GetStatusResponseData.class, null, "/getStatus.xml"},
                new Object[]{GetTagNamesResponseData.class, null, "/getTagNames.xml"},
//                new Object[]{GetTagValuesResponseData.class, ""},
                new Object[]{GetVersionResponseData.class, null, "/getVersion.xml"},
//                new Object[]{HighlightResponseData.class, ""},
                new Object[]{LanguageSettingsResponseData.class, null, "/languageSettings.xml"},
//                new Object[]{ListResponseData.class, ""},
                new Object[]{QueryResponseData.class, Blacklist.class, "/blacklists.xml"},
                new Object[]{QueryResponseData.class, DynamicSpotlight.class, "/dynamicSpotlights.xml"},
                new Object[]{QueryResponseData.class, PinToPosition.class, "/pinToPosition.xml"},
                new Object[]{QueryResponseData.class, null, "/query.xml"},
                new Object[]{QueryResponseData.class, null, "/queryForPromotions.xml"},
                new Object[]{QueryResponseData.class, null, "/querySummary.xml"},
                new Object[]{QueryResponseData.class, Spotlight.class, "/spotlights.xml"},
                new Object[]{QueryResponseData.class, SynonymGroup.class, "/synonyms.xml"},
//                new Object[]{QuerySummaryManagementResponseData.class, ""},
                new Object[]{Users.class, null, "/roleGetUserList.xml"},
                new Object[]{RolesResponseData.class, null, "/roleUserGetRoleList.xml"},
//                new Object[]{SuggestOnTextResponseData.class, ""},
//                new Object[]{SuggestResponseData.class, ""},
//                new Object[]{SummarizeResponseData.class, ""},
                new Object[]{Security.class, null, "/security.xml"},
//                new Object[]{TermExpandResponseData.class, ""},
//                new Object[]{TermGetAllResponseData.class, ""},
//                new Object[]{TermGetBestResponseData.class, ""},
//                new Object[]{TermGetInfoResponseData.class, ""},
                new Object[]{TypeAheadResponseData.class, null, "/typeAhead.xml"},
//                new Object[]{TermGetInfoResponseData.class, ""},
                new Object[]{Uid.class, null, "/userAdd.xml"},
                new Object[]{null, null, "/userDelete.xml"},
                new Object[]{User.class, null, "/userRead.xml"},
                new Object[]{UserDetails.class, null, "/userReadUserListDetails.xml"});
    }


    private final Class<T> type;
    private final Class<U> subType;
    private final String xml;

    private IdolJaxbMarshaller<CustomParsingException, CustomProcessingException> idolJaxbMarshaller;

    public IdolJaxbMarshallerTest(final Class<T> type, final Class<U> subType, final String fileName) throws IOException {
        this.type = type;
        this.subType = subType;
        xml = IOUtils.toString(IdolJaxbMarshallerTest.class.getResource(fileName));
    }

    @Before
    public void setUp() {
        idolJaxbMarshaller = new IdolJaxbMarshallerImpl<>(new IdolJaxbMarshaller.Function<Error, CustomParsingException>() {
            @Override
            public CustomParsingException apply(final Error error) {
                return new CustomParsingException(error);
            }
        }, new IdolJaxbMarshaller.BiFunction<String, Exception, CustomProcessingException>() {
            @Override
            public CustomProcessingException apply(final String message, final Exception cause) {
                return new CustomProcessingException(message, cause);
            }
        });
    }

    @Test
    public void parseResponse() throws JAXBException, IOException, SAXException, CustomParsingException, CustomProcessingException {
        final InputStream inputStream = new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8));
        final Autnresponse response = type != null ? idolJaxbMarshaller.parseIdolResponse(inputStream, type) : idolJaxbMarshaller.parseIdolResponse(inputStream);

        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        final Class<?>[] classesToBeBound = type == null ? new Class<?>[]{Autnresponse.class} : new Class<?>[]{Autnresponse.class, type};
        JAXBContext.newInstance(classesToBeBound).createMarshaller().marshal(response, outputStream);
        final String generatedXml = new String(outputStream.toByteArray(), StandardCharsets.UTF_8);

        XMLUnit.setIgnoreWhitespace(true);
        XMLUnit.setIgnoreComments(true);
        XMLUnit.setIgnoreAttributeOrder(true);
        final Diff diff = new Diff(xml, generatedXml);
        diff.overrideDifferenceListener(new XMLDifferenceListener());
        assertXMLEqual(diff, true);
    }

    @Test
    public void parseIdolQueryResponse() throws CustomParsingException, CustomProcessingException, IOException {
        if (subType != null) {
            final InputStream inputStream = new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8));
            @SuppressWarnings("unchecked")
            final Autnresponse response = idolJaxbMarshaller.parseIdolQueryResponse(inputStream, (Class<QueryResponse>) type, subType);
            for (final Hit hit : ((QueryResponse) response.getResponsedata()).getHits()) {
                for (final Object o : hit.getContent().getContent()) {
                    assertTrue(subType.isAssignableFrom(o.getClass()));
                    assertNotNull(idolJaxbMarshaller.generateXmlDocument(Collections.singleton(subType.cast(o)), subType, StandardCharsets.UTF_8));
                }
            }
        }
    }

    @Test
    public void parseResponseData() throws CustomParsingException, CustomProcessingException, IOException {
        if (type != null) {
            try (final InputStream inputStream = new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8))) {
                assertNotNull(idolJaxbMarshaller.parseIdolResponseData(inputStream, type));
            }
        }
    }

    @Test
    public void parseQueryResponseData() throws CustomParsingException, CustomProcessingException, IOException {
        if (subType != null) {
            try (final InputStream inputStream = new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8))) {
                //noinspection unchecked
                assertNotNull(idolJaxbMarshaller.parseIdolQueryResponseData(inputStream, (Class<QueryResponse>) type, subType));
            }
        }
    }

    @Test(expected = CustomParsingException.class)
    public void parseErrorResponse() throws CustomParsingException, CustomProcessingException {
        idolJaxbMarshaller.parseIdolResponse(IdolJaxbMarshallerTest.class.getResourceAsStream(ERROR_FILE_NAME), type);
    }

    @Test(expected = CustomProcessingException.class)
    public void processingError() throws CustomParsingException, CustomProcessingException {
        idolJaxbMarshaller.parseIdolResponse(new ByteArrayInputStream("bad".getBytes()), type);
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

        CustomParsingException(final Error error) {
            super(error.getErrorstring());
        }
    }

    private static class CustomProcessingException extends Exception {
        private static final long serialVersionUID = -5167479141615219983L;

        CustomProcessingException(final String message, final Throwable cause) {
            super(message, cause);
        }
    }
}
