/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.types.idol;

import org.w3c.dom.Node;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.ByteArrayInputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * Simple IDOL response parser using native Java JAXB tools
 */
@SuppressWarnings("WeakerAccess")
public class IdolJaxbMarshallerImpl<E1 extends Exception, E2 extends Exception> implements IdolJaxbMarshaller<E1, E2> {
    public static final String ERROR_NODE = "error";

    private final Function<Error, E1> errorResponseHandler;
    private final BiFunction<String, Exception, E2> parsingErrorHandler;

    public IdolJaxbMarshallerImpl(final Function<Error, E1> errorResponseHandler, final BiFunction<String, Exception, E2> parsingErrorHandler) {
        this.errorResponseHandler = errorResponseHandler;
        this.parsingErrorHandler = parsingErrorHandler;
    }

    @Override
    public Autnresponse parseIdolResponse(final String xml) throws E1, E2 {
        return parseIdolResponse(xml, null);
    }

    @Override
    @SuppressWarnings("CastToConcreteClass")
    public <T> Autnresponse parseIdolResponse(final String xml, final Class<T> type) throws E1, E2 {
        try {
            final JAXBContext jaxbContext = JAXBContext.newInstance(Autnresponse.class);
            final Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

            final Autnresponse response = (Autnresponse) unmarshaller.unmarshal(new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8)));
            final Node responseData = (Node) response.getResponsedata();

            if (responseData.getFirstChild() != null && ERROR_NODE.equals(responseData.getFirstChild().getNodeName())) {
                final ErrorResponse errorResponse = unmarshalNode(responseData, ErrorResponse.class);
                throw errorResponseHandler.apply(errorResponse.getError());
            }

            if (type != null) {
                final T unmarshalledResponseData = unmarshalNode(responseData, type);
                response.setResponsedata(unmarshalledResponseData);
            }

            return response;
        } catch (final JAXBException | IdolDateParsingException e) {
            throw parsingErrorHandler.apply("Error parsing Idol response", e);
        }
    }

    @Override
    public <R extends QueryResponse, C> Autnresponse parseIdolQueryResponse(final String xml, final Class<R> responseType, final Class<C> contentType) throws E1, E2 {
        final Autnresponse autnresponse;
        try {
            autnresponse = parseIdolResponse(xml, responseType);
            final QueryResponse queryResponse = responseType.cast(autnresponse.getResponsedata());
            for (final Hit hit : queryResponse.getHits()) {
                final DocContent contentWrapper = hit.getContent();
                if (contentWrapper != null && !contentWrapper.getContent().isEmpty()) {
                    final List<Object> contentNodes = contentWrapper.getContent();
                    final Node contentNode = (Node) contentNodes.get(0);
                    final C contentObject = unmarshalNode(contentNode, contentType);
                    contentNodes.clear();
                    contentNodes.add(contentObject);
                }
            }
        } catch (final JAXBException e) {
            throw parsingErrorHandler.apply("Error parsing Idol query response content", e);
        }

        return autnresponse;
    }

    @Override
    public <T> T parseIdolResponseData(final String xml, final Class<T> type) throws E1, E2 {
        return type.cast(parseIdolResponse(xml, type).getResponsedata());
    }

    @Override
    public <R extends QueryResponse, C> R parseIdolQueryResponseData(final String xml, final Class<R> responseType, final Class<C> contentType) throws E1, E2 {
        return responseType.cast(parseIdolQueryResponse(xml, responseType, contentType).getResponsedata());
    }

    @Override
    public <T> void generateXmlDocument(final OutputStream outputStream, final T object, final Class<T> type) throws E2 {
        try {
            final JAXBContext context = JAXBContext.newInstance(type);
            final Marshaller marshaller = context.createMarshaller();
            marshaller.marshal(object, outputStream);
        } catch (final JAXBException e) {
            throw parsingErrorHandler.apply("Error generating Idol document", e);
        }
    }

    private <T> T unmarshalNode(final Node node, final Class<T> type) throws JAXBException {
        final JAXBContext context = JAXBContext.newInstance(type);
        final Unmarshaller unmarshaller = context.createUnmarshaller();
        return unmarshaller.unmarshal(node, type).getValue();
    }
}
