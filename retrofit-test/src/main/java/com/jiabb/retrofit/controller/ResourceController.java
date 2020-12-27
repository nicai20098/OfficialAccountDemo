package com.jiabb.retrofit.controller;

import com.jiabb.retrofit.domain.Person;
import com.jiabb.retrofit.domain.ResponseVo;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jiabinbin
 * @date 2020/12/27 11:00 下午
 * @classname ResourController
 * @description TODO
 */
@RestController
@RequestMapping("/resource")
public class ResourceController {

    @RequestMapping("/person/{id}")
    public ResponseVo<Person> getPerson(@PathVariable("id")Long id){
        if (id != 12L){
            return ResponseVo.ofError();
        }
        Person person = new Person();
        person.setId(12L);
        person.setName("zhangsan");
        person.setAge(29);
        ResponseVo<Person> personResponseVo = new ResponseVo<>();
        personResponseVo.setData(person);
        personResponseVo.setStatus(200);
        return personResponseVo;

    }

}
