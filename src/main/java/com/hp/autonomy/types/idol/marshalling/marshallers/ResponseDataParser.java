/*
 * Copyright 2016 Hewlett Packard Enterprise Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.types.idol.marshalling.marshallers;

import com.autonomy.aci.client.services.AciErrorException;

import java.io.InputStream;

/**
 * Parses responsedata xml element in IDOL responses using JAXB via Spring OXM layer.
 * @param <T> the resource type to parse out
 */
public interface ResponseDataParser<T> extends ResponseParser {
    /**
     * Parses responsedata xml element in IDOL responses using JAXB via Spring OXM layer.
     *
     * @param inputStream the stream of xml data to parse
     * @return a Java object representing the specified response data
     * @throws AciErrorException exception containing error information if Idol returns an error
     */
    T parseResponseData(InputStream inputStream) throws AciErrorException;
}
