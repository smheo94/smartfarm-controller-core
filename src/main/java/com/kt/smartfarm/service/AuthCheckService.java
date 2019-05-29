package com.kt.smartfarm.service;

import java.util.List;

public interface AuthCheckService {
	Boolean authCheck(Integer gsmKey, Integer houseId);
	String getAuthUserIdx();
}
