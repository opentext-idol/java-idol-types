/*
 * Copyright 2016 Hewlett Packard Enterprise Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.types.idol.marshalling;

import com.hp.autonomy.types.idol.marshalling.marshallers.MarshallerFactory;
import com.hp.autonomy.types.idol.marshalling.marshallers.jaxb2.Jaxb2MarshallerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Simple spring configuration class for JAXB marshalling with Spring Jaxb2 underlying implementation
 */
@Configuration
public class Jaxb2ParsingConfiguration {
    @Bean
    public ProcessorFactory processingFactory(final MarshallerFactory marshallerFactory) {
        return new ProcessorFactoryImpl(marshallerFactory);
    }

    @Bean
    public MarshallerFactory marshallerFactory() {
        return new Jaxb2MarshallerFactory();
    }
}
