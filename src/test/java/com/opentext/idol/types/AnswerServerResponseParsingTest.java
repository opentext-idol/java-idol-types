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

import com.autonomy.aci.client.services.Processor;
import com.opentext.idol.types.marshalling.ProcessorFactory;
import com.opentext.idol.types.marshalling.marshallers.MarshallerFactory;
import com.opentext.idol.types.marshalling.marshallers.ResponseParser;
import com.opentext.idol.types.responses.answer.*;

import java.util.stream.Stream;

public class AnswerServerResponseParsingTest extends AbstractParsingTest<Object> {

    @Override
    protected Stream<TestData<?>> getData() {
        return Stream.of(
                new TestData<>(AskResponsedata.class, "/answer/askAnswerbank.xml"),
                new TestData<>(AskResponsedata.class, "/answer/askFactbank.xml"),
                new TestData<>(ReportResponsedata.class, "/answer/report.xml"),
                new TestData<>(GetStatusResponsedata.class, "/answer/getStatus.xml"),
                new TestData<>(GetJobStatusResponsedata.class, "/answer/getJobStatus.xml"),
                new TestData<>(ManageResourcesResponsedata.class, "/answer/manageResources.xml"),
                new TestData<>(GetResourcesResponsedata.class, "/answer/getQuestions.xml"),
                new TestData<>(GetResourcesResponsedata.class, "/answer/getQuestionEquivalenceClasses.xml"),
                new TestData<>(GetResourcesResponsedata.class, "/answer/getSchemas.xml"),
                new TestData<>(GetResourcesResponsedata.class, "/answer/getXsds.xml"),
                new TestData<>(GetResourcesResponsedata.class, "/answer/getAnswerbankXsds.xml"),
                new TestData<>(GetStatsResponsedata.class, "/answer/getStats.xml"),
                new TestData<>(TestRuleResponsedata.class, "/answer/testRule.xml"));
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
