/*
 * Copyright 2017 Hewlett Packard Enterprise Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.types.idol.xjc;

import java.util.LinkedHashMap;

// This class only exists since JAXB won't let us use generics in <bindings><property><baseType>
public class KeyValueMap extends LinkedHashMap<String, String> {
}
