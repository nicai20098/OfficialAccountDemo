package com.jiabb.hancrest.beans;

import com.jiabb.hancrest.Description;
import com.jiabb.hancrest.Matcher;
import com.jiabb.hancrest.TypeSafeMatcher;

/**
 * @author jiabinbin
 * @date 2020/11/22 12:31 下午
 * @classname HasProperty
 * @description 是否有属性
 */
public class HasProperty<T> extends TypeSafeMatcher<T> {

    private final String propertyName;

    public HasProperty(String propertyName) {
        this.propertyName = propertyName;
    }


    @Override
    protected boolean matchesSafely(T item) {
        try {
            return PropertyUtil.getPropertyDescriptor(propertyName, item) != null;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    @Override
    public void describeMismatchSafely(T item, Description mismatchDescription) {
        mismatchDescription.appendText("no ").appendValue(propertyName).appendText(" in ").appendValue(item);
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("hasProperty(").appendValue(propertyName).appendText(")");
    }

    public static <T> Matcher<T> hasProperty(String propertyName) {
        return new HasProperty<T>(propertyName);
    }
}
