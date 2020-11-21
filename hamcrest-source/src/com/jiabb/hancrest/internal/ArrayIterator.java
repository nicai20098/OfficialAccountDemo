package com.jiabb.hancrest.internal;

import java.lang.reflect.Array;
import java.util.Iterator;

/**
 * @author jiabinbin
 * @date 2020/11/21 8:46 下午
 * @classname ArrayIterator
 * @description 数组迭代器
 */
public class ArrayIterator implements Iterator<Object> {
    private final Object array;
    private int currentIndex = 0;

    public ArrayIterator(Object array){
        if (!array.getClass().isArray()){
            throw new IllegalArgumentException("not an array");
        }
        this.array = array;
    }

    @Override
    public boolean hasNext() {
        return currentIndex < Array.getLength(array);
    }

    @Override
    public Object next() {
        return Array.get(array, currentIndex++);
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("cannot remove items from an array");
    }
}
