package com.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * Description:
 * author: LinQin
 * date: 2018/07/19
 */
@Getter
@Setter
@ToString
public class ApiResult implements Serializable {
    //消息提示
    private String msg;
    //状态信息
    private boolean flag = true;
    //返回结构
    private Object result;
    //查询出的结构总数
    private int rows;
    //需要跳转的路径
    private String jumpUrl;
    //接口相应时间毫秒单位
    private long time;

    //禁止new创建对象
    private ApiResult() {
    }

    /**
     * 统一创建ApiResultBean对象
     * 方便后期扩展
     *
     * @return
     */
    public static ApiResult newInstance() {
        return new ApiResult();
    }
}
