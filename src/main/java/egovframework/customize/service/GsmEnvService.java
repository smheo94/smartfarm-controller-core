package egovframework.customize.service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface GsmEnvService {

	List<Map<String,Object>> gsmOfDeviceList(Integer gsmKey);

	//GsmEnvVO get(Integer gsmKey);

	Map<String,Object> get(Integer gsmKey, boolean  all, Boolean isSmartfarmSystem);

	Integer delete(Integer gsmKey);

	Integer insert(GsmEnvVO gsmInfo);

	Integer update(GsmEnvVO gsmInfo);

	List<Map<String, Object>> list(boolean all, Integer userInfoId, Integer categoryId, String farmName, String authUserIdx, Boolean isSmartfarmSystem);

	List<GsmEnvVO> notMappedList();

	Integer userRegistGSM(HashMap<String, Object> param, Integer gsmKey);

	List<GsmEnvVO> getGsmInfoByUser(Integer userInfoId);

	GsmThresholdVO gsmThresholdInsert(GsmThresholdVO gsmThresholdVO);
	GsmThresholdVO gsmThresholdUpdate(GsmThresholdVO gsmThresholdVO);
	GsmThresholdVO gsmThresholdGet(HashMap<String,Object> param);


	Integer copyToNewGsm(HttpServletRequest request, Integer fromGsmKey, Integer toGsmKey);

	Integer syncToSmartfarm(HttpServletRequest request, Integer gsmKey);
}
