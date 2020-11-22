package com.jiabb.hancrest.beans;

import com.jiabb.hancrest.Condition;
import com.jiabb.hancrest.Description;
import com.jiabb.hancrest.Matcher;
import com.jiabb.hancrest.TypeSafeDiagnosingMatcher;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;

import static com.jiabb.hancrest.Condition.matched;
import static com.jiabb.hancrest.Condition.notMatched;
import static com.jiabb.hancrest.beans.PropertyUtil.NO_ARGUMENTS;

/**
 * @author jiabinbin
 * @date 2020/11/22 12:45 下午
 * @classname HasPropertyWithValue
 * @description 是否有属性和对应值
 */
public class HasPropertyWithValue<T> extends TypeSafeDiagnosingMatcher<T> {

    private static final Condition.Step<PropertyDescriptor, Method> WITH_READ_METHOD = withReadMethod();
    private final String propertyName;
    private final Matcher<Object> valueMatcher;
    private final String messageFormat;

    public HasPropertyWithValue(String propertyName, Matcher<?> valueMatcher) {
        this(propertyName, valueMatcher, " property '%s' ");
    }

    public HasPropertyWithValue(String propertyName, Matcher<?> valueMatcher, String messageFormat) {
        this.propertyName = propertyName;
        this.valueMatcher = nastyGenericsWorkaround(valueMatcher);
        this.messageFormat = messageFormat;
    }

    private static Condition.Step<PropertyDescriptor,Method> withReadMethod() {
        return new Condition.Step<PropertyDescriptor, java.lang.reflect.Method>() {
            @Override
            public Condition<Method> apply(PropertyDescriptor property, Description mismatch) {
                final Method readMethod = property.getReadMethod();
                if (null == readMethod) {
                    mismatch.appendText("property \"" + property.getName() + "\" is not readable");
                    return notMatched();
                }
                return matched(readMethod, mismatch);
            }
        };
    }

    private static Matcher<Object> nastyGenericsWorkaround(Matcher<?> valueMatcher) {
        return (Matcher<Object>) valueMatcher;
    }

    @Override
    protected boolean matchesSafely(T item, Description mismatchDescription) {
        return propertyOn(item, mismatchDescription)
                .and(WITH_READ_METHOD)
                .and(withPropertyValue(item))
                .matching(valueMatcher, String.format(messageFormat, propertyName));
    }

    private Condition<PropertyDescriptor> propertyOn(T bean, Description mismatch) {
        PropertyDescriptor property = PropertyUtil.getPropertyDescriptor(propertyName, bean);
        if (property == null) {
            mismatch.appendText("No property \"" + propertyName + "\"");
            return notMatched();
        }

        return matched(property, mismatch);
    }

    private Condition.Step<Method, Object> withPropertyValue(final T bean) {
        return new Condition.Step<Method, Object>() {
            @Override
            public Condition<Object> apply(Method readMethod, Description mismatch) {
                try {
                    return matched(readMethod.invoke(bean, NO_ARGUMENTS), mismatch);
                } catch (InvocationTargetException e) {
                    mismatch
                            .appendText("Calling '")
                            .appendText(readMethod.toString())
                            .appendText("': ")
                            .appendValue(e.getTargetException().getMessage());
                    return notMatched();
                } catch (Exception e) {
                    throw new IllegalStateException(
                            "Calling: '" + readMethod + "' should not have thrown " + e);
                }
            }
        };
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("hasProperty(").appendValue(propertyName).appendText(", ")
                .appendDescriptionOf(valueMatcher).appendText(")");
    }

    public static <T> Matcher<T> hasProperty(String propertyName, Matcher<?> valueMatcher) {
        return new HasPropertyWithValue<>(propertyName, valueMatcher);
    }


    public static <T> Matcher<T> hasPropertyAtPath(String path, Matcher<T> valueMatcher) {
        List<String> properties = Arrays.asList(path.split("\\."));
        ListIterator<String> iterator =
                properties.listIterator(properties.size());

        Matcher<T> ret = valueMatcher;
        while (iterator.hasPrevious()) {
            ret = new HasPropertyWithValue<>(iterator.previous(), ret, "%s.");
        }
        return ret;
    }
}
