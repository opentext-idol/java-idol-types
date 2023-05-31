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
