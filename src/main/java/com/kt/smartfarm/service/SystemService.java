package com.kt.smartfarm.service;

import java.util.List;
import java.util.Map;

public interface SystemService {
    Map<String, Object> getAppVersion(String appName);

    List<Map<String,Object>> getAnyQueryResult(Map<String, Object> param);
}
