package egovframework.customize.service;

import javax.servlet.http.HttpServletRequest;

public interface AutoSyncService {

    Integer autoSync(Integer gmsKey, HttpServletRequest request);
}
