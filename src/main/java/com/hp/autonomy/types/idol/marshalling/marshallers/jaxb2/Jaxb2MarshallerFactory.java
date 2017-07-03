/*
 * Copyright 2016 Hewlett Packard Enterprise Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.types.idol.marshalling.marshallers.jaxb2;

import com.hp.autonomy.types.idol.responses.QueryResponse;
import com.hp.autonomy.types.idol.marshalling.marshallers.DocumentGenerator;
import com.hp.autonomy.types.idol.marshalling.marshallers.MarshallerFactory;
import com.hp.autonomy.types.idol.marshalling.marshallers.ResponseDataParser;
import com.hp.autonomy.types.idol.marshalling.marshallers.ResponseParser;
import com.hp.autonomy.types.idol.responses.QueueInfoGetStatusResponseData;
import lombok.Getter;

import java.util.Map;

public class Jaxb2MarshallerFactory implements MarshallerFactory {
    @Getter
    private final ResponseParser responseParser = new Jaxb2ResponseParser();

    @Override
    public <R> ResponseDataParser<R> getResponseDataParser(final Class<R> type) {
        return new Jaxb2ResponseDataParser<>(responseParser, type);
    }

    @Override
    public <R extends QueryResponse, C> ResponseDataParser<R> getQueryResponseDataParser(
            final ResponseDataParser<R> responseDataMarshaller,
            final Class<R> responseDataType,
            final Class<C> contentType) {
        return new Jaxb2QueryResponseDataParser<>(responseDataMarshaller, responseDataType, contentType);
    }

    @Override
    public ResponseDataParser<QueueInfoGetStatusResponseData> getQueueInfoGetStatusResponseDataParser(
            final ResponseDataParser<QueueInfoGetStatusResponseData> responseDataMarshaller,
            final Map<String, Class<?>> resultTypes
    ) {
        return new QueueInfoResponseParser(responseDataMarshaller, resultTypes);
    }

    @Override
    public <T> DocumentGenerator<T> getDocumentGenerator(final Class<T> type) {
        return new Jaxb2DocumentGenerator<>(type);
    }
}
