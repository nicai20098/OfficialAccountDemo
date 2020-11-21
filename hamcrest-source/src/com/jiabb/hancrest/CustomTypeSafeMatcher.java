package com.jiabb.hancrest;

/**
 * @author jiabinbin
 * @date 2020/11/21 10:32 下午
 * @classname CustomTypeSafeMatcher
 * @description 用于编写一次性匹配器的实用工具类。
 */
public abstract class CustomTypeSafeMatcher<T> extends BaseMatcher<T> {
    private final String fixedDescription;

    public CustomTypeSafeMatcher(String description) {
        if (description == null) {
            throw new IllegalArgumentException("Description must be non null!");
        }
        this.fixedDescription = description;
    }

    @Override
    public final void describeTo(Description description) {
        description.appendText(fixedDescription);
    }
}
