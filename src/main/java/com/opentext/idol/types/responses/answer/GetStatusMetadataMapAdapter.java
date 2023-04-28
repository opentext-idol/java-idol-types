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

package com.opentext.idol.types.responses.answer;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("WeakerAccess")
public class GetStatusMetadataMapAdapter extends XmlAdapter<SystemMetadata, Map<String, String>> {
    @Override
    public Map<String, String> unmarshal(final SystemMetadata metadata) {
        final Map<String, String> metadataMap = new LinkedHashMap<>();
        metadata.getField().forEach(field -> metadataMap.put(field.getKey(), field.getValue()));
        return metadataMap;
    }

    @Override
    public SystemMetadata marshal(final Map<String, String> metadataMap) {
        SystemMetadata metadata = null;
        if (metadataMap != null) {
            metadata = new SystemMetadata();
            final List<SystemMetadata.Field> fields = metadata.getField();
            metadataMap.entrySet().forEach(entry -> {
                final SystemMetadata.Field field = new SystemMetadata.Field();
                field.setKey(entry.getKey());
                field.setValue(entry.getValue());
                fields.add(field);
            });
        }
        return metadata;
    }
}
