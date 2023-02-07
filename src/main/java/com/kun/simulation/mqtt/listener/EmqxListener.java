package com.kun.simulation.mqtt.listener;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.stereotype.Component;

/**
 * Emqx监听类
 * @author KUN
 * @date 2022/7/26
 **/
@Slf4j
@Component
public class EmqxListener implements MqttCallbackExtended {



    /**
     * 监听mqtt连接消息
     * @param reconnect
     * @param serverURI
     */
    @Override
    public void connectComplete(boolean reconnect, String serverURI) {

//        log.info("mqtt已经连接！！");
        //连接后，可以在此做初始化事件，或订阅
//        try {
//            //订阅主题
//            emqxService.subscribe(EmqxClient.client);
//            //发布主题
//            emqxService.pubTopics(EmqxClient.client);
//        } catch (MqttException e) {
//            log.error("======>>>>>订阅主题失败 error={}",e.getMessage());
//        }
    }

    /**
     *当与服务器的连接丢失时调用此方法。
     */
    @Override
    public void connectionLost(Throwable cause) {
//        log.info("mqtt断开连接--");

    }

    /**
     * 当该客户端订阅的主题发送到emqx会触发该方法
     * @param topic 主题
     */
    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
//        emqxService.subscribeCallback(topic, message);
    }

    /**
     * 该客户端发布一个主题消息 当消息到达emq后触发方法
     */
    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {

    }
}
