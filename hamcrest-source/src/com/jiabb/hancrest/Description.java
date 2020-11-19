package com.jiabb.hancrest;

/**
 * @author jiabinbin
 * @date 2020/11/19 10:50 下午
 * @classname Description
 * @description 是对匹配器的一个描述对象
 */
public interface Description {

    /**
     * 默认实现 但是没有任何描述信息
     */
    static final Description NONE = new NullDescription();

    /**
     * 在描述中附加一些纯文本
     */
    Description appendText(String text);

    /**
     * 将{@link SelfDescribing}值的说明附加到此说明中。
     */
    Description appendDescriptionOf(SelfDescribing value);

    /**
     * 添加任意的类型的值到描述中
     */
    Description appendValue(Object value);

    /**
     * 将一个list 放到描述中
     * @param start 起始
     * @param separator 分割符
     * @param end 结尾
     * @param values 值
     * @param <T> 泛型
     * @return
     */
    <T> Description appendValueList(String start, String separator, String end,
                                    T... values);

    /**
     * 将一个list的迭代器 放到描述中
     * @param start 起始
     * @param separator 分割符
     * @param end 结尾
     * @param values 迭代器
     * @param <T> 泛型
     * @return
     */
    <T> Description appendValueList(String start, String separator, String end,
                                    Iterable<T> values);

    /**
     * 将一个SelfDescribing子类的迭代器 放到描述中
     * @param start 起始
     * @param separator 分割符
     * @param end 结尾
     * @param values SelfDescribing子类的迭代器
     * @return
     */
    Description appendList(String start, String separator, String end,
                          Iterable<? extends SelfDescribing> values);


    /**
     * 默认空实现
     */
    public static final class NullDescription implements Description {
        @Override
        public Description appendDescriptionOf(SelfDescribing value) {
            return this;
        }

        @Override
        public Description appendList(String start, String separator,
                                      String end, Iterable<? extends SelfDescribing> values) {
            return this;
        }

        @Override
        public Description appendText(String text) {
            return this;
        }

        @Override
        public Description appendValue(Object value) {
            return this;
        }

        @Override
        public <T> Description appendValueList(String start, String separator,
                                               String end, T... values) {
            return this;
        }

        @Override
        public <T> Description appendValueList(String start, String separator,
                                               String end, Iterable<T> values) {
            return this;
        }

        @Override
        public String toString() {
            return "";
        }
    }

}
