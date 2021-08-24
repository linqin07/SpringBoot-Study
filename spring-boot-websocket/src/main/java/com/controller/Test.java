package com.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.ValueNode;
import com.google.common.collect.ImmutableList;
import org.json.CDL;
import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @author: LinQin
 * @date: 2019/03/25
 */
@Controller
public class Test {
    public static void main(String[] args) throws IOException {

        String json = "{\n" +
                "    \"store\": {\n" +
                "        \"book\": [{\n" +
                "                \"category\": \"reference\",\n" +
                "                \"author\": \"Nigel Rees\",\n" +
                "                \"title\": \"Sayings of the Century\",\n" +
                "                \"price\": 8.95\n" +
                "            }\n" +
                "        ],\n" +
                "        \"book1\": [{\n" +
                "                \"category1\": \"reference\",\n" +
                "                \"author1\": \"Nigel Rees\",\n" +
                "                \"title1\": \"Sayings of the Century\",\n" +
                "                \"price1\": 8.95\n" +
                "            }\n" +
                "        ]\n" +
                "\n" +
                "    },\n" +
                "    \"expensive\": 10\n" +
                "}\n";

        String json1 = "[{\n" +
                "        \"category\": \"reference\",\n" +
                "        \"author\": \"Nigel Rees\",\n" +
                "        \"title\": \"Sayings of the Century\",\n" +
                "        \"book1\": [{\n" +
                "                \"category1\": \"reference\",\n" +
                "                \"author1\": \"Nigel Rees\",\n" +
                "                \"title1\": \"Sayings of the Century\",\n" +
                "                \"price1\": 8.95\n" +
                "            }\n" +
                "        ]\n" +
                "    }\n" +
                "]\n";

        HashMap<String, String> map = new HashMap<>();
//        System.out.println(U.formatJson(json));
        // 外面传入最长的
        addKeys("", new ObjectMapper().readTree(json), map, ImmutableList.of("store.book.author",
                "store.book.category", "store.book.title", "store.book.price"));

        System.out.println(Json2Csv(json1));
        System.out.println(map);
    }

    public static void addKeys(String currentPath, JsonNode jsonNode, Map<String, String> map, List<String> longPath) {
        if (jsonNode.isObject()) {
            ObjectNode objectNode = (ObjectNode) jsonNode;
            Iterator<Map.Entry<String, JsonNode>> iter = objectNode.fields();
            String pathPrefix = currentPath.isEmpty() ? "" : currentPath + ".";

            while (iter.hasNext()) {
                Map.Entry<String, JsonNode> entry = iter.next();
                addKeys(pathPrefix + entry.getKey(), entry.getValue(), map, longPath);
            }
        } else if (jsonNode.isArray()) {
            ArrayNode arrayNode = (ArrayNode) jsonNode;
            for (int i = 0; i < arrayNode.size(); i++) {
                addKeys(currentPath /*+ "[" + i + "]"*/, arrayNode.get(i), map, longPath);
            }
        } else if (jsonNode.isValueNode()) {
            ValueNode valueNode = (ValueNode) jsonNode;
            map.put(currentPath, valueNode.asText());
            if (longPath.contains(currentPath)) {
                // putAll 控制是否复制一份，默认要最长的才复制一份

            }
        }
    }

    @RequestMapping("/")
    public String index() {
        System.out.println("----------");
        return "index";
    }

    public static String Json2Csv(String json) throws JSONException {
        JSONArray jsonArray = new JSONArray(json);
        String csv = CDL.toString(jsonArray);
        return csv;
    }

}
