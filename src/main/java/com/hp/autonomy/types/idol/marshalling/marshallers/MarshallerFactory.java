/*
 * Copyright 2016 Hewlett Packard Enterprise Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.types.idol.marshalling.marshallers;

import com.hp.autonomy.types.idol.responses.QueryResponse;

public interface MarshallerFactory {
    ResponseParser getResponseParser();

    <T> ResponseDataParser<T> getResponseDataParser(Class<T> type);

    <R extends QueryResponse, C> ResponseDataParser<R> getQueryResponseDataParser(
            final ResponseDataParser<R> responseDataMarshaller,
            final Class<R> responseDataType,
            final Class<C> contentType);

    <T> DocumentGenerator<T> getDocumentGenerator(Class<T> type);
}
