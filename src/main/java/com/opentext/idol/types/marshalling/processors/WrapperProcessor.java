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

package com.opentext.idol.types.marshalling.processors;

import com.autonomy.aci.client.services.Processor;
import com.autonomy.aci.client.transport.AciResponseInputStream;

import java.util.function.Function;

/**
 * Convenience processor for performing a known simple transformation on the processed output
 *
 * @param <T> The response object of the inner processor
 * @param <R> The transformation function
 */
@SuppressWarnings("serial")
public class WrapperProcessor<T, R> implements Processor<R> {
    private final Processor<T> processor;
    private final Function<T, R> function;

    public WrapperProcessor(final Processor<T> processor, final Function<T, R> function) {
        this.processor = processor;
        this.function = function;
    }

    @Override
    public R process(final AciResponseInputStream aciResponse) {
        return function.apply(processor.process(aciResponse));
    }
}
