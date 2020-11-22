package com.jiabb.hancrest.beans;

import com.jiabb.hancrest.Description;
import com.jiabb.hancrest.DiagnosingMatcher;
import com.jiabb.hancrest.Matcher;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.jiabb.hancrest.beans.PropertyUtil.NO_ARGUMENTS;
import static com.jiabb.hancrest.beans.PropertyUtil.propertyDescriptorsFor;
import static com.jiabb.hancrest.core.IsEqual.equalTo;
import static java.util.Arrays.asList;

/**
 * @author jiabinbin
 * @date 2020/11/22 1:34 下午
 * @classname SamePropertyValuesAs
 * @description todo 待补充
 */
public class SamePropertyValuesAs<T> extends DiagnosingMatcher<T> {

    private final T expectedBean;
    private final Set<String> propertyNames;
    private final List<PropertyMatcher> propertyMatchers;
    private final List<String> ignoredFields;

    public SamePropertyValuesAs(T expectedBean, List<String> ignoredProperties) {
        PropertyDescriptor[] descriptors = propertyDescriptorsFor(expectedBean, Object.class);
        this.expectedBean = expectedBean;
        this.ignoredFields = ignoredProperties;
        this.propertyNames = propertyNamesFrom(descriptors, ignoredProperties);
        this.propertyMatchers = propertyMatchersFor(expectedBean, descriptors, ignoredProperties);
    }

    @Override
    protected boolean matches(Object item, Description mismatchDescription) {
        return isNotNull(item, mismatchDescription)
                && isCompatibleType(item, mismatchDescription)
                && hasNoExtraProperties(item, mismatchDescription)
                && hasMatchingValues(item, mismatchDescription);
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("same property values as " + expectedBean.getClass().getSimpleName())
                .appendList(" [", ", ", "]", propertyMatchers);
        if (!ignoredFields.isEmpty()) {
            description.appendText(" ignoring ")
                    .appendValueList("[", ", ", "]", ignoredFields);
        }
    }

    private static class PropertyMatcher extends DiagnosingMatcher<Object> {
        private final Method readMethod;
        private final Matcher<Object> matcher;
        private final String propertyName;

        public PropertyMatcher(PropertyDescriptor descriptor, Object expectedObject) {
            this.propertyName = descriptor.getDisplayName();
            this.readMethod = descriptor.getReadMethod();
            this.matcher = equalTo(readProperty(readMethod, expectedObject));
        }

        @Override
        public boolean matches(Object actual, Description mismatch) {
            final Object actualValue = readProperty(readMethod, actual);
            if (!matcher.matches(actualValue)) {
                mismatch.appendText(propertyName + " ");
                matcher.describeMismatch(actualValue, mismatch);
                return false;
            }
            return true;
        }

        @Override
        public void describeTo(Description description) {
            description.appendText(propertyName + ": ").appendDescriptionOf(matcher);
        }
    }

    private static Object readProperty(Method method, Object target) {
        try {
            return method.invoke(target, NO_ARGUMENTS);
        } catch (Exception e) {
            throw new IllegalArgumentException("Could not invoke " + method + " on " + target, e);
        }
    }

    /**
     * 过滤掉忽略的字段属性
     */
    private static Set<String> propertyNamesFrom(PropertyDescriptor[] descriptors, List<String> ignoredFields) {
        HashSet<String> result = new HashSet<>();
        for (PropertyDescriptor propertyDescriptor : descriptors) {
            if (isIgnored(ignoredFields, propertyDescriptor)) {
                result.add(propertyDescriptor.getDisplayName());
            }
        }
        return result;
    }

    /**
     * 过滤掉忽略的字段属性
     */
    private static <T> List<PropertyMatcher> propertyMatchersFor(T bean, PropertyDescriptor[] descriptors, List<String> ignoredFields) {
        List<PropertyMatcher> result = new ArrayList<>(descriptors.length);
        for (PropertyDescriptor propertyDescriptor : descriptors) {
            if (isIgnored(ignoredFields, propertyDescriptor)) {
                result.add(new PropertyMatcher(propertyDescriptor, bean));
            }
        }
        return result;
    }

    /**
     * 判断是否忽略此元素
     */
    private static boolean isIgnored(List<String> ignoredFields, PropertyDescriptor propertyDescriptor) {
        return !ignoredFields.contains(propertyDescriptor.getDisplayName());
    }

    private boolean isCompatibleType(Object actual, Description mismatchDescription) {
        if (expectedBean.getClass().isAssignableFrom(actual.getClass())) {
            return true;
        }
        mismatchDescription.appendText("is incompatible type: " + actual.getClass().getSimpleName());
        return false;
    }

    /**
     * 是否有扩展字段
     */
    private boolean hasNoExtraProperties(Object actual, Description mismatchDescription) {
        Set<String> actualPropertyNames = propertyNamesFrom(propertyDescriptorsFor(actual, Object.class), ignoredFields);
        actualPropertyNames.removeAll(propertyNames);
        if (!actualPropertyNames.isEmpty()) {
            mismatchDescription.appendText("has extra properties called " + actualPropertyNames);
            return false;
        }
        return true;
    }

    private boolean hasMatchingValues(Object actual, Description mismatchDescription) {
        for (PropertyMatcher propertyMatcher : propertyMatchers) {
            if (!propertyMatcher.matches(actual)) {
                propertyMatcher.describeMismatch(actual, mismatchDescription);
                return false;
            }
        }
        return true;
    }

    /** 期望的JavaBean属性以指定bean是否相同 */
    public static <B> Matcher<B> samePropertyValuesAs(B expectedBean, String... ignoredProperties) {
        return new SamePropertyValuesAs<>(expectedBean, asList(ignoredProperties));
    }
}
