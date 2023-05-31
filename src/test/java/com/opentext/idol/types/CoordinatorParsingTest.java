/*
 * Copyright 2015 Open Text.
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

import com.autonomy.aci.client.services.Processor;
import com.opentext.idol.types.coordinator.GetControllersResponseData;
import com.opentext.idol.types.coordinator.GetSchedulesResponseData;
import com.opentext.idol.types.coordinator.GetServicesResponseData;
import com.opentext.idol.types.coordinator.GetStatusOverviewResponseData;
import com.opentext.idol.types.marshalling.ProcessorFactory;
import com.opentext.idol.types.marshalling.marshallers.MarshallerFactory;
import com.opentext.idol.types.marshalling.marshallers.ResponseParser;

import java.util.stream.Stream;

public class CoordinatorParsingTest extends AbstractParsingTest<Object> {

    @Override
    protected Stream<TestData<?>> getData() {
        return Stream.of(
                new TestData<>(GetControllersResponseData.class, "/coordinator/controllers.xml"),
                new TestData<>(GetServicesResponseData.class, "/coordinator/services.xml"),
                new TestData<>(GetSchedulesResponseData.class, "/coordinator/schedules.xml"),
                new TestData<>(GetStatusOverviewResponseData.class, "/coordinator/getStatusOverview.xml")
        );
    }

    @Override
    protected <T extends Object> Processor<T> getProcessor(final ProcessorFactory processorFactory, final TestData<T> data) {
        return processorFactory.getResponseDataProcessor(data.getType());
    }

    @Override
    protected <T extends Object> ResponseParser getResponseParser(final MarshallerFactory marshallerFactory, final TestData<T> data) {
        return marshallerFactory.getResponseDataParser(data.getType());
    }
}
