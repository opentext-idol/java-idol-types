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

import com.opentext.idol.types.marshalling.marshallers.DocumentGenerator;
import com.opentext.idol.types.marshalling.marshallers.MarshallerFactory;
import com.opentext.idol.types.marshalling.marshallers.ResponseDataParser;
import com.opentext.idol.types.marshalling.marshallers.ResponseParser;
import com.opentext.idol.types.responses.QueryResponse;
import com.opentext.idol.types.responses.QueueInfoGetStatusResponseData;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
public class Jaxb2MarshallerFactory implements MarshallerFactory {
    private Map<String, ?> jaxbContextProperties = Map.of();

    @Getter
    private final ResponseParser responseParser = new Jaxb2ResponseParser(jaxbContextProperties);

    @Override
    public <R> ResponseDataParser<R> getResponseDataParser(final Class<R> type) {
        return new Jaxb2ResponseDataParser<>(responseParser, type, jaxbContextProperties);
    }

    @Override
    public <R extends QueryResponse, C> ResponseDataParser<R> getQueryResponseDataParser(
            final ResponseDataParser<R> responseDataMarshaller,
            final Class<R> responseDataType,
            final Class<C> contentType) {
        return new Jaxb2QueryResponseDataParser<>(
                responseDataMarshaller, responseDataType, contentType, jaxbContextProperties);
    }

    @Override
    public ResponseDataParser<QueueInfoGetStatusResponseData> getQueueInfoGetStatusResponseDataParser(
            final ResponseDataParser<QueueInfoGetStatusResponseData> responseDataMarshaller,
            final Map<String, Class<?>> resultTypes
    ) {
        return new QueueInfoResponseParser(responseDataMarshaller, resultTypes, jaxbContextProperties);
    }

    @Override
    public <T> DocumentGenerator<T> getDocumentGenerator(final Class<T> type) {
        return new Jaxb2DocumentGenerator<>(type, jaxbContextProperties);
    }
}
