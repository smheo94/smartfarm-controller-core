package com.kt.smartfarm.service;

import javax.servlet.http.HttpServletRequest;

public interface AutoSyncService {
    Integer autoSync(Long gmsKey, HttpServletRequest request);
}
