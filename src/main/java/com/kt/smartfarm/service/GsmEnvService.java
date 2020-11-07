package com.kt.smartfarm.service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface GsmEnvService {

	List<GsmEnvVO> gsmOfDeviceList(Long gsmKey);
	//GsmEnvVO get(Long gsmKey);
	Map<String,Object> get(Long gsmKey, boolean  all, Boolean isSmartfarmSystem);
	Integer delete(Long gsmKey);
	Long insert(GsmEnvVO gsmInfo);
	Integer update(GsmEnvVO gsmInfo);
	List<Map<String, Object>> list(boolean all, Integer userInfoId, Integer categoryId, String farmName, String authUserIdx, Boolean isSmartfarmSystem, Boolean hasCCTVOnly);
	List<GsmEnvVO> notMappedList();
	Integer userRegisterGSM(HashMap<String, Object> param, Long gsmKey);
	GsmConfigVO insertGSMConfig(GsmConfigVO gsmConfigVO);
	GsmConfigVO updateGSMConfig(GsmConfigVO gsmConfigVO);
	GsmConfigVO getGSMConfig(HashMap<String,Object> param);
	Integer copyToNewGsm(HttpServletRequest request, Long fromGsmKey, Long toGsmKey);
	Integer syncToSmartfarm(HttpServletRequest request, Long gsmKey);
	Integer syncToSupervisor(HttpServletRequest request);
	GsmEnvVO get(Long gsmKey, boolean forced);

	Integer autoSync(Long gmsKey, HttpServletRequest request);

}
