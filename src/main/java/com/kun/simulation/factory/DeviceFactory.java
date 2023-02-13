package com.kun.simulation.factory;

import com.kun.simulation.DeviceManger;
import com.kun.simulation.ProductProperties;
import com.kun.simulation.factory.PropertiesFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author KUN
 * @date 2023/2/8
 * @describe 设备工厂
 **/
@Component
public class DeviceFactory implements InitializingBean {

    private Map<, ProductProperties> productPropertiesMap;
    @Autowired
    private DeviceManger deviceManger;
    @Autowired
    private PropertiesFactory propertiesFactory;

    private Map<Class<?>, Class<?>> propertiesClassToDeviceClass;



    @Override
    public void afterPropertiesSet() throws Exception {
        productPropertiesMap

    }
}
