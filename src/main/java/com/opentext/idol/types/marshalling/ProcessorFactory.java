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

package com.opentext.idol.types.marshalling;

import com.autonomy.aci.client.services.Processor;
import com.opentext.idol.types.responses.QueryResponse;
import com.opentext.idol.types.marshalling.marshallers.DocumentGenerator;
import com.opentext.idol.types.responses.QueueInfoGetStatusResponseData;

import java.util.Map;
import java.util.function.Function;

/**
 * A factory which generates processors for parsing IDOL xml responses.
 * This factory is responsible for any necessary caching of underlying marshalling classes which are expensive to recreate.
 */
public interface ProcessorFactory {
    /**
     * Generates/retrieves a processor for unknown/immaterial IDOL responses where we just want to verify that there is no error
     *
     * @return a processor
     */
    Processor<Void> getVoidProcessor();

    /**
     * Generates/retrieves a processor for a known type of IDOL response, returning only the response data
     *
     * @param type the type of the Java object representing the response data
     * @param <T> the type of the Java object representing the response data
     * @return a processor
     */
    <T> Processor<T> getResponseDataProcessor(Class<T> type);

    /**
     * Generates/retrieves a processor for a known type of IDOL query response, returning only the transformed response data
     *
     * @param responseDataType the type of the Java object representing the response data
     * @param function the transformation function
     * @param <T> the response data type
     * @param <U> the transformation function output type
     * @return a processor
     */
    <T, U> Processor<U>  getResponseDataWrapperProcessor(Class<T> responseDataType, Function<T, U> function);

    /**
     * Generates/retrieves a processor for a known type of IDOL query response, with known content type, returning only the response data
     * Any 'hits' should contain nicely parsed document content
     *
     * @param responseDataType the type of the Java object representing the response data
     * @param contentType the type of the Java object representing the document content
     * @param <R> the type of the Java object representing the response data
     * @param <C> the type of the Java object representing the document content
     * @return a processor
     */
    <R extends QueryResponse, C> Processor<R> getQueryResponseDataProcessor(Class<R> responseDataType, Class<C> contentType);

    /**
     * Generates/retrieves a processor for QueueInfoGetStatus responses with known additional data
     *
     * @param resultTypes A map of node name to types representing those nodes
     * @return a processor
     */
    default Processor<QueueInfoGetStatusResponseData> getQueueInfoGetStatusResponseDataProcessor(final Map<String, Class<?>> resultTypes) {
        throw new UnsupportedOperationException("Method not supported in this implementation");
    }

    /**
     * Generates/retrieves a generator of IDOL documents
     *
     * @param type the type of document to generate
     * @param <T> the type of document to generate
     * @return a document generator
     */
    <T> DocumentGenerator<T> getDocumentGenerator(Class<T> type);
}
