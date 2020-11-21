package com.jiabb.hancrest.internal;

import java.lang.reflect.Method;

/**
 * @author jiabinbin
 * @date 2020/11/21 10:42 下午
 * @classname ReflectiveTypeFinder
 * @description 反射 类型
 */
public class ReflectiveTypeFinder {

    /**
     * 方法名称
     */
    private final String methodName;
    /**
     * 预期参数数量
     */
    private final int expectedNumberOfParameters;
    /**
     * 参数类型数量
     */
    private final int typedParameter;

    /**
     * 全参构造器
     * @param methodName
     * @param expectedNumberOfParameters
     * @param typedParameter
     */
    public ReflectiveTypeFinder(String methodName, int expectedNumberOfParameters, int typedParameter) {
        this.methodName = methodName;
        this.expectedNumberOfParameters = expectedNumberOfParameters;
        this.typedParameter = typedParameter;
    }

    public Class<?> findExpectedType(Class<?> fromClass) {
        for (Class<?> c = fromClass; c != Object.class; c = c.getSuperclass()) {
            for (Method method : c.getDeclaredMethods()) {
                if (canObtainExpectedTypeFrom(method)) {
                    return expectedTypeFrom(method);
                }
            }
        }
        throw new Error("Cannot determine correct type for " + methodName + "() method.");
    }


    /**
     *
     * @param method
     * @return
     * method.isSynthetic() 如果外部直接访问内部类对象私有属性时返回true
     */
    private boolean canObtainExpectedTypeFrom(Method method) {
        return method.getName().equals(methodName)
                && method.getParameterTypes().length == expectedNumberOfParameters
                && !method.isSynthetic();
    }

    private Class<?> expectedTypeFrom(Method method) {
        return method.getParameterTypes()[typedParameter];
    }



}
