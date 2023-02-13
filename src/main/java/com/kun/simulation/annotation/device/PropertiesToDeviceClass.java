package com.kun.simulation.annotation.device;

import java.lang.annotation.*;

/**
 * @author KUN
 * @date 2023/2/13
 **/
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface PropertiesToDeviceClass {
    Class<?> Value();
}
