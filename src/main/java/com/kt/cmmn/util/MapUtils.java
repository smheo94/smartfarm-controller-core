package com.kt.cmmn.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
                result.put(StringUtil.camelize((String)ent.getKey(), true), value);
            } else {
                result.put(ent.getKey(), value);
            }
        }
        return result;
    }
}
