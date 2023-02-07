package com.kun.simulation.mqtt.config;

import com.kun.simulation.mqtt.bean.EmqxClient;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 配置客户端信息
 * @author KUN
 * @date 2022/7/26
 **/
@Data
@Configuration
public class MqttClientConfiguration {
    @Autowired
    private MqttConfig mqttConfig;

    @Bean
    public EmqxClient emqxClient() {
        EmqxClient client = new EmqxClient(mqttConfig.getHosturl(),
                mqttConfig.getTimeout(),
                mqttConfig.getKeepalive(),
                mqttConfig.getClientID(),
                mqttConfig.getUsername(),
                mqttConfig.getPassword(),
                mqttConfig.isClearSession(),
                mqttConfig.getMaxInflight());
        return client;

    }
}
