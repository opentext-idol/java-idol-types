/*
 * Copyright 2015-2017 Open Text.
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
import com.opentext.idol.types.marshalling.marshallers.ResponseDataParser;
import com.opentext.idol.types.responses.Autnresponse;
import com.opentext.idol.types.responses.QueueInfoGetStatusResponseData;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.w3c.dom.Node;

import javax.xml.transform.dom.DOMSource;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Response parser for QueueInfoGetStatus responses. Will pick out additional parts of the response if required
 */
class QueueInfoResponseParser implements ResponseDataParser<QueueInfoGetStatusResponseData> {
    private final ResponseDataParser<QueueInfoGetStatusResponseData> responseDataMarshaller;
    private final Map<String, Jaxb2Marshaller> expectedResults = new HashMap<>();

    QueueInfoResponseParser(
            final ResponseDataParser<QueueInfoGetStatusResponseData> responseDataMarshaller,
            final Map<String, Class<?>> expectedResults,
            final Map<String, ?> jaxbContextProperties
    ) {
        this.responseDataMarshaller = responseDataMarshaller;

        expectedResults.forEach((nodeName, clazz) -> {
            final Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
            marshaller.setMappedClass(clazz);
            marshaller.setClassesToBeBound(clazz);
            marshaller.setJaxbContextProperties(jaxbContextProperties);

            this.expectedResults.put(nodeName, marshaller);
        });
    }

    @Override
    public Autnresponse parseResponse(final InputStream inputStream) throws AciErrorException {
        final Autnresponse autnresponse = responseDataMarshaller.parseResponse(inputStream);
        final QueueInfoGetStatusResponseData responseData = (QueueInfoGetStatusResponseData) autnresponse.getResponseData();

        responseData.getActions().getAction().forEach(action -> {
            final List<Object> results = action.getResults();

            final List<Object> mappedResults = results.stream()
                    .filter(o -> o instanceof Node)
                    .map(o -> (Node) o)
                    .filter(n -> expectedResults.containsKey(n.getNodeName()))
                    .map(n -> expectedResults.get(n.getNodeName()).unmarshal(new DOMSource(n)))
                    .collect(Collectors.toList());

            results.clear();
            results.addAll(mappedResults);
        });

        return autnresponse;
    }

    @Override
    public QueueInfoGetStatusResponseData parseResponseData(final InputStream inputStream) throws AciErrorException {
        return (QueueInfoGetStatusResponseData) parseResponse(inputStream).getResponseData();
    }
}
