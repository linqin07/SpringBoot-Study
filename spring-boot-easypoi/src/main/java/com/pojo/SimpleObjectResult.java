package com.pojo;

import java.util.List;
import java.util.Map;

/**
 * @Description: es-sql查询结果二度封装返回
 * @author: LinQin
 * @date: 2018/10/29
 */
public class SimpleObjectResult {
    /**
     * 查询遍历记录数目;
     */
    private Long total;

    /**
     * 查询耗时毫秒数ms
     */
    private Long took;

    /**
     * 表头
     */
    private List<String> headers;

    /**
     * 数据
     */
    private List<Map<String, Object>> lines;

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Long getTook() {
        return took;
    }

    public void setTook(Long took) {
        this.took = took;
    }

    public List<String> getHeaders() {
        return headers;
    }

    public void setHeaders(List<String> headers) {
        this.headers = headers;
    }

    public List<Map<String, Object>> getLines() {
        return lines;
    }

    public void setLines(List<Map<String, Object>> lines) {
        this.lines = lines;
    }

    @Override
    public String toString() {
        return "SimpleObjectResult{" +
                "total=" + total +
                ", took=" + took +
                ", headers=" + headers +
                ", lines=" + lines +
                '}';
    }
}
