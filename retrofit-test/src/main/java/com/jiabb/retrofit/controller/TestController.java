package com.jiabb.retrofit.controller;

import com.jiabb.retrofit.domain.Person;
import com.jiabb.retrofit.domain.ResponseVo;
import com.jiabb.retrofit.httpexample.HttpApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import retrofit2.http.Path;

/**
 * @author jiabinbin
 * @date 2020/12/27 10:54 下午
 * @classname TestController
 * @description 测试Controller
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private HttpApi httpApi;


    @RequestMapping("/getPerson/{id}")
    public ResponseVo<Person> getPerson(@PathVariable("id")Long id){
//        return new ResponseVo<>();
        return httpApi.getPerson(id);
    }

}
