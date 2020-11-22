package com.jiabb.hancrest.core;

import com.jiabb.hancrest.BaseMatcher;
import com.jiabb.hancrest.Description;
import com.jiabb.hancrest.Matcher;

import java.lang.reflect.Array;

/**
 * @author jiabinbin
 * @date 2020/11/22 1:37 下午
 * @classname IsEqual
 * @description 是否equal
 */
public class IsEqual<T> extends BaseMatcher<T> {

    private final Object expectedValue;

    public IsEqual(T equalArg) {
        expectedValue = equalArg;
    }

    @Override
    public boolean matches(Object actual) {
        return areEqual(actual, expectedValue);
    }

    @Override
    public void describeTo(Description description) {

    }

    private static boolean areEqual(Object actual, Object expected) {
        if (actual == null) {
            return expected == null;
        }
        if (expected != null && isArray(actual)) {
            return isArray(expected) && areArraysEqual(actual, expected);
        }
        return actual.equals(expected);
    }

    private static boolean isArray(Object o) {
        return o.getClass().isArray();
    }

    /**
     * 先判断数组长度 再校验数组的元素
     * @param actualArray 需要判断的数组
     * @param expectedArray 期望的数组
     * @return false or true
     */
    private static boolean areArraysEqual(Object actualArray, Object expectedArray) {
        return areArrayLengthsEqual(actualArray, expectedArray) && areArrayElementsEqual(actualArray, expectedArray);
    }

    /**
     * 判断数组长度是否相当
     * @param actualArray 需要判断的数组
     * @param expectedArray 期望的数组
     * @return false or true
     */
    private static boolean areArrayLengthsEqual(Object actualArray, Object expectedArray) {
        return Array.getLength(actualArray) == Array.getLength(expectedArray);
    }

    /**
     * 遍历数组判断是否每个元素都相等
     * 只要有一个元素不相等就返回false 否则返回true
     * @param actualArray 需要判断的数组
     * @param expectedArray 期望的数组
     * @return false or true
     */
    private static boolean areArrayElementsEqual(Object actualArray, Object expectedArray) {
        for (int i = 0; i < Array.getLength(actualArray); i++) {
            if (!areEqual(Array.get(actualArray, i), Array.get(expectedArray, i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 对外暴露的匹配方法
     */
    public static <T> Matcher<T> equalTo(T operand) {
        return new IsEqual<>(operand);
    }
    /**
     * 对外暴露的匹配方法
     */
    public static Matcher<Object> equalToObject(Object operand) {
        return new IsEqual<>(operand);
    }
}
