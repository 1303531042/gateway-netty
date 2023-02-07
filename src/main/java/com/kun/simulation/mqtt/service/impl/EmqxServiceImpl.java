package com.kun.simulation.mqtt.service.impl;


import com.kun.mqtt.bean.EmqxClient;
import com.kun.mqtt.service.EmqxService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;


/**
 * emqx服务类
     * @author KUN
 * @date 2022/7/26
 **/
@Slf4j
@Service
public class EmqxServiceImpl implements EmqxService {

    private List<String> lightTopic = new ArrayList<>();
    private List<String> acTopic = new ArrayList<>();
    private List<String> otherTopic = new ArrayList<>();
    private List<String> allFloorTopic = new ArrayList<>();
    private List<String> allFloorPowerTopic = new ArrayList<>();
    /**
     * 碳排放主题
     */
    private static final String CEI_TOPIC = "yn/info/power/carbon";
    /**
     * 研究院总用电主题
     */
    private static final String ALL_TOPIC = "yn/info/power/total";


    private static final String DAPING_1_TOPIC = "yn/info/power/f1_dp1";
    private static final String DAPING_2_TOPIC = "yn/info/power/f1_dp2";

    @Autowired
    private EmqxClient emqxClient;

    @PostConstruct
    public void init() {
        //电灯用电主题
        lightTopic.add("yn/info/power/f1_light");
        lightTopic.add("yn/info/power/f2_light");
        lightTopic.add("yn/info/power/f3_light");
        lightTopic.add("yn/info/power/f4_light");
        lightTopic.add("yn/info/power/f5_light");
        //空调用电主题
        acTopic.add("yn/info/power/f1_air");
        acTopic.add("yn/info/power/f2_air");
        acTopic.add("yn/info/power/f3_air");
        acTopic.add("yn/info/power/f4_air");
        acTopic.add("yn/info/power/f5_air");
        //其他用电主题
        otherTopic.add("yn/info/power/f1_other");
        otherTopic.add("yn/info/power/f2_other");
        otherTopic.add("yn/info/power/f3_other");
        otherTopic.add("yn/info/power/f4_other");
        otherTopic.add("yn/info/power/f5_other");
        //总用电主题
        allFloorTopic.add("yn/info/power/f1_total");
        allFloorTopic.add("yn/info/power/f2_total");
        allFloorTopic.add("yn/info/power/f3_total");
        allFloorTopic.add("yn/info/power/f4_total");
        allFloorTopic.add("yn/info/power/f5_total");
        //功率主题
        allFloorPowerTopic.add("yn/info/power/f1_power");
        allFloorPowerTopic.add("yn/info/power/f2_power");
        allFloorPowerTopic.add("yn/info/power/f3_power");
        allFloorPowerTopic.add("yn/info/power/f4_power");
        allFloorPowerTopic.add("yn/info/power/f5_power");

    }

    /**
     * 发布所有楼层对应的电器的主题消息 到HA
     */

    @Override
    public void pubAllTopic(List<String> lightList, List<String> acList, List<String> floorAllEle, List<String> floorOtherEleList, String allEle, String cei, String daping1, String daping2, List<String> floorAllPower) {
        for (int i = 0; i < 5; i++) {
            if (lightList != null) {
                emqxClient.publish(1, lightTopic.get(i), lightList.get(i));
            }
            if (acList != null) {
                emqxClient.publish(1, acTopic.get(i), acList.get(i));
            }
            if (floorOtherEleList != null) {
                emqxClient.publish(1, otherTopic.get(i), floorOtherEleList.get(i));
            }
            if (floorAllEle != null) {
                emqxClient.publish(1, allFloorTopic.get(i), floorAllEle.get(i));
            }
            if (floorAllPower != null) {
                emqxClient.publish(1, allFloorPowerTopic.get(i), floorAllPower.get(i));
            }
        }
        if (cei != null) {
            emqxClient.publish(1, CEI_TOPIC, cei);
        }
        if (allEle != null) {
            emqxClient.publish(1, ALL_TOPIC, allEle);
        }
        if (daping1 != null) {
            emqxClient.publish(1, DAPING_1_TOPIC, daping1);
        }
        if (daping2 != null) {
            emqxClient.publish(1, DAPING_2_TOPIC, daping2);
        }
    }






}
