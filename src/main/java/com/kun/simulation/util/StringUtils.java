package com.kun.simulation.util;

/**
 * @author KUN
 * @date 2023/2/9
 **/
public class StringUtils {
    public static String captureName(String name) {
        name = name.substring(0, 1).toUpperCase() + name.substring(1);
        return  name;

    }
}
