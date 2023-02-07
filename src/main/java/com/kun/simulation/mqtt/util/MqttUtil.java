package com.kun.simulation.mqtt.util;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * MQTT工具类
 * @author KUN
 * @date 2022/7/27
 **/
@Slf4j
public class MqttUtil{
    public static void pubTopic(Object obj, MqttAsyncClient client, String topic) throws MqttException {
        log.info("生成mqttMessage");
        String message = JSONObject.toJSONString(obj);
        MqttMessage mqttMessage = new MqttMessage();
        mqttMessage.setQos(1);
        mqttMessage.setPayload(message.getBytes());
        client.publish(topic, mqttMessage);
    }
}
