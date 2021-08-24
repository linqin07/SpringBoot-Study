package com.typehandle;

/**
 * @Description: 转出处理后的对象取出来的属性都是 linkedhashmap 类型，针对泛型 List<T>
 * @author: LinQin
 * @date: 2020/07/05
 */

import com.entity.User;
import com.fasterxml.jackson.databind.ObjectMapper;
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
 * 使用的时候，感觉没多大用
 * POJO pojo=objectMapper.convertValue(复杂对象里的linkedhashmap字符串,new TypeReference<POJO>(){});
 * @param <T>
 */
@MappedTypes(value = User.class)
@MappedJdbcTypes(value = {JdbcType.VARCHAR}, includeNullJdbcType = true)
public class ArrayListTypeHandler<T extends Object> extends BaseTypeHandler<List<T>> {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private Class<List<T>> clazz;

    public ArrayListTypeHandler(Class<List<T>> clazz) {
        if (clazz == null) {
            throw new IllegalArgumentException("Type argument cannot be null");
        }
        this.clazz = clazz;
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, List<T> parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, toJson(parameter));
    }

    @Override
    public List<T> getNullableResult(ResultSet rs, String columnName) throws SQLException {
    //特别要注意在转集合的情况下，第二个参数是List.class,而不是clazz会报奇怪的错误，我在这被坑了老久
        return toObject(rs.getString(columnName), List.class);
    }

    @Override
    public List<T> getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return toObject(rs.getString(columnIndex), List.class);
    }

    @Override
    public List<T> getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return toObject(cs.getString(columnIndex), List.class);
    }

    private String toJson(List<T> object) {
        try {
            return MAPPER.writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private List<T> toObject(String content, Class<?> clazz) {
        if (content != null && !content.isEmpty()) {
            try {
                List<T> readValue = (List<T>) MAPPER.readValue(content, clazz);
//                for (T t : readValue) {
//                    MAPPER.convertValue(t, clazz)
//                }

//                MAPPER.convertValue()
                return readValue;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            return null;
        }
    }
}