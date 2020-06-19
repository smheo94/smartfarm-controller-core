package com.kt.cmmn.util;


import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;
import org.codehaus.jackson.map.JsonMappingException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MapJsonOldDeserializer extends JsonDeserializer<Object> {

    @Override
    public Map<String,Object> deserialize(JsonParser jsonParser, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        try {
            Map<String,Object> o = new HashMap<String,Object>();
            JsonDeserializer<Object> deserializer  = ctxt.getDeserializerProvider().findValueDeserializer(ctxt.getConfig(), ctxt.getTypeFactory().constructMapType(Map.class, String.class, Object.class),
                    null);
            deserializer.deserialize(jsonParser, ctxt, o);
            return o;
        } catch(UnsupportedOperationException | JsonMappingException e) {
            return (HashMap<String, Object>) MapUtils.fromJson(jsonParser.getText());
        }
    }
}
