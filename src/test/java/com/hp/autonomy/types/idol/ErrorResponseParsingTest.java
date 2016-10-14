/*
 * Copyright 2016 Hewlett Packard Enterprise Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
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
