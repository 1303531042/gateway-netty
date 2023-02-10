//package com.kun.simulation.config;
//
//import com.kun.simulation.properties.Test1;
//import lombok.Data;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.PropertySource;
//
//import java.util.List;
//import java.util.Map;
//
///**
// * @author KUN
// * @date 2023/2/9
// **/
//@Configuration
//@ConfigurationProperties(prefix = "devices")
//@PropertySource(value = "classpath:deviceProperties.yml",factory = MyYamlFactory.class)
//@Data
//public class DevicePropertiesConfiguration {
//    private Map<String, List<Test1>> devicesForProductIdMap;
//
//    public void setDevicesForProductIdMap(Map<String, List<Test1>> devicesForProductIdMap) {
//        this.devicesForProductIdMap = devicesForProductIdMap;
//    }
//}
