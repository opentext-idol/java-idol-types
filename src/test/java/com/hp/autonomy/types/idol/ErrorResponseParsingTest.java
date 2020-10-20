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

package com.hp.autonomy.types.idol;

import com.autonomy.aci.client.services.AciErrorException;
import com.autonomy.aci.client.services.Processor;
import com.hp.autonomy.types.idol.marshalling.ProcessorFactory;
import com.hp.autonomy.types.idol.marshalling.marshallers.MarshallerFactory;
import com.hp.autonomy.types.idol.marshalling.marshallers.ResponseParser;
import org.junit.Test;
import org.xml.sax.SAXException;

import java.io.IOException;

public class ErrorResponseParsingTest extends AbstractParsingTest<Void> {
    public ErrorResponseParsingTest() throws IOException {
        super(Void.class, "/error.xml");
    }

    @Test(expected = AciErrorException.class)
    @Override
    public void parseResponse() throws IOException {
        super.parseResponse();
    }

    @Test(expected = AciErrorException.class)
    @Override
    public void symmetry() throws IOException, SAXException {
        super.symmetry();
    }

    @Override
    protected Processor<Void> getProcessor(final ProcessorFactory processorFactory, final Class<Void> type) {
        return processorFactory.getVoidProcessor();
    }

    @Override
    protected ResponseParser getResponseParser(final MarshallerFactory marshallerFactory, final Class<Void> type) {
        return marshallerFactory.getResponseParser();
    }
}
