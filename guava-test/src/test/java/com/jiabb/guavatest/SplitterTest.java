package com.jiabb.guavatest;

import com.google.common.base.Splitter;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;


/**
 * @author jiabinbin
 * @date 2020/11/17 10:46 下午
 * @classname SplitterTest
 * @description guava SplitterTest 分割
 */
public class SplitterTest {

    @Test
    public void testSplitOnSplit() {
        List<String> result = Splitter.on("|").splitToList("Hello|Guava|Java");
        //判断对象是否是null
        assertThat(result, notNullValue());
        assertThat(result.size(), equalTo(3));
        assertThat(result.get(0), equalTo("Hello"));
        assertThat(result.get(1), equalTo("Guava"));
        assertThat(result.get(2), equalTo("Java"));
    }

    //分割符有空的情况
    @Test
    public void testSplitOnSplitOmitEmpty() {
        List<String> result = Splitter.on("|").splitToList("Hello|Guava|Java|||");
        assertThat(result, notNullValue());
        assertThat(result.size(), equalTo(6));
        result = Splitter.on("|").omitEmptyStrings().splitToList("Hello|Guava|Java|||");
        assertThat(result, notNullValue());
        assertThat(result.size(), equalTo(3));
    }

    //去除空和空格
    @Test
    public void testSplitOnSplitOmitEmptyTrimResult() {
        List<String> result = Splitter.on("|").omitEmptyStrings().splitToList("Hello | Guava|Java|||");
        assertThat(result, notNullValue());
        assertThat(result.size(), equalTo(3));
        assertThat(result.get(0), equalTo("Hello "));
        assertThat(result.get(1), equalTo(" Guava"));

        result = Splitter.on("|").omitEmptyStrings().trimResults().splitToList("Hello| Guava|Java|||");
        assertThat(result.size(), equalTo(3));
        assertThat(result.get(0), equalTo("Hello"));
        assertThat(result.get(1), equalTo("Guava"));
    }

    //根据长度去切割
    @Test
    public void testSplitFixLength() {
        List<String> result = Splitter.fixedLength(4).splitToList("aaaabbbbcccc");
        assertThat(result, notNullValue());
        assertThat(result.size(), equalTo(3));
        assertThat(result.get(0), equalTo("aaaa"));
        assertThat(result.get(1), equalTo("bbbb"));
        assertThat(result.get(2), equalTo("cccc"));
    }
    //limit 用法 超过limit值会合并为一个
    @Test
    public void testSplitOnSplitLimit() {
        List<String> result = Splitter.on("#").limit(2).splitToList("aaaa#bbbb#cccc");
        assertThat(result, notNullValue());
        assertThat(result.size(), equalTo(2));
        assertThat(result.get(0), equalTo("aaaa"));
        assertThat(result.get(1), equalTo("bbbb#cccc"));
    }

    //根据正则表达式进行拆分
    @Test
    public void testSplitOnPatternString() {
        List<String> result = Splitter.onPattern("\\|").limit(2).splitToList("aaaa|bbbb|cccc");
        assertThat(result, notNullValue());
        assertThat(result.size(), equalTo(2));
        assertThat(result.get(0), equalTo("aaaa"));
        assertThat(result.get(1), equalTo("bbbb|cccc"));
    }

    //拆分字符串为一个map
    @Test
    public void testSplitOnSplitMap() {
        Map<String, String> result = Splitter.on("|").withKeyValueSeparator("=").split("aaaa=bbbb|cccc=dddd");
        assertThat(result, notNullValue());
        assertThat(result.size(), equalTo(2));
        assertThat(result.get("aaaa"), equalTo("bbbb"));
        assertThat(result.get("cccc"), equalTo("dddd"));
    }
}
