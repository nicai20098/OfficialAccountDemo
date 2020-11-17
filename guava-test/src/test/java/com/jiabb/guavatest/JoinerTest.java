package com.jiabb.guavatest;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableMap;
import com.google.common.io.Files;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.joining;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;


/**
 * @author jiabinbin
 * @date 2020/11/17 8:20 上午
 * @classname JoinerTest
 * @description guava JoinerTest 拼接
 */
public class JoinerTest {

    private final List<String> source = Arrays.asList("This", "Guava", "Java");
    private final List<String> sourceAndContainerNull = Arrays.asList("This", "Guava", "Java", null);
    //写的目标文件
    private final String targetFileName = "/Users/jiabinbin/IdeaProjects/github/OfficialAccountDemo/guava-test/guava-joiner.txt";
    //map源
    private final Map<String, String> stringMap = ImmutableMap.of("This", "Guava", "Java", "Test");

    //正常字符串拼接
    @Test
    public void testJoinOnJoin() {
        String result = Joiner.on("#").join(source);
        assertThat(result, equalTo("This#Guava#Java"));
    }

    //正常字符串拼接 带有null的list  expected 期望抛出异常
    @Test(expected = NullPointerException.class)
    public void testJoinOnJoinWithNullValue() {
        String result = Joiner.on("#").join(sourceAndContainerNull);
        assertThat(result, equalTo("This#Guava#Java"));
    }

    //正常字符串拼接 带有null的list  略过null
    @Test
    public void testJoinOnJoinWithNullValueButSkip() {
        String result = Joiner.on("#").skipNulls().join(sourceAndContainerNull);
        assertThat(result, equalTo("This#Guava#Java"));
    }

    //正常字符串拼接 带有null的list  将null替换为DEFAULT
    @Test
    public void testJoinOnJoinWithNullValueButDefault() {
        String result = Joiner.on("#").useForNull("DEFAULT").join(sourceAndContainerNull);
        assertThat(result, equalTo("This#Guava#Java#DEFAULT"));
    }

    //也可以将拼接完的数据放到一个 StringBuilder对象中
    @Test
    public void testJoinOnAppendToStringBuilder() {
        StringBuilder builder = new StringBuilder();
        StringBuilder resultBuilder = Joiner.on("#").useForNull("DEFAULT").appendTo(builder, sourceAndContainerNull);
        //判断是否是一个实例
        assertThat(resultBuilder, sameInstance(builder));
        assertThat(resultBuilder.toString(), equalTo("This#Guava#Java#DEFAULT"));
        assertThat(builder.toString(), equalTo("This#Guava#Java#DEFAULT"));
    }

    //也可以将拼接完的数据放到一个 文件对象中
    @Test
    public void testJoinToAppendToWriter() {
        try (FileWriter writer = new FileWriter(new File(targetFileName))) {
            Joiner.on("#").useForNull("DEFAULT").appendTo(writer, sourceAndContainerNull);
            assertThat(Files.isFile().test(new File(targetFileName)), equalTo(true));
        } catch (IOException e) {
            fail("append to the writer occur fail");
        }
    }

    //使用java8 stream和joining 实现
    @Test
    public void testJoiningByStreamSkipNullValues() {
        String result = sourceAndContainerNull.stream().filter(item -> item != null && !item.isEmpty()).collect(joining("#"));
        assertThat(result, equalTo("This#Guava#Java"));
    }

    //使用java8 stream和joining 实现 并且替换null为默认值
    @Test
    public void testJoiningByStreamNullToDefaultValue() {
        String result = sourceAndContainerNull.stream().map(item -> item == null || item.isEmpty() ? "DEFAULT" : item).collect(joining("#"));
        assertThat(result, equalTo("This#Guava#Java#DEFAULT"));
    }

    //通过函数方式来实现上个测试的情况
    @Test
    public void testJoiningByStreamNullToDefaultValueByFunction() {
        String result = sourceAndContainerNull.stream().map(this::defaultValue).collect(joining("#"));
        assertThat(result, equalTo("This#Guava#Java#DEFAULT"));
    }

    public String defaultValue(String item) {
        return item == null || item.isEmpty() ? "DEFAULT" : item;
    }

    //可以对map进行拼接 同样也可以放到一个StringBuild或者Writer中
    @Test
    public void testJoinOnWithMap() {
        String result = Joiner.on("#").withKeyValueSeparator("=").join(stringMap);
        assertThat(result, equalTo("This=Guava#Java=Test"));
    }


}
