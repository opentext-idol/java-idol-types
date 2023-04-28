/*
 * (c) Copyright 2016 Micro Focus or one of its affiliates.
 *
 * Licensed under the MIT License (the "License"); you may not use this file
 * except in compliance with the License.
 *
 * The only warranties for products and services of Micro Focus and its affiliates
 * and licensors ("Micro Focus") are as may be set forth in the express warranty
 * statements accompanying such products and services. Nothing herein should be
 * construed as constituting an additional warranty. Micro Focus shall not be
 * liable for technical or editorial errors or omissions contained herein. The
 * information contained herein is subject to change without notice.
 */

package com.opentext.idol.types.marshalling.marshallers.jaxb2;

import com.autonomy.aci.client.services.AciErrorException;
import com.opentext.idol.types.marshalling.marshallers.ResponseDataParser;
import com.opentext.idol.types.marshalling.marshallers.ResponseParser;
import com.opentext.idol.types.responses.Autnresponse;
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
