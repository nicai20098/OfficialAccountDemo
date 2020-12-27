package com.jiabb.retrofit.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * @author jiabinbin
 * @date 2020/12/27 10:44 下午
 * @classname Person
 * @description 测试domain
 */
@Data
public class Person implements Serializable {

    private Long id;
    private String name;
    private Integer age;

}
