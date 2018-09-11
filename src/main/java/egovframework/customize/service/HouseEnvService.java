package egovframework.customize.service;

import java.util.HashMap;
import java.util.List;

public interface HouseEnvService {	
	HouseEnvVO insert( HouseEnvVO house);
	Integer delete(String gsmKey, Integer greenHouseId);
	HouseEnvVO update(HouseEnvVO house);
//	HashMap<String,Object>  get(String gsmKey, Integer greenHouseId);
	List<HashMap<String, Object>> list(String gsmKey);
	
	List<HashMap<String,Object>> selectHouseTypeList();
	
	List<ProductVO> selectProductList();
	HashMap<String,Object> insertHouseDeviceMap(HashMap<String, Object> map);
}
