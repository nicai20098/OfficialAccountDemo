package com.jiabb.hancrest.number;

import com.jiabb.hancrest.Description;
import com.jiabb.hancrest.Matcher;
import com.jiabb.hancrest.TypeSafeMatcher;

import static java.lang.Math.abs;

/**
 * @author jiabinbin
 * @date 2020/11/22 7:28 下午
 * @classname IsCloseTo
 * @description IsCloseTo
 */
public class IsCloseTo extends TypeSafeMatcher<Double> {

    private final double delta;
    private final double value;

    public IsCloseTo(double value, double error) {
        this.delta = error;
        this.value = value;
    }


    @Override
    protected boolean matchesSafely(Double item) {
        return actualDelta(item) <= 0.0;
    }

    @Override
    protected void describeMismatchSafely(Double item, Description mismatchDescription) {
        mismatchDescription.appendValue(item)
                .appendText(" differed by ")
                .appendValue(actualDelta(item))
                .appendText(" more than delta ")
                .appendValue(delta);
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("a numeric value within ")
                .appendValue(delta)
                .appendText(" of ")
                .appendValue(value);
    }

    private double actualDelta(Double item) {
        return abs(item - value) - delta;
    }

    public static Matcher<Double> closeTo(double operand, double error) {
        return new IsCloseTo(operand, error);
    }
}
