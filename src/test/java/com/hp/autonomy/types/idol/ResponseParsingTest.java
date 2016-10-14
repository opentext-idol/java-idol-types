/*
 * Copyright 2016 Hewlett Packard Enterprise Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.types.idol;

import com.autonomy.aci.client.services.Processor;
import com.hp.autonomy.types.idol.marshalling.ProcessorFactory;
import com.hp.autonomy.types.idol.marshalling.marshallers.MarshallerFactory;
import com.hp.autonomy.types.idol.marshalling.marshallers.ResponseParser;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
@SuppressWarnings("OverlyCoupledClass")
public class ResponseParsingTest<T> extends AbstractParsingTest<T> {
    @SuppressWarnings("OverlyCoupledMethod")
    @Parameterized.Parameters(name = "{1}")
    public static Collection<Object[]> data() {
        return Arrays.asList(
//                new Object[]{DateConvertResponseData.class, ""},
//                new Object[]{DetectLanguageResponseData.class, ""},
//                new Object[]{DocumentStatsResponseData.class, ""},
//                new Object[]{GetAllRefsResponseData.class, ""},
                new Object[]{GetContentResponseData.class, "/getContent.xml"},
                new Object[]{GetQueryTagValuesResponseData.class, "/getQueryTagValuesRangeResponse.xml"},
//                new Object[]{GetSampleResponseData.class, ""},
                new Object[]{GetStatusResponseData.class, "/getStatus.xml"},
                new Object[]{GetTagNamesResponseData.class, "/getTagNames.xml"},
//                new Object[]{GetTagValuesResponseData.class, ""},
                new Object[]{GetVersionResponseData.class, "/getVersion.xml"},
//                new Object[]{HighlightResponseData.class, ""},
                new Object[]{LanguageSettingsResponseData.class, "/languageSettings.xml"},
//                new Object[]{ListResponseData.class, ""},
                new Object[]{QueryResponseData.class, "/query.xml"},
                new Object[]{QueryResponseData.class, "/queryForPromotions.xml"},
                new Object[]{QueryResponseData.class, "/querySummary.xml"},
//                new Object[]{QuerySummaryManagementResponseData.class, ""},
                new Object[]{Users.class, "/roleGetUserList.xml"},
                new Object[]{RolesResponseData.class, "/roleUserGetRoleList.xml"},
//                new Object[]{SuggestOnTextResponseData.class, ""},
//                new Object[]{SuggestResponseData.class, ""},
//                new Object[]{SummarizeResponseData.class, ""},
                new Object[]{Security.class, "/security.xml"},
//                new Object[]{TermExpandResponseData.class, ""},
//                new Object[]{TermGetAllResponseData.class, ""},
//                new Object[]{TermGetBestResponseData.class, ""},
//                new Object[]{TermGetInfoResponseData.class, ""},
                new Object[]{TypeAheadResponseData.class, "/typeAhead.xml"},
//                new Object[]{TermGetInfoResponseData.class, ""},
                new Object[]{Uid.class, "/userAdd.xml"},
                new Object[]{Void.class, "/userDelete.xml"},
                new Object[]{User.class, "/userRead.xml"},
                new Object[]{UserDetails.class, "/userReadUserListDetails.xml"});
    }

    public ResponseParsingTest(final Class<T> type, final String fileName) throws IOException {
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
