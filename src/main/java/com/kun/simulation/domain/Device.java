package com.kun.simulation.domain;

import com.alibaba.fastjson2.JSON;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.kun.simulation.annotation.device.Fie;
import com.kun.simulation.annotation.device.Func;
import lombok.Data;
import org.springframework.beans.factory.InitializingBean;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.*;

/**
 * 设备对象 iot_device
 * 
 * @author kerwincui
 * @date 2021-12-16
 */
@Data
public abstract class Device {

    private static final long serialVersionUID = 1L;

    /**
     * 设备属性map
     */
    private Map<String, Object> properties;

    /**
     * 设备方法map
     */
    private Map<String, Object> functions;


    /**
     * 方法执行Map
     */
    private Map<String, Method> functionActionMap;

    /**
     * remark  平台中告警、场景联动和定时下发的指令有备注信息
     */
    private Map<String, String> remarkMap;

    /** 产品ID */
    private Long productId;

    /** 产品名称 */
    private String productName;

    /** 用户ID */
    private Long userId;


    /** 设备编号 */
    private String serialNumber;

    /** 固件版本 */
    private Double firmwareVersion;

    /** 设备状态（1-未激活，2-禁用，3-在线，4-离线） */
    private Integer status;

    /** wifi信号强度（信号极好4格[-55— 0]，信号好3格[-70— -55]，信号一般2格[-85— -70]，信号差1格[-100— -85]） */
    private Integer rssi;

    /** 设备所在地址 */
    private String networkAddress;

    /** 设备入网IP */
    private String networkIp;

    /** 设备经度 */
    private Double longitude;

    /** 设备纬度 */
    private Double latitude;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /** 是否自定义位置 **/
    private Integer locationWay;

    /**
     * 设备摘要
     **/
    private Map<String, String> summary;

    /**
     * 设备是否在线
     */
    private boolean online;

    public Device(Long productId,String serialNumber, Long userId,  Double firmwareVersion, Integer rssi) {
        this.productId = productId;
        this.userId = userId;
        this.serialNumber = serialNumber;
        this.firmwareVersion = firmwareVersion;
        this.status =3 ;
        this.rssi = rssi;
        afterPropertiesSet();
    }

    public void afterPropertiesSet() {
        try {
            initSummary();
            initRemarkMap();
            initProperties();
            initFunctions();
            initFunctionActionMap();
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }

    }

    public void initProperties() throws IllegalAccessException {
        Class clazz = this.getClass();
        Field[] fields = clazz.getFields();
        for (Field field : fields) {
            if (field.getAnnotation(Fie.class) != null) {
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
    }
    public List<Map<String, Object>> getProps(String... fieldNames) throws IllegalAccessException {
        initProperties();
        List<Map<String, Object>> result = new ArrayList<>();
        for (String fieldName : fieldNames) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", fieldName);
            map.put("value", properties.get(fieldName));
            map.put("remark", remarkMap.get(fieldName));
            result.add(map);
        }
        return result;
    }
    public void initFunctions() throws IllegalAccessException {
        Class clazz = this.getClass();
        Field[] fields = clazz.getFields();
        for (Field field : fields) {
            if (field.getAnnotation(Func.class) != null) {
                Map<String, Object> functions = getFunctions();
                if (functions == null) {
                    functions = new HashMap<>();
                    setFunctions(functions);
                }
                String fieldName = field.getName();
                Object fieldValue = field.get(this);
                functions.put(fieldName, fieldValue);
            }
        }
    }
    public List<Map<String, Object>> getFuncs(String... fieldNames) throws IllegalAccessException {
        initFunctions();
        List<Map<String, Object>> result = new ArrayList<>();
        for (String fieldName : fieldNames) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", fieldName);
            map.put("value", functions.get(fieldName));
            map.put("remark", remarkMap.get(fieldName));
            result.add(map);
        }
        return result;
    }
    public void initFunctionActionMap() throws NoSuchMethodException {
        Class clazz = this.getClass();
        Field[] fields = clazz.getFields();
        Method defaultActionMethod = clazz.getDeclaredMethod("defaultAction");
        for (Field field : fields) {
            if (field.getAnnotation(Func.class) != null) {
                Map<String, Method> functionActionMap = getFunctionActionMap();
                if (functionActionMap == null) {
                    functionActionMap = new HashMap<>();
                    setFunctionActionMap(functionActionMap);
                }
                Method method = clazz.getDeclaredMethod(field.getName() + "Action", Object.class);
                functionActionMap.put(field.getName(), method);
                if (method == null) {
                    functionActionMap.put(field.getName(), defaultActionMethod);
                }
            }
        }
    }

    public void funcAction(Map<String, Object>fun) throws InvocationTargetException, IllegalAccessException {
        Iterator<Map.Entry<String, Object>> iterator = fun.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Object> entry = iterator.next();
            Method action = functionActionMap.get(entry.getKey());
            action.invoke(entry.getValue());
        }
    }

    /**
     *
     * @return 设备信息
     */
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

    /**
     * 动作属性未指定执行动作 运行默认方法
     */
    public void defaultAction(Object obj) {

    }



    protected void checkStatus() {
        if (isOnline()) {
            if (checkOffline()){
                setStatus(4);//然后发布 info
            }
        } else {
            if (checkOnline()){
                setStatus(4);//然后发布 info 但是影子模式是通过 emqx钩子实现的 虽然设备有上下线 但是emxq一直在线 导致 设备影子值无法触发
            }
        }
    }

    public abstract void initSummary();
    public abstract void initRemarkMap();
    public abstract boolean checkOffline();
    public abstract boolean checkOnline();



}
