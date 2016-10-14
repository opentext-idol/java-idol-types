/*
 * Copyright 2016 Hewlett Packard Enterprise Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.types.idol;

import com.autonomy.aci.client.transport.AciResponseInputStream;

import java.io.InputStream;

class MockAciResponseInputStream extends AciResponseInputStream {
    MockAciResponseInputStream(final InputStream inputStream) {
        super(inputStream);
    }

    @Override
    public int getStatusCode() {
        return 0;
    }

    @Override
    public String getHeader(final String name) {
        return null;
    }

    @Override
    public String getContentEncoding() {
        return null;
    }

    @Override
    public long getContentLength() {
        return 0;
    }

    @Override
    public String getContentType() {
        return null;
    }
}
