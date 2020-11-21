package com.jiabb.hancrest;

/**
 * @author jiabinbin
 * @date 2020/11/21 10:35 下午
 * @classname DiagnosingMatcher
 * @description 待定
 */
public abstract class DiagnosingMatcher<T> extends BaseMatcher<T> {
    @Override
    public boolean matches(Object actual) {
        return matches(actual,Description.NONE);
    }

    @Override
    public void describeMismatch(Object item, Description description) {
        matches(item,description);
    }

    protected abstract boolean matches(Object item, Description mismatchDescription);
}
