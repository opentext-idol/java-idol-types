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

package com.opentext.idol.types.marshalling.marshallers.jaxb2;

import com.opentext.idol.types.marshalling.marshallers.AciErrorExceptionBuilder;
import com.opentext.idol.types.marshalling.marshallers.ResponseParser;
import com.opentext.idol.types.responses.Autnresponse;
import com.opentext.idol.types.responses.ErrorResponse;
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
