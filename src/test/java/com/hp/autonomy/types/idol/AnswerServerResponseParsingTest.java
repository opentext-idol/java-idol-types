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

package com.hp.autonomy.types.idol;

import com.autonomy.aci.client.services.Processor;
import com.hp.autonomy.types.idol.marshalling.ProcessorFactory;
import com.hp.autonomy.types.idol.marshalling.marshallers.MarshallerFactory;
import com.hp.autonomy.types.idol.marshalling.marshallers.ResponseParser;
import com.hp.autonomy.types.idol.responses.answer.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
@SuppressWarnings("OverlyCoupledClass")
public class AnswerServerResponseParsingTest<T> extends AbstractParsingTest<T> {
    @SuppressWarnings("OverlyCoupledMethod")
    @Parameterized.Parameters(name = "{1}")
    public static Collection<Object[]> data() {
        return Arrays.asList(
                new Object[]{AskResponsedata.class, "/answer/askAnswerbank.xml"},
                new Object[]{AskResponsedata.class, "/answer/askFactbank.xml"},
                new Object[]{ReportResponsedata.class, "/answer/report.xml"},
                new Object[]{GetStatusResponsedata.class, "/answer/getStatus.xml"},
                new Object[]{GetJobStatusResponsedata.class, "/answer/getJobStatus.xml"},
                new Object[]{ManageResourcesResponsedata.class, "/answer/manageResources.xml"},
                new Object[]{GetResourcesResponsedata.class, "/answer/getQuestions.xml"},
                new Object[]{GetResourcesResponsedata.class, "/answer/getQuestionEquivalenceClasses.xml"},
                new Object[]{GetResourcesResponsedata.class, "/answer/getSchemas.xml"},
                new Object[]{GetResourcesResponsedata.class, "/answer/getXsds.xml"},
                new Object[]{GetResourcesResponsedata.class, "/answer/getAnswerbankXsds.xml"},
                new Object[]{GetStatsResponsedata.class, "/answer/getStats.xml"},
                new Object[]{TestRuleResponsedata.class, "/answer/testRule.xml"});
    }

    public AnswerServerResponseParsingTest(final Class<T> type, final String fileName) throws IOException {
        super(type, fileName);
    }

    @Override
    protected Processor<T> getProcessor(final ProcessorFactory processorFactory, final Class<T> type) {
        return processorFactory.getResponseDataProcessor(type);
    }

    @Override
    protected ResponseParser getResponseParser(final MarshallerFactory marshallerFactory, final Class<T> type) {
        return marshallerFactory.getResponseDataParser(type);
    }
}
