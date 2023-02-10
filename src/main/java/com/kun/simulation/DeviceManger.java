package com.kun.simulation;

import com.kun.simulation.domain.Device;
import com.kun.simulation.mqtt.bean.EmqxClient;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author KUN
 * @date 2023/2/8
 **/
@Data
@Component
public class DeviceManger {
    /**
     * key: 产品id
     * value: 设备集合
     */
    private Map<Long, List<Device>> productIdToDeviceMap;
    /**
     * key: 设备编号
     * value: 设备
     */
    private Map<String,Device> serialNumberToDeviceMap;

    /**
     * key: 设备
     * value: 设备对应的Emqx客户端
     */
    private Map<Device, EmqxClient> emqxClientMap;

    public void putDeviceToProductIdToDeviceMap(Long productId,Device device) {
        List<Device> deviceList = productIdToDeviceMap.computeIfAbsent(productId, k -> new ArrayList<>());
        deviceList.add(device);
    }
    public void putDeviceToSerialNumberToDeviceMap(String serialNumber,Device device) {
        serialNumberToDeviceMap.putIfAbsent(serialNumber, device);
    }

    public void setEmqxClientToDevice(Device device, EmqxClient client) {
        emqxClientMap.putIfAbsent(device, client);
    }

    public List<Device> getDevicesForProductIdToDeviceMap(Long productId) {
        return productIdToDeviceMap.get(productId);

    }
    public Device getDeviceForSerialNumberToDeviceMap(String serialNumber) {
        return serialNumberToDeviceMap.get(serialNumber);
    }

    public EmqxClient getEmqxClientForEmqxClientMap(Device device) {
        return emqxClientMap.get(device);
    }




}
