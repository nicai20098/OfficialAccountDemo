package com.jiabb.hancrest.internal;

import com.jiabb.hancrest.Description;
import com.jiabb.hancrest.SelfDescribing;

/**
 * @author jiabinbin
 * @date 2020/11/21 2:10 下午
 * @classname SelfDescribingValue
 * @description 自我描述对象的实体对象 value为描述的属性 通过appendValue方法把属性赋值给describe对象
 */
public class SelfDescribingValue<T> implements SelfDescribing {

    private T value;

    /**
     * 构造方法进行初始化
     * @param value
     */
    public SelfDescribingValue(T value){
        this.value = value;
    }


    @Override
    public void describeTo(Description description) {
        description.appendValue(value);
    }
}
