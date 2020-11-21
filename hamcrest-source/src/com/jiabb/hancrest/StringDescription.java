package com.jiabb.hancrest;

import java.io.IOException;

/**
 * @author jiabinbin
 * @date 2020/11/21 8:57 下午
 * @classname StringDescription
 * @description A {@link Description} that is stored as a string.
 */
public class StringDescription extends BaseDescription{

    /**
     * appendable:
     *  作用：附加字符序列和值的对象，
     *  规则：字符为Unicode。
     *  规则：线程不一定安全，是否安全是由实现类控制。
     *  规则：错误（异常）可能传递到调用程序。因为方法本身既有抛出异常：IOException  和    IndexOutOfBoundsException
     *  实现类：流等
     */
    private final Appendable out;

    public StringDescription() {
        this(new StringBuilder());
    }

    public StringDescription(Appendable out) {
        this.out = out;
    }

    /**
     * Return the description of a {@link SelfDescribing} object as a String.
     *
     * @param selfDescribing The object to be described.
     * @return he description of the object.
     */
    public static String toString(SelfDescribing selfDescribing) {
        return new StringDescription().appendDescriptionOf(selfDescribing).toString();
    }

    /**
     * Alias for {@link #toString(SelfDescribing)}.
     */
    public static String asString(SelfDescribing selfDescribing) {
        return toString(selfDescribing);
    }

    @Override
    protected void append(String str) {
        try {
            out.append(str);
        } catch (IOException e) {
            throw new RuntimeException("Could not write description", e);
        }
    }

    @Override
    protected void append(char c) {
        try {
            out.append(c);
        } catch (IOException e) {
            throw new RuntimeException("Could not write description", e);
        }
    }

    @Override
    public String toString() {
        return out.toString();
    }
}
