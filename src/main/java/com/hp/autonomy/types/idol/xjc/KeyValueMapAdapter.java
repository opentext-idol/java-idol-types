/*
 * Copyright 2017 Hewlett Packard Enterprise Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.types.idol.xjc;

import com.hp.autonomy.types.idol.responses.UserFields;
import java.util.Map;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class KeyValueMapAdapter extends XmlAdapter<UserFields, Map<String, String>> {

    @Override
    public KeyValueMap unmarshal(final UserFields v) throws Exception {
        final KeyValueMap ret = new KeyValueMap();

        for(final Element element : v.getAny()) {
            ret.put(element.getLocalName(), element.getTextContent());
        }

        return ret;
    }

    @Override
    public UserFields marshal(final Map<String, String> v) throws Exception {
        if (v == null) {
            return null;
        }

        final UserFields toReturn = new UserFields();

        if (!v.isEmpty()){
            // This is not very efficient, but we're mainly using this for XML parsing not serialization.
            final Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();

            for(Map.Entry<String, String> entry : v.entrySet()) {
                final Element el = doc.createElementNS(null, entry.getKey());
                el.setTextContent(entry.getValue());
                toReturn.getAny().add(el);
            }
        }


        return toReturn;
    }
}
