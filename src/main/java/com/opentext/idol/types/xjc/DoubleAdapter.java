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
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class DoubleAdapter extends XmlAdapter<String, Double> {
    private static final DecimalFormat DECIMAL_FORMAT;
    
    static {
        DECIMAL_FORMAT = new DecimalFormat("0.00", DecimalFormatSymbols.getInstance(Locale.US));
        DECIMAL_FORMAT.setMaximumFractionDigits(2);
        DECIMAL_FORMAT.setMinimumFractionDigits(2);
        DECIMAL_FORMAT.setGroupingUsed(false);
    }

    public static Double parseDouble(final String s) {
        if (s == null || s.isEmpty()) {
            return null;
        }
        return Double.valueOf(s);
    }
    
    public static String printDouble(final Double d) {
        if (d == null) {
            return null;
        }
        return DECIMAL_FORMAT.format(d);
    }

    @Override
    public Double unmarshal(final String v) { 
        return parseDouble(v);
    }

    @Override
    public String marshal(final Double v) {
        return printDouble(v);
    }
}