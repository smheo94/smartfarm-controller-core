package com.kt.smartfarm.service;

import com.kt.cmmn.util.AuthorityChecker;

import java.util.List;

public interface AuthCheckService {
	Boolean authCheck(Long gsmKey, Long houseId);

    Boolean authCheck(Long gsmKey, Long houseId, List<Long>gsmKeyList, List<Long>houseIdList);

    String getAuthUserIdx();
	AuthorityChecker getAuthCheck();
}
