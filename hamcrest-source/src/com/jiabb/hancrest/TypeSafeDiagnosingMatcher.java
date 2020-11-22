package com.jiabb.hancrest;

import com.jiabb.hancrest.internal.ReflectiveTypeFinder;

/**
 * @author jiabinbin
 * @date 2020/11/21 10:41 下午
 * @classname TypeSafeDiagnosingMatcher
 * @description 基类 用于需要特定类型的非空值的匹配器
 */
public abstract class TypeSafeDiagnosingMatcher<T> extends BaseMatcher<T> {

    //Finder 查找器
    private static final ReflectiveTypeFinder TYPE_FINDER = new ReflectiveTypeFinder("matchesSafely", 2, 0);
    private final Class<?> expectedType;

    /**
     * 子类应该实现这一点。该项已针对特定类型进行了检查，并且永远不会为null。
     * @param item
     * @param mismatchDescription
     * @return
     */
    protected abstract boolean matchesSafely(T item, Description mismatchDescription);

    protected TypeSafeDiagnosingMatcher(Class<?> expectedType) {
        this.expectedType = expectedType;
    }

    protected TypeSafeDiagnosingMatcher(ReflectiveTypeFinder typeFinder) {
        this.expectedType = typeFinder.findExpectedType(getClass());
    }

    protected TypeSafeDiagnosingMatcher() {
        this(TYPE_FINDER);
    }

    @Override
    public boolean matches(Object actual) {
        return actual != null
                && expectedType.isInstance(actual)
                && matchesSafely((T) actual, new Description.NullDescription());
    }

    @Override
    public void describeMismatch(Object item, Description mismatchDescription) {
        if (item == null) {
            mismatchDescription.appendText("was null");
        } else if (!expectedType.isInstance(item)) {
            mismatchDescription.appendText("was ")
                    .appendText(item.getClass().getSimpleName())
                    .appendText(" ")
                    .appendValue(item);
        } else {
            matchesSafely((T) item, mismatchDescription);
        }
    }
}
