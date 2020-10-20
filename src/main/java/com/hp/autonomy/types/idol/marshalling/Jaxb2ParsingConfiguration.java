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
