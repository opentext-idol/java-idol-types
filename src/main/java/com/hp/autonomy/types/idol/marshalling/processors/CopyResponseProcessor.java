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

package com.hp.autonomy.types.idol.marshalling.processors;

import com.autonomy.aci.client.services.Processor;
import com.autonomy.aci.client.services.ProcessorException;
import com.autonomy.aci.client.transport.AciResponseInputStream;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Processor which copies the entire response stream into the given {@link OutputStream} before flushing the output stream.
 */
@SuppressWarnings({"WeakerAccess", "TransientFieldNotInitialized"})
public class CopyResponseProcessor implements Processor<Boolean> {

    private static final long serialVersionUID = 8869650394998971739L;

    private final transient OutputStream outputStream;

    /**
     * Creates a new CopyResponseProcessor which writes to the given OutputStream.
     * @param outputStream The OutputStream to write to
     */
    public CopyResponseProcessor(final OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    /**
     * Copies the given AciResponseInputStream into the OutputStream.
     * @param aciResponse The AciResponseInputStream to read
     * @return true If the method completes successfully
     * @throws ProcessorException If an IOException is thrown internally.
     */
    @Override
    public Boolean process(final AciResponseInputStream aciResponse) {
        try {
            IOUtils.copy(aciResponse, outputStream);

            outputStream.flush();

            return true;
        }
        catch(final IOException e) {
            throw new ProcessorException(e);
        }
    }
}
