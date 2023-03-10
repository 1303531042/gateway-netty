package com.kun.simulation.properties;

import com.kun.simulation.annotation.device.ProductID;
import com.kun.simulation.annotation.device.PropertiesToDeviceClass;
import com.kun.simulation.domain.AirEle;
import com.kun.simulation.domain.LightEle;
import lombok.Data;

/**
 * @author KUN
 * @date 2023/2/9
 **/
@Data
@ProductID(Value = 2)
@PropertiesToDeviceClass(Value = LightEle.class)
public class Test2 extends BaseDeviceProperties {
    private String b;
}
