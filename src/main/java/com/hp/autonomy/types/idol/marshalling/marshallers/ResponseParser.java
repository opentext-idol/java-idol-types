/*
 * Copyright 2016 Hewlett Packard Enterprise Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.types.idol.marshalling.marshallers;

import com.autonomy.aci.client.services.AciErrorException;
import com.hp.autonomy.types.idol.responses.Autnresponse;

import java.io.InputStream;

/**
 * Parses IDOL error response and generates a corresponding exception
 */
@FunctionalInterface
public interface ResponseParser {
    /**
     * Parses IDOL xml responses using JAXB via Spring OXM layer.
     *
     * @param inputStream the stream of xml data to parse
     * @return a Java object representing the specified resource parsed from the response
     */
    Autnresponse parseResponse(InputStream inputStream) throws AciErrorException;
}
