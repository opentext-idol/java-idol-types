/*
 * Copyright 2016 Hewlett Packard Enterprise Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.types.idol;

import com.autonomy.aci.client.services.AciErrorException;
import com.autonomy.aci.client.services.ProcessorException;

import java.io.InputStream;
import java.nio.charset.Charset;

@SuppressWarnings("WeakerAccess")
public interface IdolJaxbMarshaller {
    Autnresponse parseIdolResponse(InputStream inputStream) throws AciErrorException, ProcessorException;

    @SuppressWarnings("CastToConcreteClass")
    <T> Autnresponse parseIdolResponse(InputStream inputStream, Class<T> type) throws AciErrorException, ProcessorException;

    <R extends QueryResponse, C> Autnresponse parseIdolQueryResponse(InputStream inputStream, Class<R> responseType, Class<C> contentType) throws AciErrorException, ProcessorException;

    <T> T parseIdolResponseData(InputStream inputStream, Class<T> type) throws AciErrorException, ProcessorException;

    <R extends QueryResponse, C> R parseIdolQueryResponseData(InputStream inputStream, Class<R> responseType, Class<C> contentType) throws AciErrorException, ProcessorException;

    <T> String generateXmlDocument(final Iterable<T> objects, final Class<T> type, final Charset charset) throws ProcessorException;
}
