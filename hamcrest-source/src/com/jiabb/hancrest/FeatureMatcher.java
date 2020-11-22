package com.jiabb.hancrest;

import com.jiabb.hancrest.internal.ReflectiveTypeFinder;

/**
 * @author jiabinbin
 * @date 2020/11/22 11:30 上午
 * @classname FeatureMatcher
 * @description TODO
 */
public abstract class FeatureMatcher<T, U> extends TypeSafeDiagnosingMatcher<T> {

    private static final ReflectiveTypeFinder TYPE_FINDER = new ReflectiveTypeFinder("featureValueOf", 1, 0);
    private final Matcher<? super U> subMatcher;
    private final String featureDescription;
    private final String featureName;

    public FeatureMatcher(Matcher<? super U> subMatcher, String featureDescription, String featureName) {
        super(TYPE_FINDER);
        this.subMatcher = subMatcher;
        this.featureDescription = featureDescription;
        this.featureName = featureName;
    }

    protected abstract U featureValueOf(T actual);

    @Override
    protected boolean matchesSafely(T item, Description mismatchDescription) {
        final U featureValue = featureValueOf(item);
        if (!subMatcher.matches(featureValue)) {
            mismatchDescription.appendText(featureName).appendText(" ");
            subMatcher.describeMismatch(featureValue, mismatchDescription);
            return false;
        }
        return true;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText(featureDescription).appendText(" ")
                .appendDescriptionOf(subMatcher);
    }
}
