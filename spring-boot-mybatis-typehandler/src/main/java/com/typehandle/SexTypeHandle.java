package com.typehandle;

import com.constant.Sex;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @Description:
 * @author: LinQin
 * @date: 2019/05/22
 */

@MappedTypes({Sex.class})
@MappedJdbcTypes(JdbcType.INTEGER)
public class SexTypeHandle implements TypeHandler<Sex> {
    @Override
    public void setParameter(PreparedStatement preparedStatement, int i, Sex sex, JdbcType jdbcType) throws SQLException {
        //设置第i个参数的值为传入sex的code值，preparedStatement为执行数据库操纵的对象
        //传值的时候是一个sex对象，但是当进行映射插入的时候就会转化为sex的code值进行存储
        preparedStatement.setInt(i, sex.getSexCode());
    }

    @Override
    public Sex getResult(ResultSet resultSet, String s) throws SQLException {
        //获取数据库存储的sex的code值
        int result = resultSet.getInt(s);
        return Sex.getSexFromCode(result);
    }

    @Override
    public Sex getResult(ResultSet resultSet, int i) throws SQLException {
        int result = resultSet.getInt(i);
        return Sex.getSexFromCode(result);
    }

    @Override
    public Sex getResult(CallableStatement callableStatement, int i) throws SQLException {
        int result = callableStatement.getInt(i);
        return Sex.getSexFromCode(result);
    }
}
