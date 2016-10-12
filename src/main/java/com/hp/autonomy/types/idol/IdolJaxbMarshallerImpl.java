/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.types.idol;

import com.hp.autonomy.types.idol.content.Documents;
import org.w3c.dom.Node;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Simple IDOL response parser using native Java JAXB tools
 */
@SuppressWarnings("WeakerAccess")
public class IdolJaxbMarshallerImpl<E1 extends Exception, E2 extends Exception> implements IdolJaxbMarshaller<E1, E2> {
    public static final String ERROR_NODE = "error";

    private final Map<Class<?>, JAXBContext> contextMap = new ConcurrentHashMap<>();
    private final Map<Class<?>, JAXBContext> marshallingContextMap = new ConcurrentHashMap<>();

    private final Function<Error, E1> errorResponseHandler;
    private final BiFunction<String, Exception, E2> parsingErrorHandler;

    public IdolJaxbMarshallerImpl(final Function<Error, E1> errorResponseHandler, final BiFunction<String, Exception, E2> parsingErrorHandler) {
        this.errorResponseHandler = errorResponseHandler;
        this.parsingErrorHandler = parsingErrorHandler;
    }

    @Override
    public Autnresponse parseIdolResponse(final InputStream inputStream) throws E1, E2 {
        return parseIdolResponse(inputStream, null);
    }

    @Override
    @SuppressWarnings("CastToConcreteClass")
    public <T> Autnresponse parseIdolResponse(final InputStream inputStream, final Class<T> type) throws E1, E2 {
        try {
            final JAXBContext jaxbContext = getContext(Autnresponse.class);
            final Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

            final Autnresponse response = (Autnresponse) unmarshaller.unmarshal(inputStream);
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
    public <R extends QueryResponse, C> Autnresponse parseIdolQueryResponse(final InputStream inputStream, final Class<R> responseType, final Class<C> contentType) throws E1, E2 {
        final Autnresponse autnresponse;
        try {
            autnresponse = parseIdolResponse(inputStream, responseType);
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
    public <T> T parseIdolResponseData(final InputStream inputStream, final Class<T> type) throws E1, E2 {
        return type.cast(parseIdolResponse(inputStream, type).getResponsedata());
    }

    @Override
    public <R extends QueryResponse, C> R parseIdolQueryResponseData(final InputStream inputStream, final Class<R> responseType, final Class<C> contentType) throws E1, E2 {
        return responseType.cast(parseIdolQueryResponse(inputStream, responseType, contentType).getResponsedata());
    }

    @Override
    public <T> String generateXmlDocument(final Iterable<T> objects, final Class<T> type, final Charset charset) throws E2 {
        final Documents documents = new Documents();
        final List<Object> documentList = documents.getContent();
        for (final T object : objects) {
            documentList.add(object);
        }

        try {
            final JAXBContext context = getMarshallingContext(type);
            final Marshaller marshaller = context.createMarshaller();
            final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            marshaller.marshal(documents, byteArrayOutputStream);
            return new String(byteArrayOutputStream.toByteArray(), charset) + "\n#DREENDDATANOOP\n\n"; // because Idol is rubbish
        } catch (final JAXBException e) {
            throw parsingErrorHandler.apply("Error generating Idol document", e);
        }
    }

    private <T> T unmarshalNode(final Node node, final Class<T> type) throws JAXBException {
        final JAXBContext context = getContext(type);
        final Unmarshaller unmarshaller = context.createUnmarshaller();
        return unmarshaller.unmarshal(node, type).getValue();
    }

    private JAXBContext getContext(final Class<?> type) throws JAXBException {
        final JAXBContext jaxbContext;
        if (contextMap.containsKey(type)) {
            jaxbContext = contextMap.get(type);
        } else {
            jaxbContext = JAXBContext.newInstance(type);
            contextMap.put(type, jaxbContext);
        }
        return jaxbContext;
    }

    private JAXBContext getMarshallingContext(final Class<?> type) throws JAXBException {
        final JAXBContext jaxbContext;
        if (marshallingContextMap.containsKey(type)) {
            jaxbContext = marshallingContextMap.get(type);
        } else {
            jaxbContext = JAXBContext.newInstance(new Class<?>[]{Documents.class, type});
            marshallingContextMap.put(type, jaxbContext);
        }
        return jaxbContext;
    }
}
