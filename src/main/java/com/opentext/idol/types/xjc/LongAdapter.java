/*
 * Copyright 2026 Open Text.
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


package com.opentext.idol.types.xjc;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;

public class LongAdapter
    extends XmlAdapter<String, Long>
{
    public Long unmarshal(String value) {
        return new Long(value);
    }

    public String marshal(Long value) {
        if (value == null) {
            return null;
        }
        return value.toString();
    }

}
