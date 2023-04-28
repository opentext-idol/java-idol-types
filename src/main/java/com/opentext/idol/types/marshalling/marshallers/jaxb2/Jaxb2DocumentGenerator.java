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

package com.opentext.idol.types.marshalling.marshallers.jaxb2;

import com.opentext.idol.types.content.Documents;
import com.opentext.idol.types.marshalling.marshallers.DocumentGenerator;
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
