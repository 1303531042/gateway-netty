package com.kun.simulation.properties;

import com.kun.simulation.annotation.device.ProductID;
import com.kun.simulation.annotation.device.PropertiesToDeviceClass;
import com.kun.simulation.domain.AirEle;
import lombok.Data;

/**
 * @author KUN
 * @date 2023/2/9
 **/
@Data
@ProductID(Value = 1)
@PropertiesToDeviceClass(Value = AirEle.class)
public class Test1 extends BaseDeviceProperties {
    private String a;
}
