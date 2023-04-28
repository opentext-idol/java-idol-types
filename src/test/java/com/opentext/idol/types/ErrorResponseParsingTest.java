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

package com.opentext.idol.types;

import com.autonomy.aci.client.services.AciErrorException;
import com.autonomy.aci.client.services.Processor;
import com.opentext.idol.types.marshalling.ProcessorFactory;
import com.opentext.idol.types.marshalling.marshallers.MarshallerFactory;
import com.opentext.idol.types.marshalling.marshallers.ResponseParser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.stream.Stream;

public class ErrorResponseParsingTest extends AbstractParsingTest<Void> {

    @Override
    protected Stream<TestData<? extends Void>> getData() {
        return Stream.of(
            new TestData<>(Void.class, "/error.xml")
        );
    }

    @Override
    @ParameterizedTest
    @MethodSource("getTestData")
    public <T extends Void> void parseResponse(final TestData<T> data) throws IOException {
        Assertions.assertThrows(AciErrorException.class, () -> super.parseResponse(data));
    }

    @Override
    @ParameterizedTest
    @MethodSource("getTestData")
    public <T extends Void> void symmetry(final TestData<T> data) throws IOException, SAXException {
        Assertions.assertThrows(AciErrorException.class, () -> super.symmetry(data));
    }

    @Override
    protected <T extends Void> Processor<T> getProcessor(final ProcessorFactory processorFactory, final TestData<T> data) {
        return (Processor<T>) processorFactory.getVoidProcessor();
    }

    @Override
    protected <T extends Void> ResponseParser getResponseParser(final MarshallerFactory marshallerFactory, final TestData<T> data) {
        return marshallerFactory.getResponseParser();
    }
}
