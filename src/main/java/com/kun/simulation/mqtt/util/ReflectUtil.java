package com.kun.simulation.mqtt.util;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 反射工具类
 * @author KUN
 * @date 2022/7/27
 **/
@Slf4j
public class ReflectUtil {

    /**
     * 根据属性名获取对应的value值
     */
    public static String getFieldValue(Class clazz, Object obj, String fieldName) {
        String getMethodName = null;
        try {
            getMethodName = "get" +
                    fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
            Method method = clazz.getDeclaredMethod(getMethodName);
            String value = method.invoke(obj).toString();
            return value;
        } catch (InvocationTargetException e) {
            log.info("Class："+clazz.getSimpleName()+"  "+getMethodName+"方法运行失败");
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            log.info("没有找到Class："+clazz.getSimpleName()+"中的："+fieldName+"属性的"+getMethodName+"方法");
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

    }
    /**
     * 根据属性名修改对应的value值
     */
    public static void setFieldValue(Class clazz, Object obj, String fieldName,Object valueObj) {
        String setMethodName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            if (method.getName().equals(setMethodName)) {
                try {
                    method.invoke(obj, valueObj);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                } catch (InvocationTargetException e) {
                    log.info("Class："+clazz.getSimpleName()+"  "+setMethodName+"方法运行失败");
                    throw new RuntimeException(e);
                }
                break;
            }
        }
    }

}
