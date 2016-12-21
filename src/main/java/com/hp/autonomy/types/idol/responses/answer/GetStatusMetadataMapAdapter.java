/*
 * Copyright 2016 Hewlett Packard Enterprise Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.types.idol.responses.answer;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("WeakerAccess")
public class GetStatusMetadataMapAdapter extends XmlAdapter<GetstatusMetadata, Map<String, String>> {
    @Override
    public Map<String, String> unmarshal(final GetstatusMetadata metadata) {
        final Map<String, String> metadataMap = new LinkedHashMap<>();
        metadata.getField().forEach(field -> metadataMap.put(field.getKey(), field.getValue()));
        return metadataMap;
    }

    @Override
    public GetstatusMetadata marshal(final Map<String, String> metadataMap) {
        GetstatusMetadata metadata = null;
        if (metadataMap != null) {
            metadata = new GetstatusMetadata();
            final List<GetstatusMetadata.Field> fields = metadata.getField();
            metadataMap.entrySet().forEach(entry -> {
                final GetstatusMetadata.Field field = new GetstatusMetadata.Field();
                field.setKey(entry.getKey());
                field.setValue(entry.getValue());
                fields.add(field);
            });
        }
        return metadata;
    }
}
