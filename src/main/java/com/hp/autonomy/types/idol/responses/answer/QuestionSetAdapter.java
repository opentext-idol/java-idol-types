/*
 * Copyright 2016 Hewlett Packard Enterprise Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.types.idol.responses.answer;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("WeakerAccess")
public class QuestionSetAdapter extends XmlAdapter<QuestionSet, List<String>> {
    @Override
    public List<String> unmarshal(final QuestionSet items) {
        return new ArrayList<>(items.getText());
    }

    @Override
    public QuestionSet marshal(final List<String> itemList) {
        final QuestionSet items = new QuestionSet();
        items.getText().addAll(itemList);
        return items;
    }
}
