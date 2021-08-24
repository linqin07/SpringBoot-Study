package com.dao;

import com.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    int deleteByPrimaryKey(Integer userId);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer userId);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    /**
     * 注解使用多个参数
     * 不使用@param注解时，使用param1、param2、paramn...
     * 使用@param("name")才可以使用#{name}
     * <p>
     * 单个参数时用什么都可以。
     * 同时要开启，驼峰转化。
     *
     * @param name
     * @param id
     * @return
     */
    @Select("select * from t_user where user_name = #{param1} and user_id = #{param2}")
    User selectByNameAndId(String name, int id);
}