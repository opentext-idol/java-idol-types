/*
 * Copyright 2016 Hewlett Packard Enterprise Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.types.idol.responses;

import java.util.List;

@FunctionalInterface
public interface QueryResponse {
    List<Hit> getHits();
}
