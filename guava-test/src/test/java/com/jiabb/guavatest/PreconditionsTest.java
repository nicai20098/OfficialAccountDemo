package com.jiabb.guavatest;

import org.assertj.core.util.Preconditions;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * @author jiabinbin
 * @date 2020/11/17 11:18 下午
 * @classname Preconditions
 * @description guava 断言测试
 */
public class PreconditionsTest {

    //断言检查是否为null
    private void checkNotNull(List<String> list){
        Preconditions.checkNotNull(list);
    }

    @Test(expected = NullPointerException.class)
    public void testCheckNotNull(){
        checkNotNull(null);
    }

    //断言为null并附带信息
    private void checkNotNullWithMessage(List<String> list){
        Preconditions.checkNotNull(list,"This list should not null~~~");
    }

    @Test
    public void testCheckNotNullWithMessage(){
        try {
            checkNotNullWithMessage(null);
        }catch (Exception e){
            assertThat(e.getClass(),is(NullPointerException.class));
            assertThat(e.getMessage(),equalTo("This list should not null~~~"));
        }
    }

}
