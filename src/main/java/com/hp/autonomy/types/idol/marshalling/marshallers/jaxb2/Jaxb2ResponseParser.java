/*
 * Copyright 2016 Hewlett Packard Enterprise Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.types.idol.marshalling.marshallers.jaxb2;

import com.hp.autonomy.types.idol.marshalling.marshallers.AciErrorExceptionBuilder;
import com.hp.autonomy.types.idol.marshalling.marshallers.ResponseParser;
import com.hp.autonomy.types.idol.responses.Autnresponse;
import com.hp.autonomy.types.idol.responses.ErrorResponse;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.w3c.dom.Node;

import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;
import java.io.InputStream;

class Jaxb2ResponseParser implements ResponseParser {
    private static final String SUCCESS_STATE = "SUCCESS";

    private final Jaxb2Marshaller responseEnvelopeMarshaller;
    private final Jaxb2Marshaller errorMarshaller;

    Jaxb2ResponseParser() {
        responseEnvelopeMarshaller = new Jaxb2Marshaller();
        responseEnvelopeMarshaller.setClassesToBeBound(Autnresponse.class);

        errorMarshaller = new Jaxb2Marshaller();
        errorMarshaller.setClassesToBeBound(ErrorResponse.class);
    }

    @SuppressWarnings("CastToConcreteClass")
    @Override
    public Autnresponse parseResponse(final InputStream inputStream) {
        final Autnresponse autnresponse = (Autnresponse) responseEnvelopeMarshaller.unmarshal(new StreamSource(inputStream));

        if (!SUCCESS_STATE.equals(autnresponse.getResponse())) {
            final Node responseData = (Node) autnresponse.getResponseData();
            final ErrorResponse errorResponse = (ErrorResponse) errorMarshaller.unmarshal(new DOMSource(responseData));
            throw new AciErrorExceptionBuilder(errorResponse.getError()).build();
        }

        return autnresponse;
    }
}
