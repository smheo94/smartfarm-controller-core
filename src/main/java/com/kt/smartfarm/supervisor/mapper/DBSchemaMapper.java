package com.kt.smartfarm.supervisor.mapper;

import egovframework.rte.psl.dataaccess.mapper.Mapper;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@Mapper("dbSchemaMapper")
public interface DBSchemaMapper {
    void schemaUpdate_20181129_1();
    void schemaUpdate_20181203_1();
    void schemaUpdate_20181204_1();
    List<Map<String,Object>> select_any_query(Map<String,Object> map);
    void update_any_query(Map<String,Object> map);
}
