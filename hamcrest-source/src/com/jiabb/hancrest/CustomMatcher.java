package com.jiabb.hancrest;

/**
 * @author jiabinbin
 * @date 2020/11/21 10:25 下午
 * @classname CustomMatcher
 * @description 用于注销一次性匹配器的实用程序类
 * 这个类是为匿名内部类的情况而设计的matcher说得通。它不应该有实现
 */
public abstract class CustomMatcher<T> extends BaseMatcher {

    private final String fixedDescription;

    public CustomMatcher(String description) {
        if (description == null) {
            throw new IllegalArgumentException("Description should be non null!");
        }
        this.fixedDescription = description;
    }

    @Override
    public final void describeTo(Description description) {
        description.appendText(fixedDescription);
    }
}
