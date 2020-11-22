package com.jiabb.hancrest.beans;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;

/**
 * @author jiabinbin
 * @date 2020/11/22 12:32 下午
 * @classname PropertyUtil
 * @description 用于访问JavaBean对象属性的实用工具类
 */
public class PropertyUtil {

    public static PropertyDescriptor getPropertyDescriptor(String propertyName, Object fromObj) throws IllegalArgumentException {
        for (PropertyDescriptor property : propertyDescriptorsFor(fromObj, null)) {
            if (property.getName().equals(propertyName)) {
                return property;
            }
        }

        return null;
    }

    public static PropertyDescriptor[] propertyDescriptorsFor(Object fromObj, Class<Object> stopClass) throws IllegalArgumentException {
        try {
            return Introspector.getBeanInfo(fromObj.getClass(), stopClass).getPropertyDescriptors();
        } catch (IntrospectionException e) {
            throw new IllegalArgumentException("Could not get property descriptors for " + fromObj.getClass(), e);
        }
    }

    public static final Object[] NO_ARGUMENTS = new Object[0];
}
