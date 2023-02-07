package com.kun.simulation.domain;

import com.alibaba.fastjson2.JSON;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.beans.factory.InitializingBean;

import javax.annotation.PostConstruct;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.*;

/**
 * 设备对象 iot_device
 * 
 * @author kerwincui
 * @date 2021-12-16
 */
@Data
public abstract class Device implements InitializingBean {

    private static final long serialVersionUID = 1L;

    /**
     * 设备属性map
     */
    private Map<String, Object> properties;

    /** 产品ID */
    private Long productId;

    /** 产品名称 */
    private String productName;

    /** 用户ID */
    private Long userId;


    /** 设备编号 */
    private String serialNumber;

    /** 固件版本 */
    private BigDecimal firmwareVersion;

    /** 设备状态（1-未激活，2-禁用，3-在线，4-离线） */
    private Integer status;

    /** wifi信号强度（信号极好4格[-55— 0]，信号好3格[-70— -55]，信号一般2格[-85— -70]，信号差1格[-100— -85]） */
    private Integer rssi;

    /** 设备影子 */
    private Integer isShadow;

    /** 设备所在地址 */
    private String networkAddress;

    /** 设备入网IP */
    private String networkIp;

    /** 设备经度 */
    private BigDecimal longitude;

    /** 设备纬度 */
    private BigDecimal latitude;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /** 激活时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date activeTime;

    private String thingsModelValue;

    /** 是否自定义位置 **/
    private Integer locationWay;

    /**
     * 设备摘要
     **/
    private Map<String, String> summary;



    @Override
    public void afterPropertiesSet() throws Exception {
        initSummary();
        initProperties();
    }

    public abstract void initSummary();


    public void initProperties() throws IllegalAccessException {
        Class clazz = this.getClass();
        Field[] fields = clazz.getFields();
        for (Field field : fields) {
            Map<String, Object> properties = getProperties();
            if (properties == null) {
                properties = new HashMap<>();
                setProperties(properties);
            }
            String fieldName = field.getName();
            Object fieldValue = field.get(this);
            properties.put(fieldName, fieldValue);
        }
    }

    public String info() {
        Map<String, Object> infoMap = new HashMap<>();
        infoMap.put("rssi", rssi);
        infoMap.put("status", status);
        infoMap.put("firmwareVersion", firmwareVersion);
        infoMap.put("userId", userId);
        infoMap.put("longitude", longitude);
        infoMap.put("latitude", latitude);
        infoMap.put("summary", summary);
        return JSON.toJSONString(infoMap);
    }

}
