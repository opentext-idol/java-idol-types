/*
 * Copyright 2016 Hewlett Packard Enterprise Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.types.idol.marshalling.marshallers;

import java.nio.charset.Charset;

@FunctionalInterface
public interface DocumentGenerator<T> {
    String generateIdolXmlIndexDocument(final Iterable<T> objects, final Charset charset);
}
