package com.util;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.pojo.EventBean;
import com.pojo.User;

import java.lang.reflect.Field;

/**
 * @Description: 生成对应结果集映射
 *
 * @Results({
 * @Result(property = "userId", column = "USER_ID"),
 * @Result(property = "userAge", column = "USER_AGE"),
 * @Result(property = "userName", column = "USER_NAME"),
 * })
 * @author: LinQin
 * @date: 2019/01/02
 */
public class MybatisUtil {
    /**
     * 1.用于获取结果集的映射关系
     */
    public static String getResultsStr(Class origin) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("@Results({\n");
        for (Field field : origin.getDeclaredFields()) {
            String property = field.getName();
            //映射关系：对象属性(驼峰)->数据库字段(下划线)
            String column = new PropertyNamingStrategy.SnakeCaseStrategy().translate(field.getName()).toUpperCase();
            stringBuilder.append(String.format("@Result(property = \"%s\", column = \"%s\"),\n", property, column));
        }
        stringBuilder.append("})");
        return stringBuilder.toString();
    }

    public static String getJson(Class origin) throws Exception {
        Object newInstance = origin.newInstance();
        Field[] declaredFields = newInstance.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
        }
        return null;
    }

    /**
     * 1.用于获取结果集的映射关系
     */
    public static String getResultsStr1(Class origin) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<resultMap id=\"" + origin.getSimpleName() + "ResultMap\" type=\"")
                .append(origin.getName()).append("\" >")
                .append("\n");
        for (Field field : origin.getDeclaredFields()) {
            String property = field.getName();
            //映射关系：对象属性(驼峰)->数据库字段(下划线)
            String column = new PropertyNamingStrategy.SnakeCaseStrategy().translate(field.getName());
            stringBuilder.append(String.format("     <result column=\"%s\" property=\"%s\" />\n", column, property));
        }
        stringBuilder.append("</resultMap>");
        return stringBuilder.toString();
    }

    /**
     * 用于生成万能 Mapper 对应 xml 文件 里面的万能 condition
     * @param origin 类
     * @param paramName @params("condition") 这个名称
     * @return
     */
    public static String getCondition(Class origin, String paramName) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("    <sql id=\"condition\">")
                .append("\n")
                .append("            <trim prefix=\"WHERE\" prefixOverrides=\"AND |OR \">\n");
        for (Field field : origin.getDeclaredFields()) {
            String property = field.getName();
            if ("serialVersionUID".equalsIgnoreCase(property)) continue;

            //映射关系：对象属性(驼峰)->数据库字段(下划线)
            String column = new PropertyNamingStrategy.SnakeCaseStrategy().translate(field.getName());
            Class type = field.getType();
            if ("String".equals(type.getSimpleName())) {
                stringBuilder.append(String.format("           <if test=\"%s.%s != null and %s.%s != ''\">\n", paramName, property, paramName, property));
                stringBuilder.append(String.format("                and `%s` = #{condition.%s, jdbcType=VARCHAR}\n", column, property));
            } else {
                stringBuilder.append(String.format("            <if test=\"%s.%s != null\">\n", paramName, property));
                stringBuilder.append(String.format("                and `%s` = #{condition.%s}\n", column, property));
            }
            stringBuilder.append("            </if>\n");
        }
        stringBuilder.append("        </trim>\n");
        stringBuilder.append("    </sql>");
        return stringBuilder.toString();
    }

    public static void main(String[] args) throws Exception {
        String json = getJson(EventBean.class);

        String resultsStr = getResultsStr(User.class);
        System.out.println(resultsStr);
        System.out.println("--------------");
        System.out.println(getResultsStr1(User.class));
        System.out.println("--------------");
        System.out.println(getCondition(User.class, "condition"));
    }
}
