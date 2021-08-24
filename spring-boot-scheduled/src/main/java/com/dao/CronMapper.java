package com.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @Description:
 * @author: LinQin
 * @date: 2019/02/15
 */
@Mapper
public interface CronMapper {

    @Select("SELECT * FROM cron")
    String getCron();

}
