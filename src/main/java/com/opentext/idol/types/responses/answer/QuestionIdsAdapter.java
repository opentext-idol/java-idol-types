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

package com.opentext.idol.types.responses.answer;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("WeakerAccess")
public class QuestionIdsAdapter extends XmlAdapter<QuestionIds, List<String>> {
    @Override
    public List<String> unmarshal(final QuestionIds items) {
        return new ArrayList<>(items.getQuestionId());
    }

    @Override
    public QuestionIds marshal(final List<String> itemList) {
        final QuestionIds items = new QuestionIds();
        items.getQuestionId().addAll(itemList);
        return items;
    }
}
