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

import com.autonomy.aci.client.services.AciErrorException;
import com.opentext.idol.types.responses.Autnresponse;
import com.opentext.idol.types.responses.DocContent;
import com.opentext.idol.types.responses.QueryResponse;
import com.opentext.idol.types.marshalling.marshallers.ResponseDataParser;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.w3c.dom.Node;

import javax.xml.transform.dom.DOMSource;
import java.io.InputStream;
import java.util.List;

class Jaxb2QueryResponseDataParser<R extends QueryResponse, C> implements ResponseDataParser<R> {
    private final ResponseDataParser<R> responseDataMarshaller;
    private final Class<R> responseDataType;
    private final Class<C> contentType;
    private final Jaxb2Marshaller marshaller;

    Jaxb2QueryResponseDataParser(final ResponseDataParser<R> responseDataMarshaller, final Class<R> responseDataType, final Class<C> contentType) {
        this.responseDataMarshaller = responseDataMarshaller;
        this.responseDataType = responseDataType;
        this.contentType = contentType;
        marshaller = new Jaxb2Marshaller();
        marshaller.setClassesToBeBound(contentType);
        marshaller.setMappedClass(contentType);
    }

    @Override
    public Autnresponse parseResponse(final InputStream inputStream) throws AciErrorException {
        final Autnresponse autnresponse = responseDataMarshaller.parseResponse(inputStream);
        final R responseData = responseDataType.cast(autnresponse.getResponseData());
        responseData.getHits().forEach(hit -> {
            final DocContent contentWrapper = hit.getContent();
            if (contentWrapper != null && !contentWrapper.getContent().isEmpty()) {
                final List<Object> contentNodes = contentWrapper.getContent();
                final Node contentNode = (Node) contentNodes.get(0);
                final C contentObject = contentType.cast(marshaller.unmarshal(new DOMSource(contentNode)));
                contentNodes.clear();
                contentNodes.add(contentObject);
            }
        });

        return autnresponse;
    }

    @Override
    public R parseResponseData(final InputStream inputStream) throws AciErrorException {
        return responseDataType.cast(parseResponse(inputStream).getResponseData());
    }
}
