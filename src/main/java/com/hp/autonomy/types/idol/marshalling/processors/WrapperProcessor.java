/*
 * Copyright 2016 Hewlett Packard Enterprise Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.types.idol.marshalling.processors;

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
