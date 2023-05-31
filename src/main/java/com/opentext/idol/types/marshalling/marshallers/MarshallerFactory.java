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

package com.opentext.idol.types.marshalling.marshallers;

import com.opentext.idol.types.responses.QueryResponse;
import com.opentext.idol.types.responses.QueueInfoGetStatusResponseData;

import java.util.Map;

/**
 * Provides implementations for all marshalling functionality for a particular marshaller type
 * Implementations of this class cannot be relied upon to perform any caching
 */
public interface MarshallerFactory {
    /**
     * Returns an {@link ResponseParser} for handling generic responses
     *
     * @return a {@link ResponseParser} for handling generic responses
     */
    ResponseParser getResponseParser();

    /**
     * Returns a parser for handling responses with a known responseData type
     *
     * @param type the type of the response data
     * @param <T> the type of the response data
     * @return the parser
     */
    <T> ResponseDataParser<T> getResponseDataParser(Class<T> type);

    /**
     * Returns a parser for handling query responses with a known responseData type and a known document content type
     *
     * @param responseDataMarshaller generic parser for the response data type
     * @param responseDataType the type of the response data
     * @param contentType the type of the document content
     * @param <R> the type of the response data
     * @param <C> the type of the document content
     * @return the parser
     */
    <R extends QueryResponse, C> ResponseDataParser<R> getQueryResponseDataParser(
            final ResponseDataParser<R> responseDataMarshaller,
            final Class<R> responseDataType,
            final Class<C> contentType);

    /**
     * Returns a parser for handling QueueInfoGetStatus response data with known result types
     *
     * @param responseDataMarshaller ResponseDataParser for parsing the base response
     * @param resultTypes A map of node name to types representing those nodes
     * @return the parser
     */
    default ResponseDataParser<QueueInfoGetStatusResponseData> getQueueInfoGetStatusResponseDataParser(
            final ResponseDataParser<QueueInfoGetStatusResponseData> responseDataMarshaller,
            final Map<String, Class<?>> resultTypes
    ) {
        throw new UnsupportedOperationException("Method not supported");
    }

    /**
     * Returns a generator of Idol documents
     *
     * @param type the type of document to generate
     * @param <T> the type of document to generate
     * @return the generator
     */
    <T> DocumentGenerator<T> getDocumentGenerator(Class<T> type);
}
