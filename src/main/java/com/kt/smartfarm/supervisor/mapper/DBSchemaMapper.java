package com.kt.smartfarm.supervisor.mapper;

import egovframework.rte.psl.dataaccess.mapper.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper("dBSchemaMapper")
public interface DBSchemaMapper {
    void schemaUpdate_20181129_1();
    void schemaUpdate_20181203_1();
    void schemaUpdate_20181204_1();
}
