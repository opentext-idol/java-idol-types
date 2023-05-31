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

package com.opentext.idol.types.responses.answer;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("WeakerAccess")
public class AskMetadataAdapter extends XmlAdapter<AskMetadata, AskMetadataMap> {
    @Override
    public AskMetadataMap unmarshal(final AskMetadata metadata) {
        final Map<String, AskMetadataMap> sections = new HashMap<>();
        for (final Object metadataItem : metadata.getAny()) {
            if (metadataItem instanceof Element) {
                final Element element = (Element) metadataItem;
                sections.put(element.getLocalName(), unmarshalFromElement(element));
            }
        }
        return new AskMetadataMap(sections, new HashMap<>());
    }

    private AskMetadataMap unmarshalFromElement(final Element element) {
        final Map<String, AskMetadataMap> sections = new HashMap<>();
        final NodeList childNodes = element.getChildNodes();
        for (int childNodeIndex = 0; childNodeIndex < childNodes.getLength(); childNodeIndex++) {
            final Node childNode = childNodes.item(childNodeIndex);
            if (childNode instanceof Element) {
                sections.put(childNode.getLocalName(), unmarshalFromElement((Element) childNode));
            }
        }

        final Map<String, String> values = new HashMap<>();
        final NamedNodeMap attrs = element.getAttributes();
        for (int attrIndex = 0; attrIndex < attrs.getLength(); attrIndex++) {
            final Node attr = attrs.item(attrIndex);
            values.put(attr.getLocalName(), attr.getNodeValue());
        }

        return new AskMetadataMap(sections, values);
    }

    @Override
    public AskMetadata marshal(final AskMetadataMap metadataMap) throws ParserConfigurationException {
        if (metadataMap == null) {
            return null;
        }
        final Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
        final NodeList nodes = marshalToElement(doc, "root", metadataMap)
            .getChildNodes();
        if (nodes.getLength() == 0) {
            return null;
        } else {
            final AskMetadata metadata = new AskMetadata();
            for (int nodeIndex = 0; nodeIndex < nodes.getLength(); nodeIndex++) {
                metadata.getAny().add(nodes.item(nodeIndex));
            }
            return metadata;
        }
    }

    private Element marshalToElement(
        final Document doc,
        final String elementName,
        final AskMetadataMap metadataMap
    ) {
        final Element element = doc.createElement(elementName);

        for (final String sectionName : metadataMap.getSections().keySet()) {
            final Element childElement = marshalToElement(doc, sectionName,
                metadataMap.getSections().get(sectionName));
            element.appendChild(childElement);
        }

        for (final String valueName : metadataMap.getValues().keySet()) {
            element.setAttribute(valueName, metadataMap.getValues().get(valueName));
        }

        return element;
    }

}
