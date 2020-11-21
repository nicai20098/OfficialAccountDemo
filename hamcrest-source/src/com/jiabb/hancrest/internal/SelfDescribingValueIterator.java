package com.jiabb.hancrest.internal;

import com.jiabb.hancrest.SelfDescribing;

import java.util.Iterator;


/**
 * @author jiabinbin
 * @date 2020/11/21 2:13 下午
 * @classname SelfDescribingValueIterator
 * @description SelfDescribing的迭代器对象
 */
public class SelfDescribingValueIterator<T> implements Iterator<SelfDescribing> {
    private Iterator<T> values;

    /**
     * 构造方法进行初始化
     * @param values
     */
    public SelfDescribingValueIterator(Iterator<T> values) {
        this.values = values;
    }

    @Override
    public boolean hasNext() {
        return values.hasNext();
    }

    @Override
    public SelfDescribing next() {
        return new SelfDescribingValue(values.next());
    }

    @Override
    public void remove() {
        values.remove();
    }
}
