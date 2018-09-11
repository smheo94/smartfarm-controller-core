package egovframework.customize.service;

import java.util.List;

public interface CategoryEnvService {	
	CategoryEnvVO insert( CategoryEnvVO vo);	
	CategoryEnvVO update(CategoryEnvVO vo);	
	List<CategoryEnvVO> list();
	CategoryEnvVO selectCategoryDetail(Integer categoryId);
	Integer delete(Integer categoryId);
}
