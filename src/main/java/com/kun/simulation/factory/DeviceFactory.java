package com.kun.simulation.factory;

import com.kun.simulation.DeviceManger;
import com.kun.simulation.ProductProperties;
import com.kun.simulation.annotation.device.ProductID;
import com.kun.simulation.factory.PropertiesFactory;
import com.kun.simulation.properties.BaseDeviceProperties;
import com.kun.simulation.util.ClassUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author KUN
 * @date 2023/2/8
 * @describe 设备工厂
 **/
@Component
public class DeviceFactory implements InitializingBean {

    @Autowired
    private DeviceManger deviceManger;

    @Autowired
    private PropertiesFactory propertiesFactory;




    @Override
    public void afterPropertiesSet() throws Exception {

    }

    /**
     * 实例化所有设备
     */
    public void instanceDevcies() {
        Map<String, List<BaseDeviceProperties>> map = propertiesFactory.getProductIDtoBaseDevicePropertiesListMap();
        Iterator<Map.Entry<String, List<BaseDeviceProperties>>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, List<BaseDeviceProperties>> entry = iterator.next();
            String productID = entry.getKey();
            List<BaseDeviceProperties> propertiesList = entry.getValue();
            for (BaseDeviceProperties prop : propertiesList) {
                Class<?> deviceClass = propertiesFactory.getDeviceClass(prop.getClass());
                Method[] properMethod = prop.getClass().getMethods();
                Method[] deviceMethod = deviceClass.getMethods();
                try {
                    Constructor constructor = deviceClass.getConstructor(Long.class, String.class, Long.class, Double.class, Integer.class);
                    constructor.newInstance()
                } catch (NoSuchMethodException e) {
                    throw new RuntimeException(e);
                } catch (InvocationTargetException e) {
                    throw new RuntimeException(e);
                } catch (InstantiationException e) {
                    throw new RuntimeException(e);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }



}
