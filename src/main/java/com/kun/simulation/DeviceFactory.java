package com.kun.simulation;

import com.kun.simulation.properties.factory.PropertiesFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.rmi.MarshalledObject;
import java.util.Map;

/**
 * @author KUN
 * @date 2023/2/8
 **/
@Component
public class DeviceFactory implements InitializingBean {

    private Map<Long, ProductProperties> productPropertiesMap;
    @Autowired
    private DeviceManger deviceManger;
    @Autowired
    private PropertiesFactory propertiesFactory;



    @Override
    public void afterPropertiesSet() throws Exception {

    }
}
