package com.jaibb.stream;

import org.hamcrest.core.Is;
import org.hamcrest.core.IsEqual;
import org.junit.Before;
import org.junit.Test;

import java.io.PrintStream;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.Assert.assertThat;


/**
 * @author jiabinbin
 * @date 2020/12/7 9:36 下午
 * @classname StreamTest
 * @description StreamTest
 */
public class StreamTest {

    List<Person> personList = new ArrayList<Person>();

    @Before
    public void init(){
        personList.add(new Person("Tom",7000,"male","New York"));
        personList.add(new Person("Jack",7000,"male","Washington"));
        personList.add(new Person("Lily",7800,"female","Washington"));
        personList.add(new Person("Anni",8200,"female","New York"));
        personList.add(new Person("Owen",9500,"male","New York"));
        personList.add(new Person("Alisa",7900,"female","New York"));
    }

    @Test
    public void stream_create() {
        //通过 java.util.Collection.stream() 方法用集合创建流
        List<String> list = Arrays.asList("a", "b", "c");
        // 创建一个顺序流
        Stream<String> stream = list.stream();
        // 创建一个并行流
        Stream<String> parallelStream = list.parallelStream();

        //使用java.util.Arrays.stream(T[] array)方法用数组创建流
        int[] array = {1, 3, 5, 6, 8};
        IntStream intStream = Arrays.stream(array);

        //使用Stream的静态方法：of()、iterate()、generate()
        Stream<Integer> streamOf = Stream.of(1, 2, 3, 4, 5, 6);

        Stream<Integer> stream2 = Stream.iterate(0, (x) -> x + 3).limit(5);
        stream2.forEach(System.out::println);

        Stream<Double> stream3 = Stream.generate(Math::random).limit(3);
        stream3.forEach(System.out::println);
    }

    /**
     * Stream也是支持类似集合的遍历和匹配元素的，只是Stream中的元素是以Optional类型存在的。Stream的遍历、匹配非常简单。
     */
    @Test
    public void foreach_find_match(){
        List<Integer> list = Arrays.asList(7, 6, 9, 3, 8, 2, 1);
        // 遍历输出符合条件的元素
        list.stream().filter(x -> x > 6).forEach(System.out::println);
        // 匹配第一个
        Optional<Integer> findFirst = list.stream().filter(x -> x > 6).findFirst();
        // 匹配任意（适用于并行流）
        Optional<Integer> findAny = list.parallelStream().filter(x -> x > 6).findAny();
        // 是否包含符合特定条件的元素
        boolean anyMatch = list.stream().anyMatch(x -> x < 6);
        assertThat(7, Is.is(findFirst.get()));
        assertThat(8,IsEqual.equalTo(findAny.get()));
        assertThat(true,IsEqual.equalTo(anyMatch));
    }

    @Test
    public void filter(){
        //案例一
        List<Integer> list = Arrays.asList(6, 7, 3, 8, 1, 2, 9);
        Stream<Integer> stream = list.stream();
        stream.filter(x -> x > 7).forEach(System.out::println);
        //案例二
        List<String> filterList = personList.stream().filter(x -> x.getSalary() > 8000).map(Person::getName)
                .collect(Collectors.toList());
        System.out.println("高于8000的员工姓名：" + filterList);
    }

    @Test
    public void max_min_count(){
        //max
        List<String> strList = Arrays.asList("adnm", "admmt", "pot", "xbangd", "weoujgsd");
        Optional<String> max = strList.stream().max(Comparator.comparing(String::length));
        assertThat("weoujgsd",IsEqual.equalTo(max.get()));

        List<Integer> intList = Arrays.asList(7, 6, 9, 4, 11, 6);
        //自然排序
        Optional<Integer> naturalOrder = intList.stream().max(Integer::compareTo);
        //自定义排序
        Optional<Integer> customSort = intList.stream().max(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1.compareTo(o2);
            }
        });
        assertThat(11,Is.is(naturalOrder.get()));
        assertThat(11,Is.is(customSort.get()));

        Optional<Person> maxSalaryPerson = personList.stream().max(Comparator.comparing(Person::getSalary));
        assertThat(9500,Is.is(maxSalaryPerson.get().getSalary()));

        //count
        long count = intList.stream().filter(x -> x > 6).count();
        assertThat(3L, Is.is(count));
    }

    @Test
    public void map_flatMap(){
        //英文字符串数组的元素全部改为大写。整数数组每个元素+3
        String[] strArr = { "abcd", "bcdd", "defde", "fTr" };
        List<String> stringList = Arrays.asList(strArr).stream().map(str -> str.toUpperCase()).collect(Collectors.toList());
        stringList.forEach(System.out::println);
        stringList = Arrays.stream(strArr).map(String::toUpperCase).collect(Collectors.toList());
        stringList.forEach(System.out::println);

        List<Integer> intList = Arrays.asList(1, 3, 5, 7, 9, 11);
        intList = intList.stream().map(x -> x+3).collect(Collectors.toList());
        System.out.println(intList);

        // 不改变原来员工集合的方式
        List<Person> newPersonList = personList.stream().map(person -> {
            return new Person(person.getName(), person.getSalary() + 1000, person.getSex(), person.getArea());
        }).collect(Collectors.toList());
        System.out.println(personList);
        System.out.println(newPersonList);

        //改变原有员工集合
        personList.stream().map(person -> {
            person.setSalary(person.getSalary() + 1000);
            return person;
        }).collect(Collectors.toList());
        System.out.println(personList);

        //将两个字符数组合并成一个新的字符数组
        List<String> list = Arrays.asList("m,k,l,a", "1,3,5,7");
        System.out.println(list);
        List<String> newList = list.stream().flatMap(s -> {
            String[] split = s.split(",");
            Stream<String> stream = Arrays.stream(split);
            return stream;
        }).collect(Collectors.toList());
        System.out.println(newList);

        List<String> list1 = list.stream().flatMap(s -> Arrays.stream(s.split(","))).collect(Collectors.toList());
        System.out.println(list1);
    }

    //归约，也称缩减，顾名思义，是把一个流缩减成一个值，能实现对集合求和、求乘积和求最值操作。
    @Test
    public void reduce(){

        //求Integer集合的元素之和、乘积和最大值。
        List<Integer> list = Arrays.asList(1, 3, 2, 8, 11, 4);
        //方式一
        Optional<Integer> reduce1 = list.stream().reduce((x, y) -> x + y);
        //方式二
        Optional<Integer> reduce2 = list.stream().reduce(Integer::sum);
        //方式三
        Integer reduce3 = list.stream().reduce(0, Integer::sum);
        System.out.println(String.format("集合求和：第一种 【%d】，第二种【%d】，第三种【%d】",reduce1.get(),reduce2.get(),reduce3));

        //求所有员工的工资之和和最高工资。
        //工资求和方式一
        Optional<Integer> reduce01 = personList.stream().map(Person::getSalary).reduce(Integer::sum);
        //工资求和方式二
        Integer reduce02 = personList.stream().reduce(0, (sum, p) -> sum += p.getSalary(), (sum1,sum2) -> sum1 + sum2);
        //工资求和方式三
        Integer reduce03 = personList.stream().reduce(0, (sum, p) -> sum += p.getSalary(), Integer::sum);
        System.out.println(String.format("第一种 【%d】第二种【%d】第三种【%d】",reduce01.get(),reduce02,reduce03));
        // 求最高工资方式1：
        Integer maxSalary = personList.stream().reduce(0, (max, p) -> max > p.getSalary() ? max : p.getSalary(),
                Integer::max);
        // 求最高工资方式2：
        Integer maxSalary2 = personList.stream().reduce(0, (max, p) -> max > p.getSalary() ? max : p.getSalary(),
                (max1, max2) -> max1 > max2 ? max1 : max2);
        System.out.println("最高工资：" + maxSalary + "," + maxSalary2);

    }

    @Test
    public void demoReduce() {
        //取出out
        PrintStream out = System.out;
        //实现Predicate接口并且指定行为：传入的参数中是否包含字符串"a"
        //后续可以调用该接口的test方法做筛选判断
        Predicate<String> predicate = x -> x.contains("a");

        out.println("单个参数的reduce方法->接收BinaryOperator函数返回一个Optional<T>类型\n" +
                "实际上，该方法此时的表现为将该序列(Stream流内的类型)\n" +
                "的第一个元素与该流后续所有元素做2合计算'比如：(a[0]+a[1])+a[3]'\n" +
                "执行完函数后获得一个Optional<T>类型（可选的，任意的）后调用get()方法进行取值");
        Stream<Integer> stream2 = Stream.of(1, 2, 3);
        //输出结果为6
        out.println(stream2.reduce((x, y) -> x * y).get());

        out.println("两个参数1reduce方法实际上只是多了一个初始化的值‘T’\n" +
                "，第二个参数与单参方法一致,该重载方法返回类型为‘T’，指通过BinaryOperator进行计算后\n" +
                "返回一个类型与初始化参数的类型一致的值:::重点->双参函数与单参的计算流程不同：\n" +
                "该函数是将初始化参数与流内所有的元素逐个进行二和运算，\n" +
                "下面实例为将所有包含'a'的元素拼接在初始化参数的后面");
        Stream<String> stream3 = Stream.of("as1", "a12", "nmm1", "cc2", "ac3", "ab4");
        //输出结果为[valueMain]：as1a12ac3ab4
        out.println(stream3.reduce("[valueMain]：", (x, y) -> {
            if (predicate.test(y)) return x.concat(y);
            else return x;
        }));

        out.println("初始化参数为一个集合，将流内所有符合条件的元素筛选出来加入到该初始参数中");
        Stream<String> stream = Stream.of("as", "ai", "nmm", "cc", "ac", "ab");
        //输出结果为：as ai ac ab
        stream.reduce(new ArrayList<String>(), (x, y) -> {
            if (predicate.test(y)) x.add(y);
            return x;
        }, (x, y) -> x).forEach(System.out::println);

        out.println("并行(parallel)的影响下，第三个参数才会生效\n" +
                "该状态下理解为第二个函数根据流内数据的个数分为多线程去处理每个值与首参U的2合计算\n" +
                "首参U分别与流内每个值计算完毕后，由第三个参数对这些值做出整合(该参数要求实现\n" +
                "BinaryOperator接口并给出一个单值计算的行为),即，\n" +
                "该接口内三个参数为同一类型，并作出操作(T x,T y)->{return ?(T)}");
        Stream<Integer> stream1 = Stream.of(1, 2, 3);
        //输出结果为：990   (8+1)*(8+2)*(8+3)
        out.println(stream1.parallel().reduce(8, (x, y) -> x + y, (x, y) -> x * y));

        out.println("非并行情况下的第三个参数BinaryOperator " +
                "combiner(合成器)\n会对第二个参数BiFunction accumulator(累加器) 产生什么影响？\n" +
                "会对该函数运行结果产生什么影响？\n" +
                "答案是：第三个参数无效。\n" +
                "符合预期的说法：非并行情况下第三个参数根本就不需要，不会对该函数产生任何影响\n" +
                "Debug:该函数执行完第二个参数后直接停止运行，根本没有访问到第三个参数去执行");
        Stream<Integer> stream4 = Stream.of(1, 2, 3);
        //输出结果为16     10+1+2+3
        out.println(stream4.reduce(0, (x, y) -> x + y, (x, y) -> x * y));
    }

    @Test
    public void toList_toSet_toMap(){
        List<Integer> list = Arrays.asList(1, 6, 3, 4, 6, 7, 9, 6, 20);
        List<Integer> listNew = list.stream().filter(x -> x % 2 == 0).collect(Collectors.toList());
        Set<Integer> set = list.stream().filter(x -> x % 2 == 0).collect(Collectors.toSet());


        Map<?, Person> map = personList.stream().filter(p -> p.getSalary() > 8000)
                .collect(Collectors.toMap(Person::getName, p -> p));
        System.out.println("toList:" + listNew);
        System.out.println("toSet:" + set);
        System.out.println("toMap:" + map);
    }

    @Test
    public void count_averaging(){
        // 求总数
        Long count = personList.stream().collect(Collectors.counting());
        assertThat(6L, Is.is(count));
        // 求平均工资
        Double average = personList.stream().collect(Collectors.averagingDouble(Person::getSalary));
        assertThat(8216.666666666666, Is.is(average));
        // 求最高工资
        Optional<Integer> max = personList.stream().map(Person::getSalary).collect(Collectors.maxBy(Integer::compare));
        assertThat(9500, Is.is(max.get()));
        // 求工资之和
        Integer sum = personList.stream().collect(Collectors.summingInt(Person::getSalary));
        assertThat(49300, Is.is(sum));
        // 一次性统计所有信息
        DoubleSummaryStatistics collect = personList.stream().collect(Collectors.summarizingDouble(Person::getSalary));
        System.out.println(collect);
    }

    @Test
    public void partitioningBy_groupingBy(){
        // 将员工按薪资是否高于8000分组
        Map<Boolean, List<Person>> part = personList.stream().collect(Collectors.partitioningBy(x -> x.getSalary() > 8000));
        // 将员工按性别分组
        Map<String, List<Person>> group = personList.stream().collect(Collectors.groupingBy(Person::getSex));
        // 将员工先按性别分组，再按地区分组
        Map<String, Map<String, List<Person>>> group2 = personList.stream()
                .collect(Collectors.groupingBy(Person::getSex, Collectors.groupingBy(Person::getArea)));
        System.out.println("员工按薪资是否大于8000分组情况：" + part);
        System.out.println("员工按性别分组情况：" + group);
        System.out.println("员工按性别、地区：" + group2);
    }

    @Test
    public void joining(){

        String names = personList.stream().map(p -> p.getName()).collect(Collectors.joining(","));
        System.out.println("所有员工的姓名：" + names);
        List<String> list = Arrays.asList("A", "B", "C");
        String string = list.stream().collect(Collectors.joining("-"));
        System.out.println("拼接后的字符串：" + string);

    }

    @Test
    public void Collectors_reducing(){
        // 每个员工减去起征点后的薪资之和（这个例子并不严谨，但一时没想到好的例子）
        Integer sum = personList.stream().collect(Collectors.reducing(0, Person::getSalary, (i, j) -> (i + j - 5000)));
        System.out.println("员工扣税薪资总和：" + sum);

        // stream的reduce
        Optional<Integer> sum2 = personList.stream().map(Person::getSalary).reduce(Integer::sum);
        System.out.println("员工薪资总和：" + sum2.get());
    }

    @Test
    public void sorted(){
        // 按工资增序排序
        List<String> newList = personList.stream().sorted(Comparator.comparing(Person::getSalary)).map(Person::getName)
                .collect(Collectors.toList());
        // 按工资倒序排序
        List<String> newList2 = personList.stream().sorted(Comparator.comparing(Person::getSalary).reversed())
                .map(Person::getName).collect(Collectors.toList());
        // 先按工资再按年龄自然排序（从小到大）
        List<String> newList3 = personList.stream().sorted(Comparator.comparing(Person::getSalary).reversed())
                .sorted(Comparator.comparing(Person::getArea))
                .map(Person::getName).collect(Collectors.toList());
        // 先按工资再按年龄自定义排序（从大到小）
//        List<String> newList4 = personList.stream().sorted((p1, p2) -> {
//            if (p1.getSalary() == p2.getSalary()) {
//                return p2.getAge() - p1.getAge();
//            } else {
//                return p2.getSalary() - p1.getSalary();
//            }
//        }).map(Person::getName).collect(Collectors.toList());

        System.out.println("按工资自然排序：" + newList);
        System.out.println("按工资降序排序：" + newList2);
        System.out.println("先按工资再按地区：" + newList3);
//        System.out.println("先按工资再按年龄自定义降序排序：" + newList4);
    }

    @Test
    public void distinct_skip_limit(){
        String[] arr1 = { "a", "b", "c", "d" };
        String[] arr2 = { "d", "e", "f", "g" };

        Stream<String> stream1 = Stream.of(arr1);
        Stream<String> stream2 = Stream.of(arr2);
        // concat:合并两个流 distinct：去重
        List<String> newList = Stream.concat(stream1, stream2).distinct().collect(Collectors.toList());
        // limit：限制从流中获得前n个数据
        List<Integer> collect = Stream.iterate(1, x -> x + 2).limit(10).collect(Collectors.toList());
        // skip：跳过前n个数据
        List<Integer> collect2 = Stream.iterate(1, x -> x + 2).skip(2).limit(5).collect(Collectors.toList());

        System.out.println("流合并：" + newList);
        System.out.println("limit：" + collect);
        System.out.println("skip：" + collect2);
    }

}








class Person {
    // 姓名
    private String name;
    // 薪资
    private int salary;
    //性别
    private String sex;
    // 地区
    private String area;

    // 构造方法
    public Person(String name, int salary, String sex, String area) {
        this.name = name;
        this.salary = salary;
        this.sex = sex;
        this.area = area;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", salary=" + salary +
                ", sex='" + sex + '\'' +
                ", area='" + area + '\'' +
                '}';
    }
}