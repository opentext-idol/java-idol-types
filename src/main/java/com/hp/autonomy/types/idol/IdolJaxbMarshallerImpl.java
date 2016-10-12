/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.types.idol;

import com.autonomy.aci.client.services.AciErrorException;
import com.autonomy.aci.client.services.ProcessorException;
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

/**
 * Simple IDOL response parser using native Java JAXB tools
 */
@SuppressWarnings("WeakerAccess")
public class IdolJaxbMarshallerImpl implements IdolJaxbMarshaller {
    public static final String ERROR_NODE = "error";

    private final Map<Class<?>, JAXBContext> contextMap = new ConcurrentHashMap<>();
    private final Map<Class<?>, JAXBContext> marshallingContextMap = new ConcurrentHashMap<>();

    @Override
    public Autnresponse parseIdolResponse(final InputStream inputStream) throws AciErrorException, ProcessorException {
        return parseIdolResponse(inputStream, null);
    }

    @Override
    @SuppressWarnings("CastToConcreteClass")
    public <T> Autnresponse parseIdolResponse(final InputStream inputStream, final Class<T> type) throws AciErrorException, ProcessorException {
        try {
            final JAXBContext jaxbContext = getContext(Autnresponse.class);
            final Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

            final Autnresponse response = (Autnresponse) unmarshaller.unmarshal(inputStream);
            final Node responseData = (Node) response.getResponsedata();

            if (responseData.getFirstChild() != null && ERROR_NODE.equals(responseData.getFirstChild().getNodeName())) {
                final ErrorResponse errorResponse = unmarshalNode(responseData, ErrorResponse.class);
                throw new AciErrorExceptionBuilder(errorResponse.getError()).build();
            }

            if (type != null) {
                final T unmarshalledResponseData = unmarshalNode(responseData, type);
                response.setResponsedata(unmarshalledResponseData);
            }

            return response;
        } catch (final JAXBException | IdolDateParsingException e) {
            throw new ProcessorException("Error parsing Idol response", e);
        }
    }

    @Override
    public <R extends QueryResponse, C> Autnresponse parseIdolQueryResponse(final InputStream inputStream, final Class<R> responseType, final Class<C> contentType) throws AciErrorException, ProcessorException {
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
            throw new ProcessorException("Error parsing Idol query response content", e);
        }

        return autnresponse;
    }

    @Override
    public <T> T parseIdolResponseData(final InputStream inputStream, final Class<T> type) throws AciErrorException, ProcessorException {
        return type.cast(parseIdolResponse(inputStream, type).getResponsedata());
    }

    @Override
    public <R extends QueryResponse, C> R parseIdolQueryResponseData(final InputStream inputStream, final Class<R> responseType, final Class<C> contentType) throws AciErrorException, ProcessorException {
        return responseType.cast(parseIdolQueryResponse(inputStream, responseType, contentType).getResponsedata());
    }

    @Override
    public <T> String generateXmlDocument(final Iterable<T> objects, final Class<T> type, final Charset charset) throws ProcessorException {
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
            throw new ProcessorException("Error generating Idol document", e);
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
