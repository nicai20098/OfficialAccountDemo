package com.jiabb.hancrest.number;

import com.jiabb.hancrest.Description;
import com.jiabb.hancrest.Matcher;
import com.jiabb.hancrest.TypeSafeMatcher;

import java.math.BigDecimal;
import java.math.MathContext;

/**
 * @author jiabinbin
 * @date 2020/11/22 6:40 下午
 * @classname BigDecimalCloseTo
 * @description 大数字
 */
public class BigDecimalCloseTo extends TypeSafeMatcher<BigDecimal> {

    private final BigDecimal delta;
    private final BigDecimal value;

    public BigDecimalCloseTo(BigDecimal value, BigDecimal error) {
        this.delta = error;
        this.value = value;
    }



    @Override
    protected boolean matchesSafely(BigDecimal item) {
        return actualDelta(item).compareTo(BigDecimal.ZERO) <= 0;
    }

    @Override
    protected void describeMismatchSafely(BigDecimal item, Description mismatchDescription) {
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

    /** stripTrailingZeros() 去除掉末尾无用的0  toPlainString()方法可以避免科学计数 */
    private BigDecimal actualDelta(BigDecimal item) {
        return item.subtract(value, MathContext.DECIMAL128).abs().subtract(delta, MathContext.DECIMAL128).stripTrailingZeros();
    }


    public static Matcher<BigDecimal> closeTo(BigDecimal operand, BigDecimal error) {
        return new BigDecimalCloseTo(operand, error);
    }
}
