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

package com.opentext.idol.types.marshalling.marshallers;

import com.autonomy.aci.client.services.AciErrorException;
import com.opentext.idol.types.responses.Autnresponse;

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
     * @throws AciErrorException exception containing error information if Idol returns an error
     */
    Autnresponse parseResponse(InputStream inputStream) throws AciErrorException;
}
