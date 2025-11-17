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
import com.opentext.idol.types.responses.*;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ResponseParsingTest extends AbstractParsingTest<Object> {

    @Override
    protected Stream<TestData<?>> getData() {
        return Stream.of(
                new TestData<>(Profiles.class, "/profileRead.xml"),
                new TestData<>(ProfileUser.class, "/profileUser.xml"),
                new TestData<>(CommunityStatusResponseData.class, "/communityGetStatus.xml"),
//                new TestData<>(DateConvertResponseData.class, ""),
//                new TestData<>(DetectLanguageResponseData.class, ""),
//                new TestData<>(DocumentStatsResponseData.class, ""),
//                new TestData<>(GetAllRefsResponseData.class, ""),
                new TestData<>(GetChildrenResponseData.class, "/getChildren.xml"),
                new TestData<>(GetContentResponseData.class, "/getContent.xml"),
                new TestData<>(GetQueryTagValuesResponseData.class, "/getQueryTagValuesRangeResponse.xml"),
                new TestData<>(GetQueryTagValuesResponseData.class, "/getQueryTagValuesDateRangeResponse.xml"),
                new TestData<>(GetQueryTagValuesResponseData.class, "/getQueryTagValuesFieldDependenceMultiLevelResponse.xml"),
//                new TestData<>(GetSampleResponseData.class, ""),
                new TestData<>(GetStatusResponseData.class, "/getStatus.xml"),
                new TestData<>(GetTagNamesResponseData.class, "/getTagNames.xml"),
//                new TestData<>(GetTagValuesResponseData.class, ""),
                new TestData<>(GetVersionResponseData.class, "/getVersion.xml"),
//                new TestData<>(HighlightResponseData.class, ""),
                new TestData<>(LanguageSettingsResponseData.class, "/languageSettings.xml"),
//                new TestData<>(ListResponseData.class, ""),
                new TestData<>(QueryResponseData.class, "/query.xml"),
                new TestData<>(QueryResponseData.class, "/queryForPromotions.xml"),
                new TestData<>(QueryResponseData.class, "/querySummary.xml"),
//                new TestData<>(QuerySummaryManagementResponseData.class, ""),
                new TestData<>(Users.class, "/roleGetUserList.xml"),
                new TestData<>(RolesResponseData.class, "/roleUserGetRoleList.xml"),
//                new TestData<>(SuggestOnTextResponseData.class, ""),
//                new TestData<>(SuggestResponseData.class, ""),
//                new TestData<>(SummarizeResponseData.class, ""),
                new TestData<>(Security.class, "/security.xml"),
//                new TestData<>(TermExpandResponseData.class, ""),
//                new TestData<>(TermGetAllResponseData.class, ""),
//                new TestData<>(TermGetBestResponseData.class, ""),
//                new TestData<>(TermGetInfoResponseData.class, ""),
                new TestData<>(TypeAheadResponseData.class, "/typeAhead.xml"),
//                new TestData<>(TermGetInfoResponseData.class, ""),
                new TestData<>(Uid.class, "/userAdd.xml"),
                new TestData<>(Void.class, "/userDelete.xml"),
                new TestData<>(User.class, "/userRead.xml"),
                new TestData<>(UserDetails.class, "/userReadUserListDetails.xml"));
    }

    @Override
    protected <T extends Object> Processor<T> getProcessor(final ProcessorFactory processorFactory, final TestData<T> data) {
        return processorFactory.getResponseDataProcessor(data.getType());
    }

    @Override
    protected <T extends Object> ResponseParser getResponseParser(final MarshallerFactory marshallerFactory, final TestData<T> data) {
        return marshallerFactory.getResponseDataParser(data.getType());
    }

    @Test
    protected void ValueDetailsCompatibilityTest() throws IOException {
        TestData<GetQueryTagValuesResponseData> data = new TestData<>(GetQueryTagValuesResponseData.class, "/getQueryTagValuesFieldDependenceMultiLevelResponse-compat.xml");
        final Processor<GetQueryTagValuesResponseData> processor = getProcessor(processorFactory, data);
        try (final MockAciResponseInputStream mockInputStream = new MockAciResponseInputStream(
            getClass().getResourceAsStream("/getQueryTagValuesFieldDependenceMultiLevelResponse-compat.xml")
        )) {
            final GetQueryTagValuesResponseData gqtv = processor.process(mockInputStream);
            assertNotNull(gqtv);
            assertEquals(1.78, gqtv.getValues().getField().get(0).getField().get(0).getValueDetails().getValueMin().getValue());
            assertEquals(1.78, gqtv.getValues().getField().get(0).getField().get(0).getValueDetails().getValuemin().getValue());
        }
    }
}
