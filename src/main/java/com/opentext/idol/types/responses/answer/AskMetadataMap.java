package com.opentext.idol.types.responses.answer;

import lombok.Value;

import java.util.Map;

@Value
public class AskMetadataMap {
    Map<String, AskMetadataMap> sections;
    Map<String, String> values;
}
