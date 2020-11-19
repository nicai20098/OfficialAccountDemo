package com.jiabb.hancrest;

/**
 * @author jiabinbin
 * @date 2020/11/19 10:55 下午
 * @classname Matcher
 * @description 过界匹配器 即匹配是否达到预期 匹配器可以做出描述在未匹配到时
 */
public interface Matcher<T> extends SelfDescribing{


    /**
     * 判断参数和本地项是否匹配
     * @param actual 实际参数
     * @return 是否匹配
     */
    boolean matches(Object actual);

    /**
     * 传递需要匹配的对象和描述对象
     * @param actual 实际参数
     * @param mismatchDescription 描述对象
     */
    void describeMismatch(Object actual, Description mismatchDescription);

    /**
     * @Deprecated 一个不推荐使用的注解 是方法会出现删除线 但是不代表次方法不可以使用
     */
    @Deprecated
    void _dont_implement_Matcher___instead_extend_BaseMatcher_();
}
