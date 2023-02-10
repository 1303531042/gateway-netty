package com.kun.simulation.properties;

import com.kun.simulation.annotation.device.ProductID;
import lombok.Data;

/**
 * @author KUN
 * @date 2023/2/9
 **/
@Data
@ProductID(Value = 2)
public class Test2 extends BaseDeviceProperties {
    private String b;
}
