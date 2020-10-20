/*
 * (c) Copyright 2017 Micro Focus or one of its affiliates.
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

package com.hp.autonomy.types.idol.responses.community;

import com.hp.autonomy.types.idol.responses.UserFields;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class KeyValueMapAdapter extends XmlAdapter<UserFields, Map<String, String>> {

    @Override
    public Map<String,String> unmarshal(final UserFields v) throws Exception {
        final LinkedHashMap<String,String> ret = new LinkedHashMap<String,String>();

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
