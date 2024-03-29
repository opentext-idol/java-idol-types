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
