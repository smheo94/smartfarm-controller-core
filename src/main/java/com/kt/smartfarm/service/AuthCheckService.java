package com.kt.smartfarm.service;

import com.kt.cmmn.util.AuthorityChecker;

import java.util.List;

public interface AuthCheckService {
	Boolean authCheck(Integer gsmKey, Integer houseId);
	String getAuthUserIdx();
	AuthorityChecker getAuthCheck();
}
