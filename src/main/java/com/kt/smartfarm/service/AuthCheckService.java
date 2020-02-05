package com.kt.smartfarm.service;

import com.kt.cmmn.util.AuthorityChecker;

import java.util.List;

public interface AuthCheckService {
	Boolean authCheck(Integer gsmKey, Integer houseId);

    Boolean authCheck(Integer gsmKey, Integer houseId, List<Integer> gsmKeyList, List<Integer> houseIdList);

    String getAuthUserIdx();
	AuthorityChecker getAuthCheck();
}
