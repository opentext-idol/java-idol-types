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

package com.opentext.idol.types;

import com.opentext.idol.types.marshalling.Jaxb2ParsingConfiguration;
import com.opentext.idol.types.marshalling.ProcessorFactory;
import com.opentext.idol.types.marshalling.marshallers.DocumentGenerator;
import jakarta.xml.bind.annotation.*;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SuppressWarnings("SpringJavaAutowiredMembersInspection")
@SpringBootTest(classes = Jaxb2ParsingConfiguration.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class GenerateIndexableXmlDocumentTest {
    private static final File TEST_DIR = new File("./target/test");

    @BeforeAll
    public static void init() throws IOException {
        FileUtils.forceMkdir(TEST_DIR);
    }

    @AfterAll
    public static void destroy() throws IOException {
        FileUtils.forceDelete(TEST_DIR);
    }

    @Autowired
    private ProcessorFactory processorFactory;

    @Test
    public void generateFile() throws IOException {
        final Collection<String> numericFields = new ArrayList<>(1000);
        final Random random = new Random();
        for (int i = 0; i < 1000; i++) {
            numericFields.add(String.valueOf(1451610061 + random.nextInt(1000)));
        }

        final Collection<SampleDoc> sampleDocs = new ArrayList<>(numericFields.size());
        int i = 0;
        for (final String numericField : numericFields) {
            sampleDocs.add(new SampleDoc(UUID.randomUUID().toString(), "Sample Date Title " + i++, "Sample Date Content", numericField));
        }

        @SuppressWarnings("TypeMayBeWeakened")
        final DocumentGenerator<SampleDoc> documentGenerator = processorFactory.getDocumentGenerator(SampleDoc.class);
        final File outputFile = new File(TEST_DIR, "sampleFile.xml");
        FileUtils.writeStringToFile(outputFile, documentGenerator.generateIdolXmlIndexDocument(sampleDocs, StandardCharsets.UTF_8));
        assertTrue(outputFile.exists());
    }

    @SuppressWarnings({"unused", "WeakerAccess"})
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "sampleDoc", propOrder = {
    })
    @XmlRootElement(namespace = "", name = "DOCUMENT")
    public static class SampleDoc {
        @XmlElement(namespace = "", name = "DREREFERENCE")
        private String reference;
        @XmlElement(namespace = "", name = "DRETITLE")
        private String title;
        @XmlElement(namespace = "", name = "DRECONTENT")
        private String content;
        @XmlElement(namespace = "", name = "SOME_DATE")
        private String numericField;

        public SampleDoc() {
        }

        public SampleDoc(final String reference, final String title, final String content, final String numericField) {
            this.reference = reference;
            this.title = title;
            this.content = content;
            this.numericField = numericField;
        }

        public String getReference() {
            return reference;
        }

        public String getTitle() {
            return title;
        }

        public String getContent() {
            return content;
        }

        public String getNumericField() {
            return numericField;
        }
    }
}
