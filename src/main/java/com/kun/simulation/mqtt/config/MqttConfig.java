package com.kun.simulation.mqtt.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * mqtt配置信息
 * @author KUN
 * @since  2022/7/26
 **/
@Data
@Configuration
@ConfigurationProperties(prefix = "mqtt")
public class MqttConfig{
    /**
     *用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     *连接地址
     */
    private String hosturl;
    /**
     *客户端Id
     */
    private String clientID;
    /**
     * 默认主题
     */
    private String defaultTopic;
    /**
     *超时时间
     */
    private int timeout;
    /**
     * 保持连接数
     */
    private int keepalive;
    /**
     * 支持同时发送的消息数
     */
    private String maxInflight;
    /**
     * 是否清楚Session
     */
    private boolean clearSession;
    /**是否共享订阅*/
    private boolean isShared;
    /**分组共享订阅*/
    private boolean isSharedGroup;



}
