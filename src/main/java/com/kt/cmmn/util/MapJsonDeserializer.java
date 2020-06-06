package com.kt.cmmn.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.util.HashMap;

public class MapJsonDeserializer extends JsonDeserializer<HashMap<String,Object>> {


    @Override
    public HashMap<String, Object> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        try {
            HashMap<String, Object> map = new HashMap<>();
            map = super.deserialize(jsonParser,deserializationContext, map);
            return map;
        } catch(java.lang.UnsupportedOperationException e) {
            return (HashMap<String, Object>) MapUtils.fromJson(jsonParser.getText());
        }
    }
}
