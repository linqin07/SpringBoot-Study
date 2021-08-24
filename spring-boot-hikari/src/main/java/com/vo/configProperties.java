package com.vo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Description:
 * author: LinQin
 * date: 2018/07/20
 */
@Component
public class configProperties {
    @Value("${com.hicard.key}")
    private String key;
    @Value("${com.hicard.value}")
    private String value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "configProperties{" +
                "key='" + key + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
