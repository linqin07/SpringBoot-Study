package com.controller;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.service.KafkaManageServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @author: LinQin
 * @date: 2019/02/26
 */
@RestController
public class KafkaController {
    @Autowired
    private KafkaManageServices kafkaManageServices;

    @RequestMapping("/getData")
    public List<String> getData(String topic) {
        List<String> topicData = kafkaManageServices.getTopicData(topic, 2);
        topicData.forEach(System.out::println);
        return topicData;
    }

    @RequestMapping("/setData")
    public void setData() {
        List<Map<String, Object>> data = Lists.newArrayList();
        Map<String, Object> map = Maps.newHashMap();
        map.put("1", "dsfsd1");
        map.put("2", "dsfsd2");
        data.add(map);

        kafkaManageServices.setTopicData("linqin", data);
    }
}
