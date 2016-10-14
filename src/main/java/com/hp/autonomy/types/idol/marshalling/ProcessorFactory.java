/*
 * Copyright 2016 Hewlett Packard Enterprise Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.types.idol.marshalling;

import com.autonomy.aci.client.services.Processor;
import com.hp.autonomy.types.idol.QueryResponse;
import com.hp.autonomy.types.idol.marshalling.marshallers.DocumentGenerator;

import java.util.function.Function;

public interface ProcessorFactory {
    Processor<Void> getVoidProcessor();

    <T> Processor<T> getResponseDataProcessor(Class<T> type);

    <T, U> Processor<U>  getResponseDataWrapperProcessor(Class<T> responseDataType, Class<U> type, Function<T, U> function);

    <R extends QueryResponse, C> Processor<R> getQueryResponseDataProcessor(Class<R> responseDataType, Class<C> contentType);

    <T> DocumentGenerator<T> getDocumentGenerator(Class<T> type);
}
