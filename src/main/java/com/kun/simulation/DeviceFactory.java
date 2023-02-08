package com.kun.simulation;

import org.springframework.beans.factory.InitializingBean;
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

    private DeviceManger deviceManger;



    @Override
    public void afterPropertiesSet() throws Exception {

    }
}
