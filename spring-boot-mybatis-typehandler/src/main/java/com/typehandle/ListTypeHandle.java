package com.typehandle;

import com.alibaba.fastjson.JSON;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @Description: MappedTypes 使用注解后就是对全局的类型生效，针对 xml 中的 resultType 生效。
 * @author: LinQin
 * @date: 2019/05/22
 */

@MappedTypes({List.class})
@MappedJdbcTypes(value = {JdbcType.VARCHAR}, includeNullJdbcType = true)
public class ListTypeHandle extends BaseTypeHandler<List> {

    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, List list, JdbcType jdbcType) throws SQLException {
        preparedStatement.setString(i, JSON.toJSONString(list));
    }

    @Override
    public List getNullableResult(ResultSet resultSet, String s) throws SQLException {
        return JSON.parseArray(resultSet.getString(s));
    }

    @Override
    public List getNullableResult(ResultSet resultSet, int i) throws SQLException {
        // i 从1开始
        return JSON.parseArray(resultSet.getString(i));
    }

    @Override
    public List getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        return JSON.parseArray(callableStatement.getString(i));
    }
}
