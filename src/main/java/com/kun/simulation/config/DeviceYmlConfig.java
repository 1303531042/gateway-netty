package com.kun.simulation.config;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.Map;

/**
 * @author KUN
 * @date 2023/2/9
 **/
@Configuration
public class DeviceYmlConfig {

    private static final String CONF_NAME = "deviceProperties.yml";
    public static Map<String, Object> devicesForProductIdMap;

    @Bean
    public Map<String, Object> deviceYmlMap() throws Exception {
        Yaml yaml = new Yaml();
        String fileName = this.getClass().getClassLoader().getResource(CONF_NAME).getPath();
        yaml.load(fileName);
        Map<String, Object> deviceYmlMap = (Map<String, Object>) yaml.load(readConfFromYaml(CONF_NAME));
        devicesForProductIdMap = (Map<String, Object>) ((Map<String, Object>) deviceYmlMap.get("devices")).get("devicesForProductIdMap");
        return deviceYmlMap;
    }



    public InputStream readConfFromYaml(String confName){
        InputStream input = null;
        try {
            input = this.getClass().getClassLoader().getResourceAsStream(confName);
        } catch (Exception e){
            //
        }
        return input;
    }
}
