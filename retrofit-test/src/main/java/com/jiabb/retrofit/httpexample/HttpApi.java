package com.jiabb.retrofit.httpexample;

import com.github.lianjiatech.retrofit.spring.boot.annotation.RetrofitClient;
import com.jiabb.retrofit.domain.Person;
import com.jiabb.retrofit.domain.ResponseVo;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * @author jiabinbin
 * @date 2020/12/27 10:41 下午
 * @classname HttpApi
 * @description 测试接口路径
 */
@RetrofitClient(baseUrl = "${test.baseUrl}")
public interface HttpApi {
    @GET("person")
    ResponseVo<Person> getPerson(Long id);
}
