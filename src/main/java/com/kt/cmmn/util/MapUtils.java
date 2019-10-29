package com.kt.cmmn.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class MapUtils {
    public static Map<String, Object> camelizeMap(Map<String, Object> origin) {
        Map<String, Object> result = new HashMap<>();
        for(Map.Entry<String,Object> ent : origin.entrySet()) {
            Object value = ent.getValue();
            if( value instanceof List) {
                for( int i = 0; i < ((List)ent.getValue()).size() ; i++) {
                    Object listItem = ((List)ent.getValue()).get(i);
                    if( listItem instanceof Map ) {
                        ((List)ent.getValue()).set(i, camelizeMap((Map<String, Object>)listItem));
                    }
                }
            }
            if( value instanceof  Map) {
                value = camelizeMap((Map<String, Object>)value);
            }
            if( ent.getKey() instanceof  String ) {
                result.put(StringUtil.camelize(ent.getKey(), true), value);
            } else {
                result.put(ent.getKey(), value);
            }
        }
        return result;
    }

    private static  ObjectMapper objMapper =  new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);
    public static Map<String, Object> fromJson(String json) {
        try {
            return  objMapper.readValue(json,  new TypeReference<HashMap<String, Object>>() {}  );
        } catch (IOException e) {
            log.error("ToJSON : {}", json, e);
            return new HashMap<>();
        }
    }
    public static String toJson(Map<String, Object>  mapData) {
        try {
            return objMapper.writeValueAsString(mapData);
        } catch (JsonProcessingException e) {
            log.error("ToJSON : {}", mapData, e);
            return null;
        }
    }
    public static  Map<String,Object> convertToMap(Object o) {
        return objMapper.convertValue(o, new TypeReference<HashMap<String, Object>>() {} );
    }
}
