package com.kt.cmmn.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MapJsonDeserializer extends  JsonDeserializer<Object> {

    @Override
    public Map<String,Object> deserialize(JsonParser jsonParser, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        try {
            Map<String,Object> o = new HashMap<String,Object>();
            JsonDeserializer<Object> deserializer = ctxt.findRootValueDeserializer(
                    ctxt.getTypeFactory().constructMapType(Map.class, String.class, Object.class));
            deserializer.deserialize(jsonParser, ctxt, o);
            return o;
        } catch(java.lang.UnsupportedOperationException e) {
            return (HashMap<String, Object>) MapUtils.fromJson(jsonParser.getText());
        }
    }
}
