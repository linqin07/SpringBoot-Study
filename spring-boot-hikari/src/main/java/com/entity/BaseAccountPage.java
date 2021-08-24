package com.entity;

import java.io.Serializable;

/**
 * Description:
 * author: LinQin
 * date: 2018/07/10
 */
public class BaseAccountPage implements Serializable {
    /**
     * 默认页码
     */
    private int page = 1;
    /**
     * 分页数量
     */
    private int size = 10;

    /**
     * 排序名称
     */
    private String index = "name";

    /**
     * <p>
     * 排序正序
     * <p>
     */
    protected String sord = "asc";

    public String getSord() {
        return sord;
    }

    public void setSord(String sord) {
        this.sord = sord;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    @Override
    public String toString() {
        return "BaseAccountPage{" +
                "page=" + page +
                ", size=" + size +
                ", index='" + index + '\'' +
                '}';
    }
}
