package com.jiabb.hancrest.number;

import com.jiabb.hancrest.Description;
import com.jiabb.hancrest.Matcher;
import com.jiabb.hancrest.TypeSafeMatcher;

/**
 * @author jiabinbin
 * @date 2020/11/22 7:30 下午
 * @classname IsNaN
 * @description TODO
 */
public final class IsNaN extends TypeSafeMatcher<Double> {

    private IsNaN() {
    }

    @Override
    protected boolean matchesSafely(Double item) {
        return Double.isNaN(item);
    }

    @Override
    protected void describeMismatchSafely(Double item, Description mismatchDescription) {
        mismatchDescription.appendText("was ").appendValue(item);
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("a double value of NaN");
    }

    public static Matcher<Double> notANumber() {
        return new IsNaN();
    }
}
