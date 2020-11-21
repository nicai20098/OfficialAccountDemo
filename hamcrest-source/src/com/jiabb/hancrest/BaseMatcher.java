package com.jiabb.hancrest;

/**
 * @author jiabinbin
 * @date 2020/11/21 1:53 下午
 * @classname BaseMatcher
 * @description TODO
 */
public abstract class BaseMatcher<T> implements Matcher<T>{

    @Override
    @Deprecated
    public final void _dont_implement_Matcher___instead_extend_BaseMatcher_() {
        // See Matcher interface for an explanation of this method.
    }

    @Override
    public void describeMismatch(Object item, Description description) {
        description.appendText("was ").appendValue(item);
    }

    @Override
    public String toString() {
        return StringDescription.toString(this);
    }

    /**
     * 对null的一个校验方法 如果为空 则写明为空的原因
     * @param actual the object to check
     * @param mismatch where to write the mismatch description, if any
     * @return false if the actual object is null
     */
    protected static boolean isNotNull(Object actual, Description mismatch) {
        if (actual == null) {
            mismatch.appendText("was null");
            return false;
        }
        return true;
    }


}
