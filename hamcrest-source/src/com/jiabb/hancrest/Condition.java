package com.jiabb.hancrest;

/**
 * @author jiabinbin
 * @date 2020/11/21 9:18 下午
 * @classname Condition
 * @description 条件实现了多步匹配的一部分。我们有时需要编写具有一系列步骤的匹配器，其中每一步都取决于前一步的结果，我们可以在一步之后立即停止处理失败了。
 *              这些类为编写这样的序列提供了基础设施。
 */
public abstract class Condition<T> {

    public static final NotMatched<Object> NOT_MATCHED = new NotMatched<Object>();
    /**
     * 步骤接口
     * @param <I> 上一步的类对象
     * @param <O> 下一步的类对象
     */
    public interface Step<I, O> {
        Condition<O> apply(I value, Description mismatch);
    }

    private Condition() { }

    public abstract boolean matching(Matcher<T> match, String message);
    public abstract <U> Condition<U> and(Step<? super T, U> mapping);

    public final boolean matching(Matcher<T> match) { return matching(match, ""); }
    public final <U> Condition<U> then(Step<? super T, U> mapping) { return and(mapping); }

    public static <T> Condition<T> notMatched() {
        return (Condition<T>) NOT_MATCHED;
    }

    public static <T> Condition<T> matched(final T theValue, final Description mismatch) {
        return new Matched<T>(theValue, mismatch);
    }

    private static final class Matched<T> extends Condition<T> {
        private final T theValue;
        private final Description mismatch;

        private Matched(T theValue, Description mismatch) {
            this.theValue = theValue;
            this.mismatch = mismatch;
        }

        @Override
        public boolean matching(Matcher<T> matcher, String message) {
            if (matcher.matches(theValue)) {
                return true;
            }
            mismatch.appendText(message);
            matcher.describeMismatch(theValue, mismatch);
            return false;
        }

        @Override
        public <U> Condition<U> and(Step<? super T, U> next) {
            return next.apply(theValue, mismatch);
        }
    }

    private static final class NotMatched<T> extends Condition<T> {
        @Override public boolean matching(Matcher<T> match, String message) { return false; }

        @Override public <U> Condition<U> and(Step<? super T, U> mapping) {
            return notMatched();
        }
    }
}
