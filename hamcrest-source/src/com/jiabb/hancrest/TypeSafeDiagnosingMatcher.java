package com.jiabb.hancrest;

/**
 * @author jiabinbin
 * @date 2020/11/21 10:41 下午
 * @classname TypeSafeDiagnosingMatcher
 * @description 基类 用于需要特定类型的非空值的匹配器
 */
public abstract class TypeSafeDiagnosingMatcher<T> extends BaseMatcher<T> {
    @Override
    public boolean matches(Object actual) {
        return false;
    }

    @Override
    public void describeTo(Description description) {

    }
}
