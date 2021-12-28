package com.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class JacksonUtil {
    private static Logger log = LoggerFactory.getLogger("JacksonUtil");
    private static ObjectMapper mapper = new ObjectMapper();

    /**
     * 对象转json
     *
     * @param object
     * @return
     */
    public static String toJsonString(Object object) {
        try {
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.error("...Err...Jackson转换字符串（String）过程失败：：：", e);
            e.printStackTrace();
        }
        return null;
    }

    /**
     * json转对象
     *
     * @param json
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T jsonToBean(String json, Class<T> clazz) throws IOException {
        try {
            return mapper.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            log.error("...Err...Jackson转换对象（Object）过程失败：：：", e);
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 字符串转换为List
     *
     * @param listStr
     * @param typeReference new TypeReference<List<Object>>() {}
     * @param <T>
     * @return
     */
    public static <T> T jsonToObjByTypeRf(String listStr, TypeReference<T> typeReference) throws IOException {
        try {
            return mapper.readValue(listStr, typeReference);
        } catch (JsonProcessingException e) {
            log.error("...Err...Jackson转换Object过程失败：：：", e);
            e.printStackTrace();
        }
        return null;
    }
}
