package com.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.entity.Typehandle;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author linqin
 * @since 2019-05-22
 */
@Mapper

public interface TypehandleMapper extends BaseMapper<Typehandle> {
    Typehandle getWithId(@Param("id") Integer id);
}
