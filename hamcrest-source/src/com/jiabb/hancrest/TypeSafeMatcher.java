package com.jiabb.hancrest;

import com.jiabb.hancrest.internal.ReflectiveTypeFinder;

/**
 * @author jiabinbin
 * @date 2020/11/22 12:17 下午
 * @classname TypeSafeMatcher
 * @description 待定
 */
public abstract class TypeSafeMatcher<T> extends BaseMatcher<T>{

    private static final ReflectiveTypeFinder TYPE_FINDER = new ReflectiveTypeFinder("matchesSafely", 1, 0);

    final private Class<?> expectedType;

    protected TypeSafeMatcher() {
        this(TYPE_FINDER);
    }

    protected TypeSafeMatcher(Class<?> expectedType) {
        this.expectedType = expectedType;
    }

    protected TypeSafeMatcher(ReflectiveTypeFinder typeFinder) {
        this.expectedType = typeFinder.findExpectedType(getClass());
    }

    protected abstract boolean matchesSafely(T item);

    protected void describeMismatchSafely(T item, Description mismatchDescription) {
        super.describeMismatch(item, mismatchDescription);
    }

    @Override
    public boolean matches(Object item) {
        return item != null
                && expectedType.isInstance(item)
                && matchesSafely((T) item);
    }

    @Override
    final public void describeMismatch(Object item, Description description) {
        if (item == null) {
            super.describeMismatch(null, description);
        } else if (! expectedType.isInstance(item)) {
            description.appendText("was a ")
                    .appendText(item.getClass().getName())
                    .appendText(" (")
                    .appendValue(item)
                    .appendText(")");
        } else {
            describeMismatchSafely((T)item, description);
        }
    }
}
