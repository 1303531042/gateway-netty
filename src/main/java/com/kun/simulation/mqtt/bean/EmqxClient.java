package com.kun.simulation.mqtt.bean;


import com.kun.simulation.mqtt.listener.EmqxListener;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * @author KUN
 * @date 2022/7/26
 **/
@Data
@Slf4j
public class EmqxClient {

    @Autowired
    private EmqxListener emqxListener;

    /**MQTT异步客户端*/
    public static MqttAsyncClient client;

    /**连接配置*/
    private MqttConnectOptions options;




    /**服务器地址url*/
    private String hostname;

    /**超时时间*/
    private int timeout;

    /**包活时间*/
    private int keepalive;

    /**客户端唯一ID*/
    private String clientId;

    /**用户名*/
    private String username;

    /**密码*/
    private String password;

    /**是否清除会话*/
    private boolean clearSession;
    /**
     * 支持同时发送的消息数
     */
    private String maxInflight;

    public EmqxClient(String hostname, int timeout, int keepalive, String clientId, String username, String password, boolean clearSession,String maxInflight) {
        this.hostname = hostname;
        this.timeout = timeout;
        this.keepalive = keepalive;
        this.clientId = clientId;
        this.username = username;
        this.password = password;
        this.clearSession = clearSession;
        this.maxInflight = maxInflight;
    }

    /**
     * 连接Emqx
     */
    public synchronized void connect() {
        //初始化mqtt配置
        if (options == null) {
            setOptions();
        }
        //验证客户端是否存在
        if (client == null) {
            createClient();
        }
        while (!client.isConnected()) {
            try {
                //异步非阻塞
                IMqttToken token = client.connect(options);
                //同步
                token.waitForCompletion();
            } catch (Exception e) {
                log.error
                        ("=====>>>>>mqtt连接失败 message={}",e.getMessage());
                e.printStackTrace();
            }

        }
    }


    /**
     *设置连接属性
     */
    private void setOptions() {
        if (options != null) {
            options = null;
        }
        options = new MqttConnectOptions();
        options.setUserName(this.username);
        options.setPassword(this.password.toCharArray());
        options.setConnectionTimeout(this.timeout);
        options.setKeepAliveInterval(this.keepalive);
        options.setAutomaticReconnect(true);
        options.setCleanSession(this.clearSession);
        options.setMaxInflight(Integer.parseInt(maxInflight));
        log.info("======》设置mqtt参数成功");

    }

    /**
     *创建客户端
     */
    private void createClient() {
        if (client == null) {
            try {
                client = new MqttAsyncClient(hostname, clientId, new MemoryPersistence());
                //设置监听器
                client.setCallback(emqxListener);
                log.info("============>mqtt客户端启动成功");
            } catch (MqttException e) {
                log.error("mqtt客户端连接错误 error={}",e.getMessage());
                throw new RuntimeException(e);
            }
        }

    }

    /**
     * 断开mqtt的连接
     */
    public synchronized void disconnect() {
        if (client != null && client.isConnected()) {
            try {
                IMqttToken token = client.disconnect();
                token.waitForCompletion();
            }catch (MqttException e){
                log.error("====>>>>断开mqtt连接发生错误 message={}",e.getMessage());
            }
        }
        client = null;
    }

    /**
     * 重新连接Mqtt
     */
    public synchronized void refresh() {
        disconnect();
        setOptions();
        createClient();
        connect();
    }

    /**
     *  发布主题消息
     * @param qos
     * @param topic
     * @param pushMessage
     */
    public void publish(int qos, String topic, String pushMessage) {
        MqttMessage mqttMessage = new MqttMessage();
        mqttMessage.setQos(qos);
        mqttMessage.setPayload(pushMessage.getBytes());

        try {
            if (client.isConnected()) {
                IMqttToken iMqttToken = client.publish(topic, mqttMessage);
            } else {
//                log.error("=======>>>>>发布主题时发生错误 客户端未连接");

            }
        } catch (MqttPersistenceException e) {
            throw new RuntimeException(e);
        } catch (MqttException e) {
//            log.error("=======>>>>>发布主题时发生错误 topic={},message={}",topic,e.getMessage());
        }
    }



    /**
     * 订阅了某个主题
     * @param topic
     * @param qos9+9++
     *
     *
     *
     *
     *
     *
     *
     *
     */
    public void subscribe(String topic, int qos) {
        log.info("===========》订阅了主题 topic={}", topic);
        try {
            IMqttToken iMqttToken = client.subscribe(topic, qos);
        } catch (MqttException e) {
            log.error("=======>>>>>订阅主题 topic={} 失败 message={}",topic,e.getMessage());
        }
    }



}
