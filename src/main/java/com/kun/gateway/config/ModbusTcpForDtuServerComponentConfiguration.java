package com.kun.gateway.config;


import com.iteaj.iot.config.ConnectProperties;
import com.iteaj.iot.modbus.server.dtu.ModbusRtuForDtuServerComponent;
import com.iteaj.iot.modbus.server.dtu.ModbusTcpForDtuServerComponent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author KUN
 * @date 2023/2/6
 **/
@Configuration
public class ModbusTcpForDtuServerComponentConfiguration {
//    @Bean
//    public ModbusTcpForDtuServerComponent modbusTcpForDtuServerComponent() {
//        // 监听7068端口
//        return new ModbusTcpForDtuServerComponent(new ConnectProperties(502));
//    }
    @Bean
    public ModbusRtuForDtuServerComponent modbusRtuForDtuServerComponent() {
        // 监听7058端口
        return new ModbusRtuForDtuServerComponent(new ConnectProperties(502));
    }
}
