package com.jiabb.hancrest;

import com.jiabb.hancrest.internal.ArrayIterator;
import com.jiabb.hancrest.internal.SelfDescribingValueIterator;

import java.util.Arrays;
import java.util.Iterator;

/**
 * @author jiabinbin
 * @date 2020/11/21 1:57 下午
 * @classname BaseDescription
 * @description TODO
 */
public abstract class BaseDescription implements Description{
    @Override
    public Description appendText(String text) {
        append(text);
        return this;
    }

    @Override
    public Description appendDescriptionOf(SelfDescribing value) {
        value.describeTo(this);
        return this;
    }


    @Override
    public Description appendValue(Object value) {
        if (value == null) {
            append("null");
        } else if (value instanceof String) {
            toJavaSyntax((String) value);
        } else if (value instanceof Character) {
            append('"');
            toJavaSyntax((Character) value);
            append('"');
        } else if (value instanceof Byte) {
            append('<');
            append(descriptionOf(value));
            append("b>");
        } else if (value instanceof Short) {
            append('<');
            append(descriptionOf(value));
            append("s>");
        } else if (value instanceof Long) {
            append('<');
            append(descriptionOf(value));
            append("L>");
        } else if (value instanceof Float) {
            append('<');
            append(descriptionOf(value));
            append("F>");
        } else if (value.getClass().isArray()) {
            appendValueList("[",", ","]", new ArrayIterator(value));
        } else {
            append('<');
            append(descriptionOf(value));
            append('>');
        }
        return this;
    }


    @Override
    public <T> Description appendValueList(String start, String separator, String end, T... values) {
        //将可变参数转为List<T>
        return appendValueList(start,separator,end, Arrays.asList(values));
    }

    @Override
    public <T> Description appendValueList(String start, String separator, String end, Iterable<T> values) {
        //将list<T> 转为迭代器对象
        return appendValueList(start,separator,end,values.iterator());
    }

    //使用默认对象进行处理
    private <T> Description appendValueList(String start, String separator, String end, Iterator<T> values){
        return appendList(start,separator,end,new SelfDescribingValueIterator<>(values));
    }

    //将list<T> 转为迭代器对象
    @Override
    public Description appendList(String start, String separator, String end, Iterable<? extends SelfDescribing> values) {
        return appendList(start,separator,end,values.iterator());
    }

    //真正处理逻辑
    private Description appendList(String start, String separator, String end, Iterator<? extends SelfDescribing> values){
        boolean separate = false;
        append(start);
        while (values.hasNext()) {
            if (separate) append(separator);
            appendDescriptionOf(values.next());
            separate = true;
        }
        append(end);

        return this;
    }

    /**
     * 将字符串附加到描述对象中
     * 默认实现将每个字符传递给{@link #append(char)}
     * 在子类中重写以提供有效的实现。
     * @param str
     */
    protected void append(String str) {
        for (int i = 0; i < str.length(); i++) {
            append(str.charAt(i));
        }
    }

    /**
     * 将字节附加到描述对象中
     */
    protected abstract void append(char c);

    //转换为Java语法
    private void toJavaSyntax(String unformatted) {
        append('"');
        for (int i = 0; i < unformatted.length(); i++) {
            toJavaSyntax(unformatted.charAt(i));
        }
        append('"');
    }

    //特殊字符处理
    private void toJavaSyntax(char ch) {
        switch (ch) {
            case '"':
                append("\\\"");
                break;
            case '\n':
                append("\\n");
                break;
            case '\r':
                append("\\r");
                break;
            case '\t':
                append("\\t");
                break;
            case '\\':
                append("\\\\");
                break;
            default:
                append(ch);
        }
    }

    /**
     * 将对象转为String类型 如果转换失败则抛出异常
     * @param value
     * @return
     */
    private String descriptionOf(Object value) {
        try {
            return String.valueOf(value);
        }
        catch (Exception e) {
            return value.getClass().getName() + "@" + Integer.toHexString(value.hashCode());
        }
    }
}
