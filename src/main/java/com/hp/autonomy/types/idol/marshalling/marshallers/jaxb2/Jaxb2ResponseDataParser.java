/*
 * Copyright 2016 Hewlett Packard Enterprise Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.types.idol.marshalling.marshallers.jaxb2;

import com.autonomy.aci.client.services.AciErrorException;
import com.hp.autonomy.types.idol.marshalling.marshallers.ResponseDataParser;
import com.hp.autonomy.types.idol.marshalling.marshallers.ResponseParser;
import com.hp.autonomy.types.idol.responses.Autnresponse;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.w3c.dom.Node;

import javax.xml.transform.dom.DOMSource;
import java.io.InputStream;

class Jaxb2ResponseDataParser<R> implements ResponseDataParser<R> {
    private final ResponseParser responseParser;
    private final Class<R> type;
    private final Jaxb2Marshaller marshaller;

    Jaxb2ResponseDataParser(final ResponseParser responseParser, final Class<R> type) {
        this.responseParser = responseParser;
        this.type = type;
        marshaller = new Jaxb2Marshaller();
        marshaller.setClassesToBeBound(type);
        marshaller.setMappedClass(type);
    }

    @Override
    public Autnresponse parseResponse(final InputStream inputStream) throws AciErrorException {
        final Autnresponse autnresponse = responseParser.parseResponse(inputStream);
        final Node responseData = (Node) autnresponse.getResponseData();
        autnresponse.setResponseData(marshaller.unmarshal(new DOMSource(responseData)));
        return autnresponse;
    }

    @Override
    public R parseResponseData(final InputStream inputStream) {
        return type.cast(parseResponse(inputStream).getResponseData());
    }
}
