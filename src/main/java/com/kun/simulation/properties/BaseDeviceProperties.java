package com.kun.simulation.properties;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author KUN
 * @date 2023/2/9
 **/
@Data
public abstract class BaseDeviceProperties {
    /** 产品ID */
    public Integer productId;
    /** 设备编号 */
    public String serialNumber;
    /** 用户ID */
    public Integer userId;


    /** 固件版本 */
    public Double firmwareVersion;
    /** 设备状态（1-未激活，2-禁用，3-在线，4-离线） */
    public Integer status;
    /**
     * wifi信号强度（信号极好4格[-55— 0]，信号好3格[-70— -55]，信号一般2格[-85— -70]，信号差1格[-100— -85]）
     */
    public Integer rssi;
    /** 设备经度 */
    public Double longitude;

    /** 设备纬度 */
    public Double latitude;
}
