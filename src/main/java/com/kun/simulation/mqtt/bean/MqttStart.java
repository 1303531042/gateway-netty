package com.kun.simulation.mqtt.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * MQTT客户端启动类
 * @author KUN
 * @date 2022/7/26
 **/
@Component
@Order(value = 1)
public class MqttStart implements ApplicationRunner {

    @Autowired
    private EmqxClient emqxClient;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        emqxClient.connect();
    }

}
