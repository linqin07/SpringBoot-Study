package com.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.constant.Sex;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author linqin
 * @since 2019-05-22
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class Typehandle extends Model<Typehandle> {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * name
     */
    private String name;

    /**
     * 性别
     */
    private Sex sex;

    /**
     * 配置，保存json格式
     */
    private List<User> config;

    /**
     * 年龄
     */
    private Integer age;

}
