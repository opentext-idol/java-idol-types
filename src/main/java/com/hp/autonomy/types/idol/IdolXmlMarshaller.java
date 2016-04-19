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
public class IdolXmlMarshaller<E1 extends Exception, E2 extends Exception> {
    public static final String ERROR_NODE = "error";

    private final Function<Error, E1> errorResponseHandler;
    private final BiFunction<String, Exception, E2> parsingErrorHandler;

    public IdolXmlMarshaller(final Function<Error, E1> errorResponseHandler, final BiFunction<String, Exception, E2> parsingErrorHandler) {
        this.errorResponseHandler = errorResponseHandler;
        this.parsingErrorHandler = parsingErrorHandler;
    }

    public Autnresponse parseIdolResponse(final String xml) throws E1, E2 {
        return parseIdolResponse(xml, null);
    }

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

    public <T> T parseIdolResponseData(final String xml, final Class<T> type) throws E1, E2 {
        return type.cast(parseIdolResponse(xml, type).getResponsedata());
    }

    public <R extends QueryResponse, C> R parseIdolQueryResponseData(final String xml, final Class<R> responseType, final Class<C> contentType) throws E1, E2 {
        return responseType.cast(parseIdolQueryResponse(xml, responseType, contentType).getResponsedata());
    }

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

    /**
     * Represents a function that accepts one argument and produces a result.
     * TODO: delete this once we can use Java8
     *
     * @param <T> the type of the input to the function
     * @param <R> the type of the result of the function
     */
    public interface Function<T, R> {
        /**
         * Applies this function to the given argument.
         *
         * @param t the function argument
         * @return the function result
         */
        R apply(T t);
    }

    /**
     * Represents a function that accepts two arguments and produces a result.
     * TODO: delete this once we can use Java8
     *
     * @param <T> the type of the first argument to the function
     * @param <U> the type of the second argument to the function
     * @param <R> the type of the result of the function
     */
    public interface BiFunction<T, U, R> {

        /**
         * Applies this function to the given arguments.
         *
         * @param t the first function argument
         * @param u the second function argument
         * @return the function result
         */
        R apply(T t, U u);
    }
}
