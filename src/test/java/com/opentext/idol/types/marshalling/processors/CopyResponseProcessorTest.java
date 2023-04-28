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

package com.opentext.idol.types.marshalling.processors;

import com.autonomy.aci.client.services.Processor;
import com.autonomy.aci.client.services.ProcessorException;
import com.autonomy.aci.client.transport.AciResponseInputStream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CopyResponseProcessorTest {

    private InputStream inputStream;

    @BeforeEach
    public void setUp() throws UnsupportedEncodingException {
        inputStream = new ByteArrayInputStream("Hello world!".getBytes("UTF-8"));
    }

    @Test
    public void process() throws IOException {
        final AciResponseInputStream aciResponseInputStream = mock(AciResponseInputStream.class);
        when(aciResponseInputStream.read(any(byte[].class))).thenAnswer(invocationOnMock -> inputStream.read((byte[]) invocationOnMock.getArguments()[0]));

        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        final Processor<Boolean> processor = new CopyResponseProcessor(outputStream);
        processor.process(aciResponseInputStream);

        assertEquals(outputStream.toString("UTF-8"), "Hello world!");
    }

    @Test
    public void badInput() throws IOException {
        final AciResponseInputStream aciResponseInputStream = mock(AciResponseInputStream.class);
        //noinspection ProhibitedExceptionDeclared
        when(aciResponseInputStream.read(any(byte[].class))).thenAnswer(invocationOnMock -> {
            throw new IOException();
        });
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        final Processor<Boolean> processor = new CopyResponseProcessor(outputStream);
        Assertions.assertThrows(ProcessorException.class, () -> {
            processor.process(aciResponseInputStream);
        });
    }
}
