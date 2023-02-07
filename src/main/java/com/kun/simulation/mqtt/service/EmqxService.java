package com.kun.simulation.mqtt.service;


import java.util.List;

/**
 * @author KUN
 * @date 2022/7/26
 **/
public interface EmqxService {
    void pubAllTopic(List<String> lightList, List<String> acList, List<String> allFloorList, List<String> otherList, String allEle, String cei, String daping1,String daping2,List<String> floorAllPower);





}
