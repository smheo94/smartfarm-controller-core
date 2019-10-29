package com.kt.cmmn.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

@Slf4j
public class JsonTypeHandler extends BaseTypeHandler<Object> {
    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, Object o, JdbcType jdbcType) throws SQLException {
        String p = MapUtils.toJson((Map<String,Object>)o);
        preparedStatement.setObject(i, p);
    }

    @Override
    public Object getNullableResult(ResultSet resultSet, String s) throws SQLException {
        Object d = resultSet.getObject(s);
        if( d == null ) return d;
        return MapUtils.fromJson((String)d);
    }

    @Override
    public Object getNullableResult(ResultSet resultSet, int i) throws SQLException {
        Object d = resultSet.getObject(i);
        if( d == null ) return d;
        return MapUtils.fromJson((String)d);
    }

    @Override
    public Object getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        final Object d = callableStatement.getObject(i);
        if( d == null ) return d;
        return MapUtils.fromJson((String)d);
    }
}
