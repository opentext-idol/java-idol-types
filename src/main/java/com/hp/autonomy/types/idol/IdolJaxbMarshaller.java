/*
 * Copyright 2016 Hewlett Packard Enterprise Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.types.idol;

import java.io.InputStream;
import java.nio.charset.Charset;

@SuppressWarnings("WeakerAccess")
public interface IdolJaxbMarshaller<E1 extends Exception, E2 extends Exception> {
    Autnresponse parseIdolResponse(InputStream inputStream) throws E1, E2;

    @SuppressWarnings("CastToConcreteClass")
    <T> Autnresponse parseIdolResponse(InputStream inputStream, Class<T> type) throws E1, E2;

    <R extends QueryResponse, C> Autnresponse parseIdolQueryResponse(InputStream inputStream, Class<R> responseType, Class<C> contentType) throws E1, E2;

    <T> T parseIdolResponseData(InputStream inputStream, Class<T> type) throws E1, E2;

    <R extends QueryResponse, C> R parseIdolQueryResponseData(InputStream inputStream, Class<R> responseType, Class<C> contentType) throws E1, E2;

    <T> String generateXmlDocument(final Iterable<T> objects, final Class<T> type, final Charset charset) throws E2;

    /**
     * Represents a function that accepts one argument and produces a result.
     * TODO: delete this once we can use Java8
     *
     * @param <T> the type of the input to the function
     * @param <R> the type of the result of the function
     */
    interface Function<T, R> {
        /**
         * Applies this function to the given argument.
         *
         * @param t the function argument
         * @return the function result
         */
        R apply(T t);
    }

    /**
     * Represents a function that accepts two arguments and produces a result.
     * TODO: delete this once we can use Java8
     *
     * @param <T> the type of the first argument to the function
     * @param <U> the type of the second argument to the function
     * @param <R> the type of the result of the function
     */
    interface BiFunction<T, U, R> {

        /**
         * Applies this function to the given arguments.
         *
         * @param t the first function argument
         * @param u the second function argument
         * @return the function result
         */
        R apply(T t, U u);
    }
}
