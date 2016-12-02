/*
 * Copyright 2016 Hewlett Packard Enterprise Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.types.idol.responses.answer;

import javax.xml.bind.annotation.adapters.XmlAdapter;
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
