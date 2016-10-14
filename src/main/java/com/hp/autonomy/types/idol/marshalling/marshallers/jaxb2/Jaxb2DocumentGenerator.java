/*
 * Copyright 2016 Hewlett Packard Enterprise Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.types.idol.marshalling.marshallers.jaxb2;

import com.hp.autonomy.types.idol.content.Documents;
import com.hp.autonomy.types.idol.marshalling.marshallers.DocumentGenerator;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayOutputStream;
import java.nio.charset.Charset;
import java.util.List;

class Jaxb2DocumentGenerator<T> implements DocumentGenerator<T> {
    private final Jaxb2Marshaller marshaller;

    Jaxb2DocumentGenerator(final Class<T> type) {
        marshaller = new Jaxb2Marshaller();
        marshaller.setClassesToBeBound(Documents.class, type);
        marshaller.setMappedClass(type);
    }

    @Override
    public String generateIdolXmlIndexDocument(final Iterable<T> objects, final Charset charset) {
        final Documents documents = new Documents();
        final List<Object> documentList = documents.getContent();
        for (final T object : objects) {
            documentList.add(object);
        }

        @SuppressWarnings("resource")
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        marshaller.marshal(documents, new StreamResult(outputStream));
        return new String(outputStream.toByteArray(), charset) + "\n#DREENDDATANOOP\n\n";
    }
}
