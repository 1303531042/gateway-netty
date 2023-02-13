package com.kun.simulation.factory;

import com.kun.simulation.annotation.device.ProductID;
import com.kun.simulation.properties.config.DeviceYmlConfig;
import com.kun.simulation.properties.BaseDeviceProperties;
import com.kun.simulation.util.ClassUtils;
import com.kun.simulation.util.StringUtils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @author KUN
 * @date 2023/2/9
 * @describe  设备信息工厂
 **/
@Data
@Slf4j
@Component
public class PropertiesFactory implements InitializingBean {
    @Autowired
    private Map<String, Object> deviceYmlMap;

    /**
     * yml内容
     * key: 产品id
     * value：map key: valueName
     *           value: 值
     */
    private Map<String, Object> devicesForProductIdMap;
    /**
     * key: 产品id
     * value：产品propertiesClass对象
     */
    private Map<String, Class<?>> productIDtoDevicePropertiesClassMap;

    /**
     * key: 产品id
     * value: DeviceProperties对象数组
     */
    private Map<String, List<BaseDeviceProperties>> productIDtoBaseDevicePropertiesListMap = new HashMap<>();

    @Override
    public void afterPropertiesSet() throws Exception {
        initProductIDtoDevicePropertiesMap();
        initProductIDtoBaseDevicePropertiesListMap();

    }

    /**
     * 初始化initProductIDtoDevicePropertiesMap
     */
    private void initProductIDtoDevicePropertiesMap() {
        List<Class<?>> classList = ClassUtils.getClasses("com.kun.simulation.properties");
        productIDtoDevicePropertiesClassMap = new HashMap<>();
        for (Class<?> clazz : classList) {
            ProductID productID = clazz.getAnnotation(ProductID.class);
            if (productID != null) {
                String id = String.valueOf(productID.Value());
                productIDtoDevicePropertiesClassMap.put(id, clazz);
            }
        }
    }
    /**
     * 初始化initProductIDtoBaseDevicePropertiesListMap
     */
    private void initProductIDtoBaseDevicePropertiesListMap() {
        devicesForProductIdMap = DeviceYmlConfig.devicesForProductIdMap;
        Iterator<Map.Entry<String, Object>> iterator = devicesForProductIdMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Object> entry = iterator.next();
            String productID = String.valueOf(entry.getKey());
            List<Map<String, Object>> deviceFieldList = (List<Map<String, Object>>) entry.getValue();
            createDeviceProperties(productID, deviceFieldList);
        }

    }

    /**
     * 初始化所有yml中的设备信息
     * 初始化productIDtoBaseDevicePropertiesListMap
     * @param productID
     * @param deviceFieldList
     */
    private void createDeviceProperties(String productID, List<Map<String, Object>> deviceFieldList) {
        Class<?> clazz = productIDtoDevicePropertiesClassMap.get(productID);
        if (clazz == null) {
            log.error("productID： " + productID + " deviceProperties未实现");
            return;
        }
        for (Map<String, Object> fieldMap : deviceFieldList) {
            if (!(fieldMap.containsKey("productId") && fieldMap.containsKey("serialNumber")
                    && fieldMap.containsKey("userId") && fieldMap.containsKey("firmwareVersion")
                    && fieldMap.containsKey("rssi"))) {
                log.error("该deviceProperties必要参数不足");
                continue;
            }


            try {
                Constructor constructor = clazz.getDeclaredConstructor();
                Object obj = null;
                try {
                    obj = constructor.newInstance();
                } catch (InstantiationException e) {
                    throw new RuntimeException(e);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                } catch (InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
                Iterator<Map.Entry<String, Object>> iterator = fieldMap.entrySet().iterator();
                Method[] methods = clazz.getMethods();
                while (iterator.hasNext()) {
                    Map.Entry<String, Object> entry = iterator.next();
                    String fieldName = entry.getKey();
                    Object fieldValue = entry.getValue();
                    String setMethodName = "set" + StringUtils.captureName(fieldName);
                    for (Method method : methods) {
                        if (method.getName().equals(setMethodName)) {
                            method.invoke(obj, fieldValue);
                        }
                    }
                }

                List<BaseDeviceProperties> baseDeviceProperties = productIDtoBaseDevicePropertiesListMap.computeIfAbsent(productID, k -> new ArrayList<>());
                baseDeviceProperties.add((BaseDeviceProperties) obj);

            } catch (NoSuchMethodException e) {
                log.error(clazz.getName() + " 为实现无参构造参数");
                break;
            } catch (InvocationTargetException e) {
                log.error("field value造型异常");
            } catch (IllegalAccessException e) {
                log.error("field value造型异常");
            }
        }

    }
}
